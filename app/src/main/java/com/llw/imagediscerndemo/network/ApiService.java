package com.llw.imagediscerndemo.network;

import com.google.gson.JsonObject;
import com.llw.imagediscerndemo.model.GetDiscernResultRequest;
import com.llw.imagediscerndemo.model.GetDiscernResultResponse;
import com.llw.imagediscerndemo.model.GetTokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * TODO
 *
 * @author llw
 * @date 2021/4/1 17:48
 */
public interface ApiService {

    /**
     * 获取鉴权认证Token
     *
     *
     */
    @FormUrlEncoded
    @POST("/oauth/2.0/token")
    Call<GetTokenResponse> getToken(@Field("grant_type") String grant_type,
                                    @Field("client_id") String client_id,
                                    @Field("client_secret") String client_secret);

    /**
     * 获取图像识别结果
     * @param headerStr   请求头  值： application/x-www-form-urlencoded
     * @param accessToken 获取鉴权认证Token
     * @param url 请求实体Bean
     * @return JsonObject
     */
    @FormUrlEncoded
    @POST("/rest/2.0/image-classify/v2/advanced_general")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Call<GetDiscernResultResponse> getDiscernResult(@Field("access_token") String accessToken,
                                                    @Field("url") String url);
}
