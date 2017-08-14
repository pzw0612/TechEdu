package org.ifly.edu.akka.inbox;

import akka.actor.UntypedActor;

/**
 * Created by liyanxin on 2015/1/12.
 */
public class MyActor extends UntypedActor {
 
    private int x;
    private int y;
 
    public MyActor(int x, int y) {
        this.x = x;
        this.y = y;
    }
 
    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("message=" + message);
        int result = x + y;
        this.getSender().tell(result, this.getSelf());
        this.getContext().stop(this.getSelf());
    }
}
