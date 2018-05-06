package com.example.dovebook.book.model;

/**
 * Created by zjs on 2018/3/14.
 */

public class Copy{

    private String copyId;

    private Boolean copyStatus;

    private String bookId;

    private String userId;

    private String copyLatitude;

    private String copyLongitude;

    private String copyDetailloc;

    private long createdat;

    private long updatedat;

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId == null ? null : copyId.trim();
    }

    public Boolean getCopyStatus() {
        return copyStatus;
    }

    public void setCopyStatus(Boolean copyStatus) {
        this.copyStatus = copyStatus;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCopyLatitude() {
        return copyLatitude;
    }

    public void setCopyLatitude(String copyLatitude) {
        this.copyLatitude = copyLatitude == null ? null : copyLatitude.trim();
    }

    public String getCopyLongitude() {
        return copyLongitude;
    }

    public void setCopyLongitude(String copyLongitude) {
        this.copyLongitude = copyLongitude == null ? null : copyLongitude.trim();
    }

    public String getCopyDetailloc() {
        return copyDetailloc;
    }

    public void setCopyDetailloc(String copyDetailloc) {
        this.copyDetailloc = copyDetailloc == null ? null : copyDetailloc.trim();
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


}
