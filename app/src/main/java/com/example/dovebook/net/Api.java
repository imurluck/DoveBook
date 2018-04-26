package com.example.dovebook.net;

import com.example.dovebook.base.model.User;
import com.example.dovebook.book.model.Book;
import com.example.dovebook.book.model.Copy;
import com.example.dovebook.share.model.Moment;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zzx on 18-1-26.
 */

public interface Api {
    /**
     * 通过userId得到此用户的分享，无需要用户登录
     *
     * @param userId        用户Id
     * @param startPosition 起始位置
     * @param endPosition   末位置
     * @return Rxjava的observable对象
     */
    @GET("moment/list/{userId}/{startPosition}/{endPosition}")
    Observable<List<Moment>> selectMomentsByUserId(@Path("userId") String userId,
                                                   @Path("startPosition") int startPosition,
                                                   @Path("endPosition") int endPosition);

    /**
     * 得到所有用户的分享，无需用户登录
     *
     * @param startPosition 起始位置
     * @param endPosition   末位置
     * @return Rxjava的Observable对象
     */
    @GET("moment/all/{startPosition}/{endPosition}")
    Observable<List<Moment>> selectAllMoment(@Path("startPosition") int startPosition,
                                             @Path("endPosition") int endPosition);

    /**
     * 得到所有好友的分享，需要用户登录
     *
     * @param userId        用户id
     * @param startPosition 起始位置
     * @param endPosition   末位置
     * @return Rxjava的Observable对象
     */
    @GET("moment/friend/{userId}/{startPosition}/{endPosition}")
    Observable<List<Moment>> selectMomentsFromAllFriend(@Path("userId") String userId,
                                                        @Path("startPosition") int startPosition,
                                                        @Path("endPosition") int endPosition);


    /**
     * 获取一个人的所有图书copy
     *
     * @param userId 用户id
     * @return Rxjava的observable对象
     */
    @GET("usercopy/{userId}/?offset=0&limit=10")
    Observable<List<Copy>> selectAllBookCopy(@Path("userId") String userId);

    /**
     * 通过bookId获取对应的Book
     *
     * @param bookId 图书Id
     * @return Rxjava的observable对象
     */
    @GET("book/{bookId}")
    Observable<Book> selectBook(@Path("bookId") String bookId);


    /**
     * @param startPosition 起始位置
     * @param endPosition   末位置
     * @return Rxjava的observable对象
     */
    @GET("book/{startPosition}/{endPosition}")
    Observable<List<Book>> selectAllBooks(@Path("startPosition") int startPosition,
                                          @Path("endPosition") int endPosition);

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //用户数据操作
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 登录
     *
     * @param userName     用户名
     * @param userPassword 用户密码
     * @return Rxjava的observable对象
     */
    @POST("{userName}/{userPassword}")
    Observable<User> login(@Path("userName") String userName,
                           @Path("userPassword") String userPassword);

    /**
     * 修改个人信息
     *
     * @param userId         用户Id
     * @param userName       用户名
     * @param userPhone      电话号码
     * @param userEmail      邮箱
     * @param userAge        用户年龄
     * @param userAvatarPath 用户头像
     * @return Rxjava的observable对象
     */
    @Multipart
    @POST("{userId}")
    Observable<User> updateUser(@Path("userId") String userId,
                                @Part("userName") RequestBody userName,
                                @Part("userPhone") long userPhone,
                                @Part("userEmail") RequestBody userEmail,
                                @Part("userAge") int userAge,
                                @Part MultipartBody.Part userAvatarPath);

    /**
     * 注册
     *
     * @param userName     用户名
     * @param userPassword 用户密码
     * @return Rxjava的observable对象
     */
    @Multipart
    @POST("user/")
    Observable<User> register(@Part("userName") RequestBody userName,
                              @Part("userPassword") RequestBody userPassword);

    /**
     * 获取好友列表
     *
     * @param userId        用户ID
     * @param startPosition 起始位置
     * @param endPosition   结束位置
     */
    @GET("friend/{userId}/{startPosition}/{endPosition}")
    Observable<List<User>> getFriends(@Path("userId") String userId,
                                      @Path("startPosition") int startPosition,
                                      @Path("endPosition") int endPosition);

    @Multipart
    @POST(".")
    Observable<Book> insertABook(@Part("bookPages") int bookPages,
                                 @Part("bookPrice") double bookPrice,
                                 @Part MultipartBody.Part bookImagepath,
                                 @PartMap Map<String, RequestBody> map);

}
