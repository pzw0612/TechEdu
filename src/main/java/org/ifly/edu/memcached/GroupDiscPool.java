/**
 * Copyright (c) 2008 Greg Whalin
 * All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the BSD license
 *
 * This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 *
 * You should have received a copy of the BSD License along with this
 * library.
 *
 * @author greg whalin <greg@meetup.com> 
 */
package org.ifly.edu.memcached;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.CRC32;

import org.apache.log4j.Logger;
import org.ifly.edu.memcached.SockIOPool.SockIO;

/** 
 * This class is a connection pool for maintaning a pool of persistent connections<br/>
 * to memcached servers.
 *
 * The pool must be initialized prior to use. This should typically be early on<br/>
 * in the lifecycle of the JVM instance.<br/>
 * <br/>
 * <h3>An example of initializing using defaults:</h3>
 * <pre>
 *
 *	static {
 *		String[] serverlist = { "cache0.server.com:12345", "cache1.server.com:12345" };
 *
 *		SockIOPool pool = SockIOPool.getInstance();
 *		pool.setServers(serverlist);
 *		pool.initialize();	
 *	}
 * </pre> 
 * <h3>An example of initializing using defaults and providing weights for servers:</h3>
 *  <pre>
 *	static {
 *		String[] serverlist = { "cache0.server.com:12345", "cache1.server.com:12345" };
 *		Integer[] weights   = { new Integer(5), new Integer(2) };
 *		
 *		SockIOPool pool = SockIOPool.getInstance();
 *		pool.setServers(serverlist);
 *		pool.setWeights(weights);	
 *		pool.initialize();	
 *	}
 *  </pre> 
 * <h3>An example of initializing overriding defaults:</h3>
 *  <pre>
 *	static {
 *		String[] serverlist     = { "cache0.server.com:12345", "cache1.server.com:12345" };
 *		Integer[] weights       = { new Integer(5), new Integer(2) };	
 *		int initialConnections  = 10;
 *		int minSpareConnections = 5;
 *		int maxSpareConnections = 50;	
 *		long maxIdleTime        = 1000 * 60 * 30;	// 30 minutes
 *		long maxBusyTime        = 1000 * 60 * 5;	// 5 minutes
 *		long maintThreadSleep   = 1000 * 5;			// 5 seconds
 *		int	socketTimeOut       = 1000 * 3;			// 3 seconds to block on reads
 *		int	socketConnectTO     = 1000 * 3;			// 3 seconds to block on initial connections.  If 0, then will use blocking connect (default)
 *		boolean failover        = false;			// turn off auto-failover in event of server down	
 *		boolean nagleAlg        = false;			// turn off Nagle's algorithm on all sockets in pool	
 *		boolean aliveCheck      = false;			// disable health check of socket on checkout
 *
 *		SockIOPool pool = SockIOPool.getInstance();
 *		pool.setServers( serverlist );
 *		pool.setWeights( weights );	
 *		pool.setInitConn( initialConnections );
 *		pool.setMinConn( minSpareConnections );
 *		pool.setMaxConn( maxSpareConnections );
 *		pool.setMaxIdle( maxIdleTime );
 *		pool.setMaxBusyTime( maxBusyTime );
 *		pool.setMaintSleep( maintThreadSleep );
 *		pool.setSocketTO( socketTimeOut );
 *		pool.setNagle( nagleAlg );	
 *		pool.setHashingAlg( SockIOPool.NEW_COMPAT_HASH );
 *		pool.setAliveCheck( true );
 *		pool.initialize();	
 *	}
 *  </pre> 
 * The easiest manner in which to initialize the pool is to set the servers and rely on defaults as in the first example.<br/> 
 * After pool is initialized, a client will request a SockIO object by calling getSock with the cache key<br/>
 * The client must always close the SockIO object when finished, which will return the connection back to the pool.<br/> 
 * <h3>An example of retrieving a SockIO object:</h3>
 * <pre>
 *		SockIOPool.SockIO sock = SockIOPool.getInstance().getSock( key );
 *		try {
 *			sock.write( "version\r\n" );	
 *			sock.flush();	
 *			System.out.println( "Version: " + sock.readLine() );	
 *		}
 *		catch (IOException ioe) { System.out.println( "io exception thrown" ) };	
 *
 *		sock.close();	
 * </pre> 
 *
 * @author greg whalin <greg@whalin.com> 
 * @version 1.5
 */
