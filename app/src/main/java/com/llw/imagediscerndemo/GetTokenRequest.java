package com.llw.imagediscerndemo;

/**
 * 获取鉴权认证Token请求实体
 *
 * @author llw
 * @date 2021/4/1 17:50
 */
public class GetTokenRequest {

    private String grant_type;//固定为client_credentials
    private String client_id;//应用的API Key
    private String client_secret;//应用的Secret Key

    public GetTokenRequest(String grant_type, String client_id, String client_secret) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.client_secret = client_secret;
    }
}
