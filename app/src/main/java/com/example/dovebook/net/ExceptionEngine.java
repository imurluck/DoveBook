package com.example.dovebook.net;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.text.ParseException;

import retrofit2.HttpException;

public class ExceptionEngine {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;

    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {
        ApiException ae;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ae = new ApiException(e, ((HttpException) e).code());
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ae.message = "网络错误";
                    break;
            }
            return ae;
        } else if (e instanceof ServerException) {
            ServerException serverException = (ServerException) e;
            ae = new ApiException(serverException, serverException.code);
            ae.message = serverException.message;
            return ae;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ae = new ApiException(e, ERROR.PARSE_ERROR);
            ae.message = "解析错误";
            return ae;
        } else if (e instanceof ConnectException) {
            ae = new ApiException(e, ERROR.NETWORK_ERROR);
            ae.message = "连接失败";
            return ae;
        } else {
            ae = new ApiException(e, ERROR.UNKNOW);
            ae.message = "未知错误";
            return ae;
        }
    }

    public class ERROR {
        //未知错误
        public static final int UNKNOW = 1000;
        //解析错误
        public static final int PARSE_ERROR = 1001;
        //网络错误
        public static final int NETWORK_ERROR = 1002;
        //协议错误
        public static final int HTTP_ERROR = 1003;
    }
}
