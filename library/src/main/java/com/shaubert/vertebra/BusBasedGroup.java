package com.shaubert.vertebra;

import com.shaubert.lifecycle.objects.LifecycleObjectsGroup;
import de.greenrobot.event.EventBus;

public class BusBasedGroup extends LifecycleObjectsGroup implements IDSource {

    private EventBus bus;
    private ID id;

    public BusBasedGroup(EventBus bus) {
        this.bus = bus;
        attachToLifecycle(id);
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