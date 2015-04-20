package com.shaubert.vertebra;

import com.shaubert.lifecycle.objects.LifecycleObjectsGroup;
import de.greenrobot.event.EventBus;

public class BusBasedGroup extends LifecycleObjectsGroup {

    private EventBus bus;

    public BusBasedGroup(EventBus bus) {
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