package com.utils;

import android.util.Log;

import java.lang.ref.SoftReference;

public final class BtsStringBuilder {
    private static final String TAG = "BtsStringBuilder";

    private StringBuilder mStringBuilder;

    private static final int STRING_BUILDER_CAPACITY = 256;

    private static final ThreadLocal<SoftReference<BtsStringBuilder>> TL_INSTANCE = new ThreadLocal<SoftReference<BtsStringBuilder>>() {
        @Override
        protected SoftReference<BtsStringBuilder> initialValue() {
            Log.i("TestViewModel", "initialValue start");
            return new SoftReference<>(new BtsStringBuilder());
        }
    };

    private BtsStringBuilder() {
        mStringBuilder = new StringBuilder(STRING_BUILDER_CAPACITY);
    }

    public static BtsStringBuilder of() {
        SoftReference<BtsStringBuilder> ref = TL_INSTANCE.get();
        BtsStringBuilder builder = ref.get();
        if (builder != null) {
            return builder;
        }
        BtsStringBuilder newBuilder = new BtsStringBuilder();
        SoftReference<BtsStringBuilder> newRef = new SoftReference<>(newBuilder);
        TL_INSTANCE.set(newRef);
        if (newRef.get() == null) {
        }
        return newBuilder;
    }

    public BtsStringBuilder append(String str) {
        mStringBuilder.append(str);
        return this;
    }

    public BtsStringBuilder append(Object object) {
        mStringBuilder.append(object);
        return this;
    }

    public BtsStringBuilder append(boolean b) {
        mStringBuilder.append(b);
        return this;
    }

    public BtsStringBuilder append(char c) {
        mStringBuilder.append(c);
        return this;
    }

    public BtsStringBuilder append(char[] str) {
        mStringBuilder.append(str);
        return this;
    }

    public BtsStringBuilder append(int i) {
        mStringBuilder.append(i);
        return this;
    }

    public BtsStringBuilder append(long lng) {
        mStringBuilder.append(lng);
        return this;
    }

    public BtsStringBuilder append(double d) {
        mStringBuilder.append(d);
        return this;
    }

    public BtsStringBuilder append(float f) {
        mStringBuilder.append(f);
        return this;
    }

    public BtsStringBuilder append(CharSequence s) {
        mStringBuilder.append(s);
        return this;
    }

    public BtsStringBuilder appendAll(Object... s) {
        if (s == null) {
            return this;
        }
        for (Object o : s) {
            mStringBuilder.append(o);
        }
        return this;
    }

    public int length() {
        return mStringBuilder.length();
    }

    /**
     * ??????<b>of() --- toString()</b>???????????????????????????????????????????????????????????????
     */
    public String toString() {
        String value = mStringBuilder.toString();
        clearLength();
        return value;
    }

    /**
     * ?????????????????????Builder??????????????????????????????????????????
     */
    private void clearLength() {
        mStringBuilder.setLength(0);
    }

    /**
     * ?????????????????????BtsStringBuilder??????
     */
    public void clear() {
        TL_INSTANCE.remove();
    }
}