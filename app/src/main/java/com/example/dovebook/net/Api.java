package com.example.dovebook.net;

import com.example.dovebook.HandleRequest.model.UpdateFriendResponse;
import com.example.dovebook.HandleRequest.model.UpdateRecordParams;
import com.example.dovebook.base.model.Friend;
import com.example.dovebook.base.model.User;
import com.example.dovebook.bean.Book;
import com.example.dovebook.bean.Copy;
import com.example.dovebook.bean.OrderBean;
import com.example.dovebook.bookupload.model.DoubanBook;
import com.example.dovebook.share.model.Moment;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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


    /////////////////////////////////////////////////////////////////////////////////////////////////
    //图书操作
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 通过bookId获取对应的Book
     *
     * @param bookId 图书Id
     * @return Rxjava的observable对象
     */
    @GET("{bookId}")
    Observable<Book> selectBookById(@Path("bookId") String bookId);


    /**
     * 根据图书Id删除相应图书
     *
     * @param bookId
     * @return
     */
    @DELETE("{bookId}")
    Observable<Book> deleteBook(@Path("bookId") String bookId);

    /**
     * 上传图书
     *
     * @param bookPages
     * @param bookPrice
     * @param bookImagepath
     * @param map
     * @return
     */
    @Multipart
    @POST(".")
    Observable<retrofit2.Response<Book>> insertBook(@Part("bookPages") int bookPages,
                                                    @Part("bookPrice") double bookPrice,
                                                    @Part MultipartBody.Part bookImagepath,
                                                    @PartMap Map<String, RequestBody> map);

    /**
     * 更新图书
     *
     * @param bookId
     * @param bookPages
     * @param bookPrice
     * @param bookImagepath
     * @param map
     * @return
     */
    @Multipart
    @POST("{bookId}")
    Observable<Book> updateBook(@Path("bookId") String bookId,
                                @Part("bookPages") int bookPages,
                                @Part("bookPrice") double bookPrice,
                                @Part MultipartBody.Part bookImagepath,
                                @PartMap Map<String, RequestBody> map);


    /**
     * @param startPosition 起始位置
     * @param endPosition   末位置
     * @return Rxjava的observable对象
     */
    @GET("book/{startPosition}/{endPosition}")
    Observable<retrofit2.Response<List<Book>>> selectAllBooks(@Path("startPosition") int startPosition,
                                                              @Path("endPosition") int endPosition);

    /**
     * 调用豆瓣API  根据isbn获取图书信息
     *
     * @param isbn
     * @return
     */
    @GET(":{isbn}")
    Observable<DoubanBook> getBookInfoByIsbn(@Path("isbn") String isbn);

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //副本操作
    ////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 获取一个人所有的Copy
     *
     * @param userId
     * @param startPosition
     * @param requestNum
     * @return 返回Copy列表
     */
    @GET("{userId}/")
    Observable<List<Copy>> selectCopiesByUserId(@Path("userId") String userId,
                                                @Query("offset") int startPosition,
                                                @Query("limit") int requestNum);

    /**
     * 创建副本
     *
     * @param map
     * @param copyStatus
     * @return
     */
    @POST("copy/")
    Observable<Copy> insertCopy(@PartMap Map<String, RequestBody> map,
                                @Part boolean copyStatus);

    /**
     * 根据copyId 删除 副本
     *
     * @param copyId
     * @return
     */
    @DELETE("{copyId}")
    Observable<Copy> deleteCopy(@Path("copyId") String copyId);

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //漂流表操作
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @POST(".")
    Observable<OrderBean> insertOrder(@PartMap Map<String, RequestBody> map,
                                      @Part boolean ordersStates,
                                      @Part int ordersCredit,
                                      @Part long ordersStart,
                                      @Part long ordersEnd,
                                      @Part long createdat,
                                      @Part long updatedat);

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
     * @param userId 用户ID
     */
    @GET("{userId}/0/20")
    Observable<List<Friend>> getFriends(@Path("userId") String userId);

    /**
     * 删除好友
     */
    @DELETE("{friendId}")
    Observable<retrofit2.Response<Void>> deleteFriend(@Path("friendId") String friendId);

    /**
     * 获取收到的请求
     */
    @GET("{userId}/0/20")
    Observable<retrofit2.Response<List<Friend>>> getRequests(@Path("userId") String userId);

    /**
     *提交请求处理结果
     *
     */
    @Headers({"Content-Type: application/json","Accept:  application/json"})
    @PUT("{friendId}")
    Observable<UpdateFriendResponse> updateFriendRecord(@Path("friendId") String friendId,
                                                        @Body UpdateRecordParams updateRecordParams);


}
