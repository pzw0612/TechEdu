package org.ifly.edu.akka.router;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
 
public class Example {
    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        ActorSystem _system = ActorSystem.create("CustomRouterExample");
        ActorRef burstyMessageRouter = _system.actorOf(Props.create(
                MsgEchoActor.class).withRouter(new BurstyMessageRouter(3)), "MsgEchoActor");
 
        //sends series of messages in a broadcast way to all the actors
        burstyMessageRouter.tell("are you ready?", ActorRef.noSender());
 
        Thread.sleep(2000);
        _system.shutdown();
    }
 
}
