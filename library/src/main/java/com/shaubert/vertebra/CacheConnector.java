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

        switch (event.getStatus()) {
            case QUEUED:
            case RUNNING:
                entry.addMark(Entry.UPDATING_MARK);
                break;
            case SUCCESS:
                entry.removeMark(Entry.UPDATING_MARK);
                entry.removeMark(Entry.FAILURE_MARK);
                entry.removeMark(Entry.DIRTY_MARK);
                break;
            case CANCELLED:
                entry.removeMark(Entry.UPDATING_MARK);
                break;
            case FAILURE:
                entry.removeMark(Entry.UPDATING_MARK);
                entry.addMark(Entry.FAILURE_MARK);
                break;
        }

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
