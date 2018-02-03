package com.example.dovebook.net;

import com.example.dovebook.base.model.User;
import com.example.dovebook.share.model.Moment;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Observable<List<Moment>> selectAllMoment(@Path("startPosition") int startPosition,
                                             @Path("endPosition") int endPosition);

    /**
     * 得到所有好友的分享，需要用户登录
     * @param userId 用户id
     * @param startPosition 起始位置
     * @param endPosition 末位置
     * @return Rxjava的Observable对象
     */
    @GET("moment/friend/{userId}/{startPosition}/{endPosition}")
    Observable<List<Moment>> selectMomentsFromAllFriend(@Path("userId") String userId,
                                                  @Path("startPosition") int startPosition,
                                                  @Path("endPosition") int endPosition);

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
