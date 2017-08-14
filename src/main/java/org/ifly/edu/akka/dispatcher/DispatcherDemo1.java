package org.ifly.edu.akka.dispatcher;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
 
/**
 * Created by pangzhw on 2015/1/13.
 */
public class DispatcherDemo1 {
 
    public static void main(String args[]) throws Exception {
        ActorSystem system = ActorSystem.create("myActorSystem");
        ActorRef myActor = system.actorOf(Props.create(MyActor.class, 54, 65).
                withDispatcher("my-dispatcher"), "myactor");
 
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(myActor, "are you ready?", timeout);
 
        // This will cause the current thread to block and wait for the UntypedActor to ‘complete’
        // the Future with it’s reply.
        // 在这里会阻塞到 Await.result 方法上，但这会导致性能的损失。
        Integer result = (Integer) Await.result(future, timeout.duration());
        System.out.println(result);
    }
}
