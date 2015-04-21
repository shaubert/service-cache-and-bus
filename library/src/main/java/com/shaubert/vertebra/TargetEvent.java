package com.shaubert.vertebra;

import com.shaubert.network.service.RSEvent;
import com.shaubert.network.service.Response;

public class TargetEvent<OK extends Response<OK>, FAIL> extends RSEvent<OK, FAIL> {

    public TargetEvent(Status status, Object responseOrFailure) {
        super(status, responseOrFailure);
    }

    public boolean mine(IDSource idSource) {
        return mine(idSource.getID());
    }

    public boolean mine(ID id) {
        TargetRequest targetRequest = getRequestSafe(TargetRequest.class);
        ID target = targetRequest != null ? targetRequest.getTarget() : null;
        return target != null && target.equals(id);
    }

    public void setHandled(IDSource idSource) {
        setHandled(idSource.getID());
    }

    public void setHandled(ID id) {
        if (mine(id)) {
            TargetRequest targetRequest = getRequestSafe(TargetRequest.class);
            targetRequest.setTarget((ID)null);
        }
    }

}