public class GroupDiscPool {

	// logger
	private static Logger log =
		Logger.getLogger( GroupDiscPool.class.getName() );

	// store instances of pools
	private static Map<String,GroupDiscPool> pools =
		new HashMap<String,GroupDiscPool>();

	// avoid recurring construction
	private static ThreadLocal<MessageDigest> MD5 = new ThreadLocal<MessageDigest>() {
		@Override
		protected MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance( "MD5" );
			}
			catch ( NoSuchAlgorithmException e ) {
				log.error( "++++ no md5 algorithm found" );
				throw new IllegalStateException( "++++ no md5 algorythm found");			
			}
		}
	};

	// Constants
	private static final Integer ZERO       = new Integer( 0 );
	public static final int NATIVE_HASH     = 0;				// native String.hashCode();
	public static final int OLD_COMPAT_HASH = 1;				// original compatibility hashing algorithm (works with other clients)
	public static final int NEW_COMPAT_HASH = 2;				// new CRC32 based compatibility hashing algorithm (works with other clients)
	public static final int CONSISTENT_HASH = 3;				// MD5 Based -- Stops thrashing when a server added or removed

	public static final long MAX_RETRY_DELAY = 10 * 60 * 1000;  // max of 10 minute delay for fall off

	// Pool data
	private MaintThread maintThread;
	private boolean initialized        = false;
	private int maxCreate              = 1;					// this will be initialized by pool when the pool is initialized

	// initial, min and max pool sizes
	private int poolMultiplier        = 3;
	private int initConn              = 10;
	private int minConn               = 5;
	private int maxConn               = 100;
	private long maxIdle              = 1000 * 60 * 5;		// max idle time for avail sockets
	private long maxBusyTime          = 1000 * 30;			// max idle time for avail sockets
	private long maintSleep           = 1000 * 30;			// maintenance thread sleep time
	private int socketTO              = 1000 * 3;			// default timeout of socket reads
	private int socketConnectTO       = 1000 * 3;	        // default timeout of socket connections
	private boolean aliveCheck        = false;				// default to not check each connection for being alive
	private boolean failover          = true;				// default to failover in event of cache server dead
	private boolean failback          = true;				// only used if failover is also set ... controls putting a dead server back into rotation
	private boolean nagle             = false;				// enable/disable Nagle's algorithm
	private int hashingAlg 		      = NATIVE_HASH;		// default to using the native hash as it is the fastest

	// locks
	private final ReentrantLock hostDeadLock = new ReentrantLock();

	// list of all servers
	private String[] servers;
	private Integer[] weights;
	private Integer totalWeight = 0;

	private List<String> buckets;
	private TreeMap<Long,String> consistentBuckets;

	// dead server map
	private Map<String,Date> hostDead;
	private Map<String,Long> hostDeadDur;
	
	// map to hold all available sockets
	// map to hold busy sockets
	// set to hold sockets to close
	private Map<String,Map<String,Long>> availPool;
	private Map<String,Map<String,Long>> busyPool;
	private Map<String,Integer> deadPool;
	
	// empty constructor
	protected GroupDiscPool() { }
	
	/** 
	 * Factory to create/retrieve new pools given a unique poolName. 
	 * 
	 * @param poolName unique name of the pool
	 * @return instance of SockIOPool
	 */
	public static synchronized GroupDiscPool getInstance( String poolName ) {
		if ( pools.containsKey( poolName ) )
			return pools.get( poolName );

		GroupDiscPool pool = new GroupDiscPool();
		pools.put( poolName, pool );

		return pool;
	}

	/** 
	 * Single argument version of factory used for back compat.
	 * Simply creates a pool named "default". 
	 * 
	 * @return instance of SockIOPool
	 */
	public static GroupDiscPool getInstance() {
		return getInstance( "default" );
	}

	/** 
	 * Sets the list of all cache servers. 
	 * 
	 * @param servers String array of servers [host:port]
	 */
	public void setServers( String[] servers ) { this.servers = servers; }
	
	/** 
	 * Returns the current list of all cache servers. 
	 * 
	 * @return String array of servers [host:port]
	 */
	public String[] getServers() { return this.servers; }

	/** 
	 * Sets the list of weights to apply to the server list.
	 *
	 * This is an int array with each element corresponding to an element<br/>
	 * in the same position in the server String array. 
	 * 
	 * @param weights Integer array of weights
	 */
	public void setWeights( Integer[] weights ) { this.weights = weights; }
	
	/** 
	 * Returns the current list of weights. 
	 * 
	 * @return int array of weights
	 */
	public Integer[] getWeights() { return this.weights; }

	/** 
	 * Sets the initial number of connections per server in the available pool. 
	 * 
	 * @param initConn int number of connections
	 */
	public void setInitConn( int initConn ) { this.initConn = initConn; }
	
	/** 
	 * Returns the current setting for the initial number of connections per server in
	 * the available pool. 
	 * 
	 * @return number of connections
	 */
	public int getInitConn() { return this.initConn; }

	/** 
	 * Sets the minimum number of spare connections to maintain in our available pool. 
	 * 
	 * @param minConn number of connections
	 */
	public void setMinConn( int minConn ) { this.minConn = minConn; }
	
	/** 
	 * Returns the minimum number of spare connections in available pool. 
	 * 
	 * @return number of connections
	 */
	public int getMinConn() { return this.minConn; }

	/** 
	 * Sets the maximum number of spare connections allowed in our available pool. 
	 * 
	 * @param maxConn number of connections
	 */
	public void setMaxConn( int maxConn ) { this.maxConn = maxConn; }

	/** 
	 * Returns the maximum number of spare connections allowed in available pool. 
	 * 
	 * @return number of connections
	 */
	public int getMaxConn() { return this.maxConn; }

	/** 
	 * Sets the max idle time for threads in the available pool.
	 * 
	 * @param maxIdle idle time in ms
	 */
	public void setMaxIdle( long maxIdle ) { this.maxIdle = maxIdle; }
	
	/** 
	 * Returns the current max idle setting. 
	 * 
	 * @return max idle setting in ms
	 */
	public long getMaxIdle() { return this.maxIdle; }

	/** 
	 * Sets the max busy time for threads in the busy pool.
	 * 
	 * @param maxBusyTime idle time in ms
	 */
	public void setMaxBusyTime( long maxBusyTime ) { this.maxBusyTime = maxBusyTime; }
	
	/** 
	 * Returns the current max busy setting. 
	 * 
	 * @return max busy setting in ms
	 */
	public long getMaxBusy() { return this.maxBusyTime; }

	/** 
	 * Set the sleep time between runs of the pool maintenance thread.
	 * If set to 0, then the maint thread will not be started. 
	 * 
	 * @param maintSleep sleep time in ms
	 */
	public void setMaintSleep( long maintSleep ) { this.maintSleep = maintSleep; }
	
	/** 
	 * Returns the current maint thread sleep time.
	 * 
	 * @return sleep time in ms
	 */
	public long getMaintSleep() { return this.maintSleep; }

	/** 
	 * Sets the socket timeout for reads.
	 * 
	 * @param socketTO timeout in ms
	 */
	public void setSocketTO( int socketTO ) { this.socketTO = socketTO; }
	
	/** 
	 * Returns the socket timeout for reads.
	 * 
	 * @return timeout in ms
	 */
	public int getSocketTO() { return this.socketTO; }

	/** 
	 * Sets the socket timeout for connect.
	 * 
	 * @param socketConnectTO timeout in ms
	 */
	public void setSocketConnectTO( int socketConnectTO ) { this.socketConnectTO = socketConnectTO; }
	
	/** 
	 * Returns the socket timeout for connect.
	 * 
	 * @return timeout in ms
	 */
	public int getSocketConnectTO() { return this.socketConnectTO; }

	/** 
	 * Sets the failover flag for the pool.
	 *
	 * If this flag is set to true, and a socket fails to connect,<br/>
	 * the pool will attempt to return a socket from another server<br/>
	 * if one exists.  If set to false, then getting a socket<br/>
	 * will return null if it fails to connect to the requested server.
	 * 
	 * @param failover true/false
	 */
	public void setFailover( boolean failover ) { this.failover = failover; }
	
	/** 
	 * Returns current state of failover flag.
	 * 
	 * @return true/false
	 */
	public boolean getFailover() { return this.failover; }

	/** 
	 * Sets the failback flag for the pool.
	 *
	 * If this is true and we have marked a host as dead,
	 * will try to bring it back.  If it is false, we will never
	 * try to resurrect a dead host.
	 *
	 * @param failback true/false
	 */
	public void setFailback( boolean failback ) { this.failback = failback; }
	
	/** 
	 * Returns current state of failover flag.
	 * 
	 * @return true/false
	 */
	public boolean getFailback() { return this.failback; }

	/**
	 * Sets the aliveCheck flag for the pool.
	 *
	 * When true, this will attempt to talk to the server on
	 * every connection checkout to make sure the connection is
	 * still valid.  This adds extra network chatter and thus is
	 * defaulted off.  May be useful if you want to ensure you do
	 * not have any problems talking to the server on a dead connection.
	 *
	 * @param aliveCheck true/false
	 */
	public void setAliveCheck( boolean aliveCheck ) { this.aliveCheck = aliveCheck; }


	/**
	 * Returns the current status of the aliveCheck flag.
	 *
	 * @return true / false
	 */
	public boolean getAliveCheck() { return this.aliveCheck; }

	/** 
	 * Sets the Nagle alg flag for the pool.
	 *
	 * If false, will turn off Nagle's algorithm on all sockets created.
	 * 
	 * @param nagle true/false
	 */
	public void setNagle( boolean nagle ) { this.nagle = nagle; }
	
	/** 
	 * Returns current status of nagle flag
	 * 
	 * @return true/false
	 */
	public boolean getNagle() { return this.nagle; }

	/** 
	 * Sets the hashing algorithm we will use.
	 *
	 * The types are as follows.
	 *
	 * SockIOPool.NATIVE_HASH (0)     - native String.hashCode() - fast (cached) but not compatible with other clients
	 * SockIOPool.OLD_COMPAT_HASH (1) - original compatibility hashing alg (works with other clients)
	 * SockIOPool.NEW_COMPAT_HASH (2) - new CRC32 based compatibility hashing algorithm (fast and works with other clients)
	 * 
	 * @param alg int value representing hashing algorithm
	 */
	public void setHashingAlg( int alg ) { this.hashingAlg = alg; }

	/** 
	 * Returns current status of customHash flag
	 * 
	 * @return true/false
	 */
	public int getHashingAlg() { return this.hashingAlg; }

	/** 
	 * Internal private hashing method.
	 *
	 * This is the original hashing algorithm from other clients.
	 * Found to be slow and have poor distribution.
	 * 
	 * @param key String to hash
	 * @return hashCode for this string using our own hashing algorithm
	 */
	private static long origCompatHashingAlg( String key ) {
		long hash   = 0;
		char[] cArr = key.toCharArray();

		for ( int i = 0; i < cArr.length; ++i ) {
			hash = (hash * 33) + cArr[i];
		}

		return hash;
	}

	/** 
	 * Internal private hashing method.
	 *
	 * This is the new hashing algorithm from other clients.
	 * Found to be fast and have very good distribution. 
	 *
	 * UPDATE: This is dog slow under java
	 * 
	 * @param key 
	 * @return 
	 */
	private static long newCompatHashingAlg( String key ) {
		CRC32 checksum = new CRC32();
		checksum.update( key.getBytes() );
		long crc = checksum.getValue();
		return (crc >> 16) & 0x7fff;
	}

	/** 
	 * Internal private hashing method.
	 *
	 * MD5 based hash algorithm for use in the consistent
	 * hashing approach.
	 * 
	 * @param key 
	 * @return 
	 */
	private static long md5HashingAlg( String key ) {
		MessageDigest md5 = MD5.get();
		md5.reset();
		md5.update( key.getBytes() );
		byte[] bKey = md5.digest();
		long res = ((long)(bKey[3]&0xFF) << 24) | ((long)(bKey[2]&0xFF) << 16) | ((long)(bKey[1]&0xFF) << 8) | (long)(bKey[0]&0xFF);
		return res;
	}

	/** 
	 * Returns a bucket to check for a given key. 
	 * 
	 * @param key String key cache is stored under
	 * @return int bucket
	 */
	private long getHash( String key, Integer hashCode ) {

		if ( hashCode != null ) {
			if ( hashingAlg == CONSISTENT_HASH )
				return hashCode.longValue() & 0xffffffffL;
			else
				return hashCode.longValue();
		}
		else {
			switch ( hashingAlg ) {
				case NATIVE_HASH:
					return (long)key.hashCode();
				case OLD_COMPAT_HASH:
					return origCompatHashingAlg( key );
				case NEW_COMPAT_HASH:
					return newCompatHashingAlg( key );
				case CONSISTENT_HASH:
					return md5HashingAlg( key );
				default:
					// use the native hash as a default
					hashingAlg = NATIVE_HASH;
					return (long)key.hashCode();
			}
		}
	}

	private long getBucket( String key, Integer hashCode ) {
		long hc = getHash( key, hashCode );

		if ( this.hashingAlg == CONSISTENT_HASH ) {
			return findPointFor( hc );
		}
		else {
			long bucket = hc % buckets.size();
			if ( bucket < 0 ) bucket *= -1;
			return bucket;
		}
	}

	/**
	 * Gets the first available key equal or above the given one, if none found,
	 * returns the first k in the bucket 
	 * @param k key
	 * @return
	 */
	private Long findPointFor( Long hv ) {
		// this works in java 6, but still want to release support for java5
		//Long k = this.consistentBuckets.ceilingKey( hv );
		//return ( k == null ) ? this.consistentBuckets.firstKey() : k;

		SortedMap<Long,String> tmap =
			this.consistentBuckets.tailMap( hv );

		return ( tmap.isEmpty() ) ? this.consistentBuckets.firstKey() : tmap.firstKey();
	}

	/** 
	 * Initializes the pool. 
	 */
	public void initialize() {

		synchronized( this ) {

			// check to see if already initialized
			if ( initialized
					&& ( buckets != null || consistentBuckets != null )
					&& ( availPool != null )
					&& ( busyPool != null ) ) {
				log.error( "++++ trying to initialize an already initialized pool" );
				return;
			}

			// if servers is not set, or it empty, then
			// throw a runtime exception
			if ( servers == null || servers.length <= 0 ) {
				log.error( "++++ trying to initialize with no servers" );
				throw new IllegalStateException( "++++ trying to initialize with no servers" );
			}

			// initalize our internal hashing structures
			if ( this.hashingAlg == CONSISTENT_HASH )
				populateConsistentBuckets();
			else
				populateBuckets();

			// mark pool as initialized
			this.initialized = true;

			// start maint thread
			if ( this.maintSleep > 0 )
				this.startMaintThread();
		}
	}

	private void populateBuckets() {
		if ( log.isDebugEnabled() )
			log.debug( "++++ initializing internal hashing structure for consistent hashing" );

		// store buckets in tree map
		this.buckets = new ArrayList<String>();

		for ( int i = 0; i < servers.length; i++ ) {
			if ( this.weights != null && this.weights.length > i ) {
				for ( int k = 0; k < this.weights[i].intValue(); k++ ) {
					this.buckets.add( servers[i] );
					if ( log.isDebugEnabled() )
						log.debug( "++++ added " + servers[i] + " to server bucket" );
				}
			}
			else {
				this.buckets.add( servers[i] );
				if ( log.isDebugEnabled() )
					log.debug( "++++ added " + servers[i] + " to server bucket" );
			}

			// create initial connections
			if ( log.isDebugEnabled() )
				log.debug( "+++ creating initial connections (" + initConn + ") for host: " + servers[i] );
		}
	}

	private void populateConsistentBuckets() {
		if ( log.isDebugEnabled() )
			log.debug( "++++ initializing internal hashing structure for consistent hashing" );

		// store buckets in tree map
		this.consistentBuckets = new TreeMap<Long,String>();

		MessageDigest md5 = MD5.get();
		if ( this.totalWeight <= 0 && this.weights !=  null ) {
			for ( int i = 0; i < this.weights.length; i++ )
				this.totalWeight += ( this.weights[i] == null ) ? 1 : this.weights[i];
		}
		else if ( this.weights == null ) {
			this.totalWeight = this.servers.length;
		}
		
		for ( int i = 0; i < servers.length; i++ ) {
			int thisWeight = 1;
			if ( this.weights != null && this.weights[i] != null )
				thisWeight = this.weights[i];

			double factor = Math.floor( ((double)(40 * this.servers.length * thisWeight)) / (double)this.totalWeight );
			
			for ( long j = 0; j < factor; j++ ) {
				byte[] d = md5.digest( ( servers[i] + "-" + j ).getBytes() );
				for ( int h = 0 ; h < 4; h++ ) {
					Long k = 
						  ((long)(d[3+h*4]&0xFF) << 24)
						| ((long)(d[2+h*4]&0xFF) << 16)
						| ((long)(d[1+h*4]&0xFF) << 8)
						| ((long)(d[0+h*4]&0xFF));

					consistentBuckets.put( k, servers[i] );
					if ( log.isDebugEnabled() )
						log.debug( "++++ added " + servers[i] + " to server bucket" );
				}				
			}

			// create initial connections
			if ( log.isDebugEnabled() )
				log.debug( "+++ creating initial connections (" + initConn + ") for host: " + servers[i] );
		}
	}

	/** 
	 * Returns state of pool. 
	 * 
	 * @return <CODE>true</CODE> if initialized.
	 */
	public boolean isInitialized() {
		return initialized;
	}



 	/** 
	 * @param key 
	 * @return 
	 */
	public String getHost( String key ) {
		return getHost( key, null );
	}

	/** 
	 * Gets the host that a particular key / hashcode resides on. 
	 * 
	 * @param key 
	 * @param hashcode 
	 * @return 
	 */
	public String getHost( String key, Integer hashcode ) {
		String socket = getSock( key, hashcode );

		//socket.close();
		return socket;
	}

	/** 
	 * Returns appropriate SockIO object given
	 * string cache key.
	 * 
	 * @param key hashcode for cache key
	 * @return SockIO obj connected to server
	 */
	public String getSock( String key ) {
		return getSock( key, null );
	}

	/** 
	 * Returns appropriate SockIO object given
	 * string cache key and optional hashcode.
	 *
	 * Trys to get SockIO from pool.  Fails over
	 * to additional pools in event of server failure.
	 * 
	 * @param key hashcode for cache key
	 * @param hashCode if not null, then the int hashcode to use
	 * @return SockIO obj connected to server
	 */
	public String getSock( String key, Integer hashCode ) {

		if ( log.isDebugEnabled() )
			log.debug( "cache socket pick " + key + " " + hashCode );

		if ( !this.initialized ) {
			log.error( "attempting to get SockIO from uninitialized pool!" );
			return null;
		}

		// if no servers return null
		if ( ( this.hashingAlg == CONSISTENT_HASH && consistentBuckets.size() == 0 )
				|| ( buckets != null && buckets.size() == 0 ) )
			return null;

		// if only one server, return it
		if ( ( this.hashingAlg == CONSISTENT_HASH && consistentBuckets.size() == 1 )
				|| ( buckets != null && buckets.size() == 1 ) ) {

			String sock = ( this.hashingAlg == CONSISTENT_HASH )
				? getConnection( consistentBuckets.get( consistentBuckets.firstKey() ) )
				: getConnection( buckets.get( 0 ) );



			return sock;
		}
		
		// from here on, we are working w/ multiple servers
		// keep trying different servers until we find one
		// making sure we only try each server one time

		// get initial bucket
		long bucket = getBucket( key, hashCode );
		String server = ( this.hashingAlg == CONSISTENT_HASH )
			? consistentBuckets.get( bucket )
			: buckets.get( (int)bucket );

		return server;
	}

	
	/** 
	 * Adds a socket to a given pool for the given host.
	 * THIS METHOD IS NOT THREADSAFE, SO BE CAREFUL WHEN USING!
	 *
	 * Internal utility method. 
	 * 
	 * @param pool pool to add to
	 * @param host host this socket is connected to
	 * @param socket socket to add
	 */
	protected void addSocketToPool( Map<String,Map<String,Long>> pool, String host, String socket ) {

		if ( pool.containsKey( host ) ) {
			Map<String,Long> sockets = pool.get( host );

			if ( sockets != null ) {
				sockets.put( socket, new Long( System.currentTimeMillis() ) );
				return;
			}
		}

		Map<String,Long> sockets =
			new IdentityHashMap<String,Long>();

		sockets.put( socket, new Long( System.currentTimeMillis() ) );
		pool.put( host, sockets );
	}

	/** 
	 * Removes a socket from specified pool for host.
	 * THIS METHOD IS NOT THREADSAFE, SO BE CAREFUL WHEN USING!
	 *
	 * Internal utility method. 
	 * 
	 * @param pool pool to remove from
	 * @param host host pool
	 * @param socket socket to remove
	 */
	protected void removeSocketFromPool( Map<String,Map<String,Long>> pool, String host, String socket ) {
		if ( pool.containsKey( host ) ) {
			Map<String,Long> sockets = pool.get( host );
			if ( sockets != null )
				sockets.remove( socket );
		}
	}


		





	/** 
	 * Starts the maintenance thread.
	 *
	 * This thread will manage the size of the active pool<br/>
	 * as well as move any closed, but not checked in sockets<br/>
	 * back to the available pool.
	 */
	protected void startMaintThread() {

		if ( maintThread != null ) {

			if ( maintThread.isRunning() ) {
				log.error( "main thread already running" );
			}
			else {
				maintThread.start();
			}
		}
		else {
			maintThread = new MaintThread( this );
			maintThread.setInterval( this.maintSleep );
			maintThread.start();
		}
	}

	/** 
	 * Stops the maintenance thread.
	 */
	protected void stopMaintThread() {
		if ( maintThread != null && maintThread.isRunning() )
			maintThread.stopThread();
	}

	/** 
	 * Runs self maintenance on all internal pools.
	 *
	 * This is typically called by the maintenance thread to manage pool size. 
	 */
	protected void selfMaint() {


		if ( log.isDebugEnabled() )
			log.debug( "+++ ending self maintenance." );
	}
	
	/** 
	 * Class which extends thread and handles maintenance of the pool.
	 * 
	 * @author greg whalin <greg@meetup.com>
	 * @version 1.5
	 */
	protected static class MaintThread extends Thread {

		// logger
		private static Logger log =
			Logger.getLogger( MaintThread.class.getName() );

		private GroupDiscPool pool;
		private long interval      = 1000 * 3; // every 3 seconds
		private boolean stopThread = false;
		private boolean running;

		protected MaintThread( GroupDiscPool pool ) {
			this.pool = pool;
			this.setDaemon( true );
			this.setName( "MaintThread" );
		}

		public void setInterval( long interval ) { this.interval = interval; }
		
		public boolean isRunning() {
			return this.running;
		}

		/** 
		 * sets stop variable
		 * and interupts any wait 
		 */
		public void stopThread() {
			this.stopThread = true;
			this.interrupt();
		}

		/** 
		 * Start the thread.
		 */
		public void run() {
			this.running = true;

			while ( !this.stopThread ) {
				try {
					Thread.sleep( interval );

					// if pool is initialized, then
					// run the maintenance method on itself
					if ( pool.isInitialized() )
						pool.selfMaint();

				}
				catch ( Exception e ) {
					break;
				}
			}

			this.running = false;
		}
	}

	public String getConnection( String host ) {
		return host;
	}
}
