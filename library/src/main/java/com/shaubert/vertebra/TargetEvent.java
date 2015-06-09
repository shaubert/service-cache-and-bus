package com.shaubert.vertebra;

import android.os.Bundle;
import com.shaubert.network.service.RSEvent;
import com.shaubert.network.service.Response;

import java.util.HashSet;
import java.util.Set;

public class TargetEvent<OK extends Response<OK>, FAIL> extends RSEvent<OK, FAIL> {

    private static final ID NOT_SET_ID = new ID((Bundle) null);

    private Set<String> handledIds = new HashSet<>();
    private ID target = NOT_SET_ID;

    public TargetEvent(Status status, Object responseOrFailure) {
        super(status, responseOrFailure);
    }

    public void setTarget(IDSource idSource) {
        setTarget(idSource != null ? idSource.getID() : null);
    }

    public void setTarget(ID id) {
        target = id;
    }

    public ID getTarget() {
        if (target != NOT_SET_ID) return target;

        TargetRequest targetRequest = getRequestSafe(TargetRequest.class);
        return targetRequest != null ? targetRequest.getTarget() : null;
    }

    public boolean mine(IDSource idSource) {
        return mine(idSource != null ? idSource.getID() : null);
    }

    public boolean mine(ID id) {
        ID target = getTarget();
        return target != null && target.equals(id);
    }

    public void setHandled(IDSource idSource) {
        setHandled(idSource != null ? idSource.getID() : null);
    }

    public void setHandled(ID id) {
        if (id == null) return;

        handledIds.add(id.toString());

        if (mine(id)) {
            TargetRequest targetRequest = getRequestSafe(TargetRequest.class);
            targetRequest.setTarget((ID)null);
        }
    }

    public boolean isHandled(IDSource idSource) {
        return isHandled(idSource != null ? idSource.getID() : null);
    }

    public boolean isHandled(ID id) {
        if (id == null) return false;

        return handledIds.contains(id.toString());
    }

}