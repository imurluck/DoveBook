package com.example.dovebook.net.process;

import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.example.dovebook.base.BaseApp;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProcessResponseBody extends ResponseBody {

    private ProcessListener mProcessListener;

    private ResponseBody mDelegate;

    private long mRefreshTime;

    private ProcessInfo mProcessInfo;

    private BufferedSource mBufferedSource;

    public ProcessResponseBody(@Nullable ProcessListener listener,
                               @Nullable ResponseBody delegate,
                               long refreshTime) {
        mProcessListener = listener;
        mDelegate = delegate;
        mRefreshTime = refreshTime;
        mProcessInfo = new ProcessInfo();
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return mDelegate.contentType();
    }

    @Override
    public long contentLength() {
        return mDelegate.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mDelegate.source()));
        }
        return mBufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {

            private long totalBytesRead = 0L;
            private long lastRefreshTime = 0L;

            @Override
            public long read(Buffer sink, final long byteCount) throws IOException {
                try {
                    super.read(sink, byteCount);
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
                        || totalBytesRead >= mProcessInfo.getTotleBytes()) {
                    BaseApp.getMainHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            mProcessInfo.setCurrentBytes(totalBytesRead);
                            mProcessInfo.setIntervalTime(curTime - lastRefreshTime);
                            mProcessInfo.setFinish(totalBytesRead >= mProcessInfo.getTotleBytes());
                            mProcessInfo.setEachBytes(byteCount);
                            mProcessListener.onProcess(mProcessInfo);
                        }
                    });
                    lastRefreshTime = curTime;
                }
                return byteCount;
            }
        };
    }
}
