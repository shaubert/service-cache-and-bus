package com.shaubert.vertebra;

import android.os.Bundle;
import com.shaubert.lifecycle.objects.LifecycleBasedObject;
import de.greenrobot.event.EventBus;

public class BusBasedObject extends LifecycleBasedObject implements IDSource {

    private EventBus bus;
    private ID id;

    public BusBasedObject(EventBus bus) {
        this.bus = bus;
    }

    @Override
    protected void onCreate(Bundle state) {
        id = new ID(state);
    }

    public EventBus getBus() {
        return bus;
    }

    @Override
    public ID getID() {
        if (id == null) {
            throw new IllegalStateException("accessing ID before onCreate()");
        }
        return id;
    }

    @Override
    protected void onResume() {
        try {
            bus.registerSticky(this);
        } catch (Exception ignored) {
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
    }

}