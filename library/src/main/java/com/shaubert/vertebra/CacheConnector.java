package com.shaubert.vertebra;

import com.shaubert.cache.*;
import com.shaubert.network.service.RSCache;
import com.shaubert.network.service.RSEvent;
import com.shaubert.network.service.Request;
import com.shaubert.network.service.Response;

public class CacheConnector implements RSCache {

    private Cache cache;

    public CacheConnector(Cache cache) {
        this.cache = cache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void put(RSEvent event) {
        Request request = event.getRequest();
        Response success = event.getSuccess();

        Entry entry = getEntry(request);
        entry.setState(mapEventStatusToDataState(event.getStatus()));
        if (success != null) {
            entry.setValue(success);
        }
    }

    private Entry getEntry(Request request) {
        return cache.get(request.getResponseClass(), request.getQualifier());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void get(Request request, Callback callback) {
        Entry entry = getEntry(request);
        if (entry instanceof AsyncEntry) {
            ((AsyncEntry) entry).getValue(new DataCallbackAdapter(request, callback));
        } else {
            callback.onResultFromCache(request, (Response) entry.getValue());
        }
    }

    public DataState mapEventStatusToDataState(RSEvent.Status status) {
        switch (status) {
            case SUCCESS:
                return DataState.IDLE;
            case RUNNING:
                return DataState.UPDATE;
            case CANCELLED:
                return DataState.IDLE;
            case FAILURE:
                return DataState.FAIL;
            default:
                throw new IllegalArgumentException("unable to map " + status);
        }
    }

    private class DataCallbackAdapter implements DataCallback {
        private Request request;
        private Callback callback;

        private DataCallbackAdapter(Request request, Callback callback) {
            this.request = request;
            this.callback = callback;
        }

        @Override
        public void onDataResult(Object o) {
            callback.onResultFromCache(request, (Response) o);
        }
    }

}
