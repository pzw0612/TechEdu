package org.ifly.edu.arithmetic.weightRobin;


 
/**
 * 权重轮询策略
 * 
 * @author pangzhiwang
 * @date 2016-10-19
 */
public class WeightRoundRobinStrategy {
	
	// 机器序号：权重
    private static long[] group = null;
    private static long cw = 0;
    // 当前group的序号,开始是-1
    private static int number = -1;
    // 最大权重
    private static long max;
    // 最大公约数
    private static long gcd;
 
    public static void setGroup(long[] tmpGroup){
    	if(tmpGroup==null || tmpGroup.length==0){
    		throw new RuntimeException("无分组信息");
    	}
    	group = tmpGroup;
        max = getMaxWeight(group);
        gcd = gcd(group);
        cw=0;
    }
     
    /**
     * 求最大公约数
     * 
     * @param array
     * @return
     */
    private static long gcd(long[] ary) {
 
    	long min = ary[0];
 
        for (int i = 0; i < ary.length; i++) {
            if (ary[i] < min) {
                min = ary[i];
            }
        }
        while (min >= 1) {
            boolean isCommon = true;
            for (int i = 0; i < ary.length; i++) {
                if (ary[i] % min != 0) {
                    isCommon = false;
                    break;
                }
            }
            if (isCommon) {
                break;
            }
            min--;
        }
        // System.out.println("gcd=" + min);
        return min;
    }
 
    /**
     * 求最大值，权重
     * 
     * @return
     */
 
    private static long getMaxWeight(long[] ary) {
    	long max = 0;
        for (int i = 0; i < ary.length; i++) {
            if (max < ary[i]) {
                max = ary[i];
            }
        }
        return max;
    }
 
    /**
     * 获取请求的group序号
     * 
     * @return
     */
    public static synchronized Integer next() {
    	
    	if(group==null || group.length==0){
    		throw new RuntimeException("无分组信息");
    	}
    	
        while (true) {
            number = (number + 1) % group.length;
            if (number == 0) {
                cw = cw - gcd;// cw比较因子，从最大权重开始，以最大公约数为步长递减
                if (cw <= 0) {//
                    cw = max;
                    if (cw == 0)
                        return null;
                }
            }
            if (group[number] >= cw)
                return number;
        }
    }
 
    public static void main(String[] args) {
//        try {
//        	
//        	int weightSum = 0;
//        	for (long weight : group) {
//        		weightSum+= weight;
//			}
//        	
//            ExecutorService pool = Executors.newFixedThreadPool(1);
//            for (int i = 0; i < weightSum; i++) {
//                Runnable run = new Runnable() {
//                    public void run() {
//                        System.out.print(WeightRoundRobinStrategy.next() + ",");
//                    }
//                };
//             
//                pool.execute(run);
//            }
//            // 关闭启动线程
//            pool.shutdown();
//            // 等待子线程结束，再继续执行下面的代码
//            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
//            System.out.println("all thread complete");
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        /*
         * int req = 99; while (req >= 0) {
         * System.out.print(RoundRobinWeightTest.next() + ","); req--; }
         */
    	
    	long weightSum = 0;
    	long[] group = new long[5];
    	group[0] = 2;
    	group[1] = 4;
    	group[2] = 2;
    	group[3] = 2;
    	group[4] = 2;
    	
    	weightSum = calWeight(group);
    	test(group,weightSum);
    	
    	System.out.println("--------------------------------------");
    	
    	group = new long[7];
    	group[0] = 2;
    	group[1] = 4;
    	group[2] = 2;
    	group[3] = 2;
    	group[4] = 2;
    	group[5] = 1;
    	group[6] = 5;
    	weightSum = calWeight(group);
    	test(group,weightSum);
    	
    	System.out.println("--------------------------------------");
    	
    	group = new long[6];
    	group[0] = 2;
    	group[1] = 4;
    	group[2] = 2;
    	group[3] = 2;
    	group[4] = 2;
    	group[5] = 5;

    	weightSum = calWeight(group);
    	test(group,weightSum-1);
    	
    	System.out.println("--------------------------------------");
    	
    	group = new long[6];
    	group[0] = 2;
    	group[1] = 4;
    	group[2] = 2;
    	group[3] = 2;
    	group[4] = 2;
    	group[5] = 5;
    	
    	weightSum = calWeight(group);
    	test(group,weightSum-1);
    	
    	System.out.println("--------------------------------------");
    	
    	group = new long[6];
    	group[0] = 2;
    	group[1] = 4;
    	group[2] = 2;
    	group[3] = 2;
    	group[4] = 2;
    	group[5] = 5;

    	weightSum = calWeight(group);
    	test(group,weightSum);
    	
    }
    
    static long calWeight(long[] group){
    	long weightSum = 0;
    	for (long weight : group) {
    		weightSum+= weight;
		}
    	return weightSum;
    }
    
    static void test(long[] group ,long thisTimes){
    	WeightRoundRobinStrategy.setGroup(group);
    	for(long i=0; i< thisTimes; i++){
    		System.out.print(WeightRoundRobinStrategy.next() + ",");
        }
    	System.out.println();
    	
    }
}

