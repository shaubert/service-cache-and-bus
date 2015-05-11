package com.shaubert.vertebra;

import com.shaubert.network.service.RSEvent;
import com.shaubert.network.service.Response;

import java.util.HashSet;
import java.util.Set;

public class TargetEvent<OK extends Response<OK>, FAIL> extends RSEvent<OK, FAIL> {

    private Set<String> handledIds = new HashSet<>();

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
        handledIds.add(id.toString());

        if (mine(id)) {
            TargetRequest targetRequest = getRequestSafe(TargetRequest.class);
            targetRequest.setTarget((ID)null);
        }
    }

    public boolean isHandled(IDSource idSource) {
        return isHandled(idSource.getID());
    }

    private boolean isHandled(ID id) {
        return handledIds.contains(id.toString());
    }

}