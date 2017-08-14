package org.ifly.edu.akka.inbox;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import akka.actor.Terminated;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
 
import java.util.concurrent.TimeUnit;
 
/**
 * Created by liyanxin on 2015/1/12.
 */
public class InboxWatchDemo {
 
    public static void main(String args[]) {
        ActorSystem system = ActorSystem.create("myActorSystem");
        Inbox inbox = Inbox.create(system);
        ActorRef cal = system.actorOf(Props.create(MyActor.class, 5, 7), "myActor");
        inbox.watch(cal);
    
        cal.tell("good afternoon", ActorRef.noSender()); //这个消息没有发送者
        Terminated terminated = (Terminated) inbox.receive((FiniteDuration) Duration.create(1, TimeUnit.SECONDS));
        System.out.println(terminated.toString());
        System.out.println(terminated.getActor().path());//actor path = akka://myActorSystem/user/myActor
        
        system.shutdown();
    }
}
