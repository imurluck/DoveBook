package com.example.dovebook.net.process;

import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.example.dovebook.base.BaseApp;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class ProcessRequestBody extends RequestBody {

    private ProcessListener mProcessListener;

    private RequestBody mDelegate;

    private ProcessInfo mProcessInfo;

    private BufferedSink mBufferedSink;

    private long mRefreshTime;

    public ProcessRequestBody(@Nullable ProcessListener listener,
                              @Nullable RequestBody delegate,
                              long refreshTime) {
        this.mProcessListener = listener;
        mDelegate = delegate;
        mProcessInfo = new ProcessInfo();
        mRefreshTime = refreshTime;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return mDelegate.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mDelegate.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (mBufferedSink == null) {
            mBufferedSink = Okio.buffer(new CountingSink(sink));
        }
        try {
            mDelegate.writeTo(mBufferedSink);
            mBufferedSink.flush();
        } catch (final IOException e) {
            e.printStackTrace();
            BaseApp.getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    mProcessListener.onError(e);
                }
            });
            throw e;
        }
    }

    private class CountingSink extends ForwardingSink {

        private long totalBytesRead = 0L;
        private long lastRefreshTime = 0L;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, final long byteCount) throws IOException {
            try {
                super.write(source, byteCount);
            } catch (final IOException e) {
                e.printStackTrace();
                BaseApp.getMainHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        mProcessListener.onError(e);
                    }
                });
                throw e;
            }

            if (mProcessInfo.getTotleBytes() == 0) {
                mProcessInfo.setTotleBytes(contentLength());
            }
            totalBytesRead += byteCount;
            final long curTime = SystemClock.elapsedRealtime();
            if (curTime - lastRefreshTime >= mRefreshTime
                    || totalBytesRead == mProcessInfo.getTotleBytes()) {
                BaseApp.getMainHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        mProcessInfo.setEachBytes(byteCount);
                        mProcessInfo.setCurrentBytes(totalBytesRead);
                        mProcessInfo.setIntervalTime(curTime - lastRefreshTime);
                        mProcessInfo.setFinish(totalBytesRead >= mProcessInfo.getTotleBytes());
                        mProcessListener.onProcess(mProcessInfo);
                    }
                });
                lastRefreshTime = curTime;
            }
        }
    }
}
