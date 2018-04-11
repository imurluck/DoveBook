package com.example.dovebook.book.model;

/**
 * Created by 28748 on 2018/3/14.
 */

public class Copy{

    private String mCopyId;

    private Boolean mCopyStatus;

    private String mBookId;

    private String mUserId;

    private String mCopyLatitude;

    private String mCopyLongitude;

    private String mCopyDetailloc;

    private long mCreatedat;

    private long mUpdatedat;

    public String getCopyId() {
        return mCopyId;
    }

    public void setCopyId(String copyId) {
        this.mCopyId = copyId == null ? null : copyId.trim();
    }

    public Boolean getCopyStatus() {
        return mCopyStatus;
    }

    public void setCopyStatus(Boolean copyStatus) {
        this.mCopyStatus = copyStatus;
    }

    public String getBookId() {
        return mBookId;
    }

    public void setBookId(String bookId) {
        this.mBookId = bookId == null ? null : bookId.trim();
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        this.mUserId = userId == null ? null : userId.trim();
    }

    public String getCopyLatitude() {
        return mCopyLatitude;
    }

    public void setCopyLatitude(String copyLatitude) {
        this.mCopyLatitude = copyLatitude == null ? null : copyLatitude.trim();
    }

    public String getCopyLongitude() {
        return mCopyLongitude;
    }

    public void setCopyLongitude(String copyLongitude) {
        this.mCopyLongitude = copyLongitude == null ? null : copyLongitude.trim();
    }

    public String getCopyDetailloc() {
        return mCopyDetailloc;
    }

    public void setCopyDetailloc(String copyDetailloc) {
        this.mCopyDetailloc = copyDetailloc == null ? null : copyDetailloc.trim();
    }

    public long getCreatedat() {
        return mCreatedat;
    }

    public void setCreatedat(long createdat) {
        this.mCreatedat = createdat;
    }

    public long getUpdatedat() {
        return mUpdatedat;
    }

    public void setUpdatedat(long updatedat) {
        this.mUpdatedat = updatedat;
    }


}
