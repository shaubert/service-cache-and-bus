package com.shaubert.vertebra;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.UUID;

public class ID implements Parcelable {

    private final String value;

    public ID(@Nullable Bundle savedInstanceState) {
        String id = null;
        if (savedInstanceState != null) {
            id = savedInstanceState.getString("__id");
        }
        if (TextUtils.isEmpty(id)) {
            id = generateId();
        }
        value = id;
    }

    protected ID(Parcel in) {
        if (in != null) {
            this.value = in.readString();
        } else {
            this.value = generateId();
        }
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
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
