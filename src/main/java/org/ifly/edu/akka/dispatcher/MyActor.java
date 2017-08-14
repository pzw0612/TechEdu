package org.ifly.edu.akka.dispatcher;

import akka.actor.UntypedActor;


public class MyActor extends UntypedActor {
    private int x;
    private int y;
 
    public MyActor(int x, int y) {
        this.x = x;
        this.y = y;
    }
 
    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("接收到的消息=" + message);
        int result = x + y;
        this.getSender().tell(result, this.getSelf());
        this.getContext().stop(this.getSelf());
    }
}
