package com.shaubert.vertebra;

import android.os.Parcel;
import com.shaubert.network.service.RSEvent;
import com.shaubert.network.service.Request;
import com.shaubert.network.service.Response;

public abstract class TargetRequest<T extends Response, F> extends Request<T, F> {

    private ID target;

    protected TargetRequest(Parcel in, Class<T> responseClass) {
        super(in, responseClass);

        if (in != null) {
            target = in.readParcelable(ID.class.getClassLoader());
        }
    }

    public ID getTarget() {
        return target;
    }

    public void setTarget(IDSource target) {
        setTarget(target.getID());
    }

    public void setTarget(ID target) {
        this.target = target;
    }

    @Override
    public abstract TargetEvent<T, F> produceEvent(RSEvent.Status status, Object o);

    @Override
    public final void writeChildToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(target, flags);
        writeChild2ToParcel(parcel, flags);
    }

    protected abstract void writeChild2ToParcel(Parcel parcel, int flags);

}
