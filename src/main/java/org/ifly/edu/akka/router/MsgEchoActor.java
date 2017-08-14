package org.ifly.edu.akka.router;

import akka.actor.UntypedActor;

public class MsgEchoActor extends UntypedActor {
    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println(String.format("Received Message '%s' in Actor %s",
                msg, getSelf().path()));
    }
}
