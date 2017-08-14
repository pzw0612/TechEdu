package org.ifly.edu.akka.util;



/**
 * Akka 设置延迟执行的Future_Patterns. after
 * @author pangzhiwang
 *
 */
import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import akka.dispatch.OnComplete;
import akka.pattern.Patterns;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
 
import java.util.Arrays;
import java.util.concurrent.Callable;
 
/**
 * Created by liyanxin on 2015/1/9.
 */
public class PatternAfterDemo {
 
    public static void main(String args[]) {
 
        ActorSystem system = ActorSystem.create("myActorSystem");
        final ExecutionContext ec = system.dispatcher();
 
        final Future<String> failExc = Futures.failed(new IllegalStateException("OHNOES1"));
        Future<String> delayed = Patterns.after(Duration.create(200, "millis"),
                system.scheduler(), ec, failExc);
        Future<String> future = Futures.future(new Callable<String>() {
            public String call() throws InterruptedException {
                Thread.sleep(1000);
                return "foo";
            }
        }, ec);
        Future<String> result = Futures.firstCompletedOf(Arrays.asList(future, delayed), ec);
        result.onComplete(new OnComplete<String>() {
            @Override
            public void onComplete(Throwable failure, String success) throws Throwable {
                if (failure != null) {
                    failure.printStackTrace();
                } else {
                    System.out.println(success);
                }
            }
        }, ec);
    }
}
