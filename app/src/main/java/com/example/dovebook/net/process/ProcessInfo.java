package com.example.dovebook.net.process;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用于记录网络请求的进度信息的类
 */
public class ProcessInfo implements Parcelable {
    //已经完成的长度
    private long currentBytes;
    //总长度
    private long totleBytes;
    //两次记录的间隔时间
    private long intervalTime;
    //每次记录的长度
    private long eachBytes;

    private boolean isFinish;

    public ProcessInfo() {

    }

    public long getCurrentBytes() {
        return currentBytes;
    }

    public void setCurrentBytes(long currentBytes) {
        this.currentBytes = currentBytes;
    }

    public long getTotleBytes() {
        return totleBytes;
    }

    public void setTotleBytes(long totleBytes) {
        this.totleBytes = totleBytes;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public long getEachBytes() {
        return eachBytes;
    }

    public void setEachBytes(long eachBytes) {
        this.eachBytes = eachBytes;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    /**
     * 计算进度
     * @return 返回0 - 100 之间的数
     */
    public int getProcess() {
        if (getCurrentBytes() <= 0 || getTotleBytes() <= 0)
            return 0;
        return (int) ((100 * getCurrentBytes()) / getTotleBytes());
    }

    /**
     * 计算网络请求速度
     * @return bytes / s
     */
    public long getSpeed() {
        if (getEachBytes() <= 0 || getIntervalTime() <= 0)
            return 0;
        return getEachBytes() * 1000 / getIntervalTime();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(currentBytes);
        parcel.writeLong(totleBytes);
        parcel.writeLong(intervalTime);
        parcel.writeLong(eachBytes);
        parcel.writeByte(isFinish ? (byte) 1 : (byte) 0);
    }

    protected ProcessInfo(Parcel in) {
        currentBytes = in.readLong();
        totleBytes = in.readLong();
        intervalTime = in.readLong();
        eachBytes = in.readLong();
        isFinish = in.readByte() == 1;
    }

    public static final Creator<ProcessInfo> CREATOR = new Creator<ProcessInfo>() {
        @Override
        public ProcessInfo createFromParcel(Parcel parcel) {
            return new ProcessInfo(parcel);
        }

        @Override
        public ProcessInfo[] newArray(int size) {
            return new ProcessInfo[size];
        }
    };
}
