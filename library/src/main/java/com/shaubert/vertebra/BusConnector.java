package com.shaubert.vertebra;

import com.shaubert.network.service.RSBus;
import com.shaubert.network.service.RSEvent;
import de.greenrobot.event.EventBus;

public class BusConnector implements RSBus {

    private EventBus eventBus;

    public BusConnector(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void post(RSEvent event) {
        if (event instanceof TargetEvent) {
            eventBus.postSticky(event);
        } else {
            eventBus.post(event);
        }
    }

}