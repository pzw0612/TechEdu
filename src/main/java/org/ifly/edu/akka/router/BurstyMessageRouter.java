package org.ifly.edu.akka.router;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.ActorRefRoutee;
import akka.routing.BroadcastRoutingLogic;
import akka.routing.CustomRouterConfig;
import akka.routing.Routee;
import akka.routing.Router;
 
import java.util.ArrayList;
import java.util.List;
 
public class BurstyMessageRouter extends CustomRouterConfig {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int noOfInstances;
 
    public BurstyMessageRouter(int inNoOfInstances) {
        noOfInstances = inNoOfInstances;
    }
 
    @Override
    public Router createRouter(ActorSystem system) {
        final List<Routee> routees = new ArrayList<Routee>(noOfInstances);
        for (int i = 0; i < noOfInstances; i++) {
            routees.add(new ActorRefRoutee(system.actorOf(
                    Props.create(MsgEchoActor.class), "actor-" + String.valueOf(i))));
        }
        return new Router(new BroadcastRoutingLogic(), routees);
    }
}
