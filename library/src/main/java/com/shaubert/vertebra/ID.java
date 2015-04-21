package com.shaubert.vertebra;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.shaubert.lifecycle.objects.LifecycleBasedObject;

import java.util.UUID;

public class ID extends LifecycleBasedObject implements Parcelable {

    private String value = UUID.randomUUID().toString();

    public ID() {
    }

    public ID(Bundle savedInstanceState) {
        onRestoreInstanceState(savedInstanceState);
    }

    public ID(Parcel in) {
        if (in != null) {
            this.value = in.readString();
        }
    }

    public void regenerate() {
        value = UUID.randomUUID().toString();
    }

    @Override
    protected void onRestoreInstanceState(Bundle stateBundle) {
        if (stateBundle != null) {
            String id = stateBundle.getString("__id");
            if (!TextUtils.isEmpty(id)) {
                value = id;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("__id", value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ID id = (ID) o;

        if (!value.equals(id.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value);
    }

    public static final Parcelable.Creator<ID> CREATOR = new Parcelable.Creator<ID>() {
        public ID createFromParcel(Parcel source) {
            return new ID(source);
        }

        public ID[] newArray(int size) {
            return new ID[size];
        }
    };
}
