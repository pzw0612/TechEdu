package org.ifly.edu.akka.inbox;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
 
import java.util.concurrent.TimeUnit;
 
/**
 * Created by liyanxin on 2015/1/12.
 */
public class InboxDemo {
    public static void main(String args[]) {
        ActorSystem system = ActorSystem.create("myActorSystem");
        Inbox inbox = Inbox.create(system);
        ActorRef cal = system.actorOf(Props.create(MyActor.class, 5, 7), "myActor");
        inbox.send(cal, "hello world!");
        Integer result = (Integer) inbox.receive((FiniteDuration) Duration.create(1, TimeUnit.SECONDS));
        System.out.println("result=" + result);
    }
}