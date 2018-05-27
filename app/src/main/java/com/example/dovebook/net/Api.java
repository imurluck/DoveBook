package com.example.dovebook.net;

import com.example.dovebook.base.model.User;
import com.example.dovebook.share.model.Moment;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Created by zzx on 18-1-26.
 */

public interface Api {
    /**
     * 通过userId得到此用户的分享，无需要用户登录
     * @param userId 用户Id
     * @param startPosition 起始位置
     * @param endPosition 末位置
     * @return Rxjava的observable对象
     */
    @GET("moment/list/{userId}/{startPosition}/{endPosition}")
    Observable<List<Moment>> selectMomentsByUserId(@Path("userId") String userId,
                                               @Path("startPosition") int startPosition,
                                               @Path("endPosition") int endPosition);

    /**
     * 得到所有用户的分享，无需用户登录
     * @param startPosition 起始位置
     * @param endPosition 末位置
     * @return Rxjava的Observable对象
     */
    @GET("moment/all/{startPosition}/{endPosition}")
    Observable<Response<List<Moment>>> selectAllMoment(@Path("startPosition") int startPosition,
                                             @Path("endPosition") int endPosition);

    /**
     * 得到所有好友的分享，需要用户登录
     * @param userId 用户id
     * @param startPosition 起始位置
     * @param endPosition 末位置
     * @return Rxjava的Observable对象
     */
    @GET("moment/friend/{userId}/{startPosition}/{endPosition}")
    Observable<Response<List<Moment>>> selectMomentsFromAllFriend(@Path("userId") String userId,
                                                                 @Path("startPosition") int startPosition,
                                                                 @Path("endPosition") int endPosition);

    /**
     * 发表自己的一个分享
     * @param paraMap form-data表单中的参数,
     * //////////////////////////////////////////////////////////////////////////////
     * paraMap = new HashMap<String, RequestBody>();
     * RequestBody requestBody = RequestBody.create(MediaType.parse("[{\"key\":\"Content-Type\",\"value\":\"application/x-www-form-urlencoded\",\"description\":\"\"}]"), mUserId);
     * paraMap.put("userId", requestBody);
     * RequestBody cmomentDetaillocBody = RequestBody.create(MediaType.parse(type), momentDetail);
     * paraMap.put("cmomentDetailloc", cmomentDetaillocBody);
     * RequestBody momentLatitudeBody = RequestBody.create(MediaType.parse(type), String.valueOf(latitude));
     * paraMap.put("momentLatitude", momentLatitudeBody);
     * RequestBody momentLongitudeBody = RequestBody.create(MediaType.parse(type), String.valueOf(longitude));
     * paraMap.put("momentLongitude", momentLongitudeBody);
     * RequestBody momentContentBody = RequestBody.create(MediaType.parse(type), momentContent);
     * paraMap.put("momentContent", momentContentBody);
     * 注意每一项的contentType不能为json类型，否则会上传不成功
     *  即{@link okhttp3.MediaType} 设置的类型类型
     * //////////////////////////////////////////////////////////////////////
     * @param momentPics 多文件 使用{@link MultipartBody.Part} 数组进行上传
     * @return
     */
    @Multipart
    @POST("momentpic/")
    Observable<Response<Moment>> addOneMoment(@PartMap Map<String, RequestBody> paraMap,
                                              @Part List<MultipartBody.Part> momentPics);

    /**
     * 登录
     * @param userName 用户名
     * @param userPassword 用户密码
     * @return Rxjava的observable对象
     */
    @POST("{userName}/{userPassword}")
    Observable<User> login(@Path("userName") String userName,
                           @Path("userPassword") String userPassword);

}
