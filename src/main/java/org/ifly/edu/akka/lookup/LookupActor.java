package org.ifly.edu.akka.lookup;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
 
public class LookupActor extends UntypedActor {
 
    @Override
    public void preStart() {
        // 创建一个名为child的子actor
        getContext().actorOf(Props.create(ChildActor.class), "child");
 
        ActorSelection child = this.getContext().actorSelection("akka://mySystem/user/service/child");
        child.tell("from parent 1", null);
        // 使用相对路径
        child = this.getContext().actorSelection("child");
        child.tell("from parent 2", null);
 
    }
 
    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("service 收到消息 ：" + message);
    }
 
    public static void main(String[] args) {
        // 创建一个actor system，名为mySystem
        ActorSystem system = ActorSystem.create("mySystem");
        // 创建一个名为service的顶级actor
        system.actorOf(Props.create(LookupActor.class), "service");
        // 使用绝对路径获取service
        ActorSelection service = system.actorSelection("akka://mySystem/user/service");
 
        // 给service 这个actor发送消息使用的sender为null，表示没有消息可以回复给这个sender
        // Pass [[akka.actor.ActorRef$.noSender]] or `null` as sender if there is nobody to reply to
        service.tell("sss", null);
        // 使用相对路径(相对于根目录的路径)
        service = system.actorSelection("user/service");
        service.tell("fff", null);
 
        // 使用绝对路径获取child actor
        ActorSelection child = system.actorSelection("akka://mySystem/user/service/child");
        child.tell("sss", null);
        child = system.actorSelection("user/service/child");
        child.tell("fff", null);
        system.shutdown();
    }
 
    static class ChildActor extends UntypedActor {
        @Override
        public void onReceive(Object message) throws Exception {
            System.out.println("child 收到消息: " + message);
        }
    }
 
}
