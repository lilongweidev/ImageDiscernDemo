package com.llw.imagediscerndemo;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
     * @param body 请求实体Bean
     * @return 返回
     */
    @POST("/oauth/2.0/token")
    Call<JsonObject> getToken(@Body GetTokenRequest body);

    /**
     * @param headerStr   请求头  值： application/x-www-form-urlencoded
     * @param accessToken 获取鉴权认证Token
     * @param body 请求实体Bean
     * @return JsonObject
     */
    @POST("/rest/2.0/image-classify/v2/advanced_general")
    Call<JsonObject> getToken(@Header("Content-Type") String headerStr,
                              @Field("access_token") String accessToken,
                              @Body GetDiscernResultRequest body);
}
