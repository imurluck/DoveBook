package com.example.dovebook.bean;

public class OrderBean {
    private String ordersId;
    private boolean ordersStates;
    private String copyId;
    private String preordersId;
    private String userId;
    private int ordersCredit;
    private long ordersStart;
    private long ordersEnd;
    private long createdat;
    private long updatedat;

    public OrderBean (){

    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public boolean isOrdersStates() {
        return ordersStates;
    }

    public void setOrdersStates(boolean ordersStates) {
        this.ordersStates = ordersStates;
    }

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public String getPreordersId() {
        return preordersId;
    }

    public void setPreordersId(String preordersId) {
        this.preordersId = preordersId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOrdersCredit() {
        return ordersCredit;
    }

    public void setOrdersCredit(int ordersCredit) {
        this.ordersCredit = ordersCredit;
    }

    public long getOrdersStart() {
        return ordersStart;
    }

    public void setOrdersStart(long ordersStart) {
        this.ordersStart = ordersStart;
    }

    public long getOrdersEnd() {
        return ordersEnd;
    }

    public void setOrdersEnd(long ordersEnd) {
        this.ordersEnd = ordersEnd;
    }

    public long getCreatedat() {
        return createdat;
    }

    public void setCreatedat(long createdat) {
        this.createdat = createdat;
    }

    public long getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(long updatedat) {
        this.updatedat = updatedat;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "ordersId='" + ordersId + '\'' +
                ", ordersStates=" + ordersStates +
                ", copyId='" + copyId + '\'' +
                ", preordersId='" + preordersId + '\'' +
                ", userId='" + userId + '\'' +
                ", ordersCredit=" + ordersCredit +
                ", ordersStart=" + ordersStart +
                ", ordersEnd=" + ordersEnd +
                ", createdat=" + createdat +
                ", updatedat=" + updatedat +
                '}';
    }
}
