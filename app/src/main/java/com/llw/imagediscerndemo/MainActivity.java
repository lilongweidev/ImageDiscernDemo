package com.llw.imagediscerndemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.llw.imagediscerndemo.model.GetDiscernResultRequest;
import com.llw.imagediscerndemo.model.GetDiscernResultResponse;
import com.llw.imagediscerndemo.model.GetTokenResponse;
import com.llw.imagediscerndemo.network.ApiService;
import com.llw.imagediscerndemo.network.NetCallBack;
import com.llw.imagediscerndemo.network.ServiceGenerator;
import com.llw.imagediscerndemo.util.Constant;
import com.llw.imagediscerndemo.util.SPUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ApiService service;
    /**
     * 鉴权Toeken
     */
    private String accessToken;
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = ServiceGenerator.createService(ApiService.class);

        rxPermissions = new RxPermissions(this);
    }

    /**
     * 获取鉴权Token
     */
    private String getAccessToken() {
        String token = SPUtils.getString(Constant.TOKEN, null, this);
        if (token == null) {
            //访问API获取接口
            requestApiGetToken();
        } else {
            //则判断Token是否过期
            if (isTokenExpired()) {
                //过期
                requestApiGetToken();
            } else {
                accessToken = token;
            }

        }
        return accessToken;
    }

    /**
     * 访问API获取接口
     */
    private void requestApiGetToken() {
        String grantType = "client_credentials";
        String apiKey = "TjPChftoEyBq7Nzm65KNerqr";
        String apiSecret = "eTph4jO95te6R3G2aecktGMbkieOv7rS";
        service.getToken(grantType, apiKey, apiSecret)
                .enqueue(new NetCallBack<GetTokenResponse>() {
                    @Override
                    public void onSuccess(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {
                        if (response.body() != null) {
                            //鉴权Token
                            accessToken = response.body().getAccess_token();
                            //过期时间 秒
                            long expiresIn = response.body().getExpires_in();
                            //当前时间 秒
                            long currentTimeMillis = System.currentTimeMillis() / 1000;
                            //放入缓存
                            SPUtils.putString(Constant.TOKEN, accessToken, MainActivity.this);
                            SPUtils.putLong(Constant.GET_TOKEN_TIME, currentTimeMillis, MainActivity.this);
                            SPUtils.putLong(Constant.TOKEN_VALID_PERIOD, expiresIn, MainActivity.this);
                        }
                    }

                    @Override
                    public void onFailed(String errorStr) {
                        Log.e(TAG, "获取Token失败，失败原因：" + errorStr);
                        accessToken = null;
                    }
                });
    }

    /**
     * Token是否过期
     *
     * @return
     */
    private boolean isTokenExpired() {
        //获取Token的时间
        long getTokenTime = SPUtils.getLong(Constant.GET_TOKEN_TIME, 0, this);
        //获取Token的有效时间
        long effectiveTime = SPUtils.getLong(Constant.TOKEN_VALID_PERIOD, 0, this);
        //获取当前系统时间
        long currentTime = System.currentTimeMillis() / 1000;

        return (currentTime - getTokenTime) >= effectiveTime;
    }


    /**
     * 识别网络图片
     *
     * @param view
     */
    public void IdentifyWebPictures(View view) {
        String hander = "application/x-www-form-urlencoded";
        String token = getAccessToken();
        //Log.d(TAG, token);
        String imgUrl = "https://bce-baiyu.cdn.bcebos.com/14ce36d3d539b6004ef2e45fe050352ac65cb71e.jpeg";
        //GetDiscernResultRequest requestBody = new GetDiscernResultRequest(null, imgUrl, 100);
        service.getDiscernResult(token,imgUrl).enqueue(new NetCallBack<GetDiscernResultResponse>() {
            @Override
            public void onSuccess(Call<GetDiscernResultResponse> call, Response<GetDiscernResultResponse> response) {
                Log.d(TAG, new Gson().toJson(response.body()));
            }

            @Override
            public void onFailed(String errorStr) {
                Log.e(TAG, "图像识别失败，失败原因：" + errorStr);
            }
        });
    }

    /**
     * 识别相册图片
     *
     * @param view
     */
    @SuppressLint("CheckResult")
    public void IdentifyAlbumPictures(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(grant -> {
                if (grant) {
                    //获得权限

                } else {

                }
            });
        } else {

        }
    }
}
