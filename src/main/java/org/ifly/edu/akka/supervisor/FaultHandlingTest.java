package org.ifly.edu.akka.supervisor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
 
import static akka.pattern.Patterns.ask;
 
/**
 * Created by pangzhw on 2015/1/14.
 */
public class FaultHandlingTest {
 
    public static void main(String args[]) throws Exception {
        ActorSystem system = ActorSystem.create("myActorSystem");
        Props superprops = Props.create(SupervisorActor.class);
        ActorRef supervisor = system.actorOf(superprops, "supervisor");
 
        // 由监控Actor产生一个子Actor
        ActorRef child = (ActorRef) Await.result(ask(supervisor,
                Props.create(Child.class), 5000), Duration.create(5, "s"));
 
        child.tell(42, ActorRef.noSender());
        Integer result1 = (Integer) Await.result(ask(child, "get", 5000), Duration.create(5, "s"));
        System.out.println(result1);
 
        // 导致child actor 抛出异常，由于监控策略，还会保持该Actor内部的状态
        // child actor resume
        child.tell(new ArithmeticException(), ActorRef.noSender());
        Integer result2 = (Integer) Await.result(ask(child, "get", 5000), Duration.create(5, "s"));
        System.out.println(result2);
 
 
        // 导致child actor 抛出NPE，由于监控策略，导致child actor restart
        child.tell(new NullPointerException(), ActorRef.noSender());
        Integer result3 = (Integer) Await.result(ask(child, "get", 5000), Duration.create(5, "s"));
        System.out.println(result3); // 0
 
        // IllegalArgumentException 异常导致child actor stop
        child.tell(new IllegalArgumentException(), ActorRef.noSender());
        Integer result4 = (Integer) Await.result(ask(child, "get", 5000), Duration.create(5, "s"));
        System.out.println(result4); // 0
        
        system.shutdown();
        
    }
}
