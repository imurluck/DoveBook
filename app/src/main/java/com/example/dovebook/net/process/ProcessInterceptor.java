package com.example.dovebook.net.process;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 为了获取网络请求进度的拦截器，用于包装请求报文和响应报文
 */
public class ProcessInterceptor implements Interceptor {
    //进度监听器，不能为空
    private ProcessListener mProcessListener;

    private static final long DEFAULT_REFRESH_TIME = 150;
    //刷新时间，即两次进度刷新的间隔时间
    private long mRefreshTime = DEFAULT_REFRESH_TIME;

    public ProcessInterceptor(@Nullable ProcessListener listener) {
        this.mProcessListener = listener;
    }

    public void setRefreshTime(long time) {
        mRefreshTime = time;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return wrapResponseBody(chain.proceed(wrapRequestBody(chain.request())));
    }

    /**
     * 包装请求体
     * @param request
     * @return
     */
    private Request wrapRequestBody(Request request) {
        if (request == null) {
            return null;
        }
        return request.newBuilder()
                .method(request.method(), new ProcessRequestBody(mProcessListener, request.body()
                        , mRefreshTime))
                .build();
    }

    /**
     * 包装响应体
     * @param response
     * @return
     */
    private Response wrapResponseBody(Response response) {
        if (response == null) {
            return null;
        }
        return response.newBuilder()
                .body(new ProcessResponseBody(mProcessListener, response.body(),
                        mRefreshTime))
                .build();
    }
}
