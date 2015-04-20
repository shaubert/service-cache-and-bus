package com.shaubert.vertebra;

import com.shaubert.lifecycle.objects.LifecycleBasedObject;
import de.greenrobot.event.EventBus;

public class BusBasedObject extends LifecycleBasedObject {

    private EventBus bus;

    public BusBasedObject(EventBus bus) {
        this.bus = bus;
    }

    @Override
    protected void onResume() {
        bus.register(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
    }

}