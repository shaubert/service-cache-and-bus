package com.shaubert.vertebra;

import com.shaubert.lifecycle.objects.LifecycleBasedObject;
import de.greenrobot.event.EventBus;

public class BusBasedObject extends LifecycleBasedObject implements IDSource {

    private EventBus bus;
    private ID id;

    public BusBasedObject(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public ID getID() {
        return id;
    }

    @Override
    protected void onResume() {
        bus.registerSticky(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
    }

}