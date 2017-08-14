package org.ifly.edu.akka.supervisor;

import akka.actor.UntypedActor;

public class Child extends UntypedActor {
 
    // 该Actor内部的状态，该Actor发生异常时，由父Actor负责监控和处理异常（根据监控策略）
    private int state = 0;
 
    public void onReceive(Object o) throws Exception {
        if (o instanceof Exception) {
            throw (Exception) o;
        } else if (o instanceof Integer) {
            state = (Integer) o;
        } else if (o.equals("get")) {
            getSender().tell(state, getSelf());
        } else {
            unhandled(o);
        }
    }
}
