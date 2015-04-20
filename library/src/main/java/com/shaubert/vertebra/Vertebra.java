package com.shaubert.vertebra;

import com.shaubert.cache.Cache;
import com.shaubert.cache.DefaultCache;
import com.shaubert.cache.DefaultEntryFactory;
import com.shaubert.cache.DefaultEntryKeyFactory;
import com.shaubert.network.service.ServiceConfig;
import de.greenrobot.event.EventBus;

public class Vertebra {

    private static Vertebra instance;

    public static Vertebra get() {
        return instance;
    }

    public static void set(Vertebra instance) {
        Vertebra.instance = instance;
    }

    private Cache cache;
    private EventBus bus;

    private Vertebra(Builder builder) {
        cache = builder.cache;
        bus = builder.bus;

        if (builder.setAsInstance) {
            set(this);
        }
    }

    public ServiceConfig.Builder createBaseConfigBuilder() {
        return ServiceConfig.newBuilder()
                .bus(new BusConnector(bus))
                .cache(new CacheConnector(cache));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Cache getCache() {
        return cache;
    }

    public EventBus getBus() {
        return bus;
    }

    public static final class Builder {
        private Cache cache;
        private EventBus bus;
        private boolean setAsInstance;

        private Builder() {
        }

        public Builder cache(Cache cache) {
            this.cache = cache;
            return this;
        }

        public Builder bus(EventBus bus) {
            this.bus = bus;
            return this;
        }

        public Builder setAsInstance(boolean value) {
            this.setAsInstance = value;
            return this;
        }

        public Vertebra build() {
            if (cache == null) {
                createDefaultCache();
            }
            if (bus == null) {
                createDefaultBus();
            }
            return new Vertebra(this);
        }

        private void createDefaultBus() {
            bus = EventBus.builder()
                    .eventInheritance(true)
                    .logNoSubscriberMessages(false)
                    .sendNoSubscriberEvent(false)
                    .sendSubscriberExceptionEvent(false)
                    .throwSubscriberException(true)
                    .logSubscriberExceptions(false)
                    .installDefaultEventBus();
        }

        private void createDefaultCache() {
            DefaultEntryKeyFactory keyFactory = new DefaultEntryKeyFactory();
            DefaultEntryFactory entryFactory = new DefaultEntryFactory(keyFactory);
            cache = new DefaultCache(keyFactory, entryFactory);
        }
    }
}
