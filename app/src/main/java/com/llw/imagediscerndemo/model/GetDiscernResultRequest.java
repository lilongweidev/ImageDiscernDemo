package com.llw.imagediscerndemo.model;

/**
 * 获取识别结果请求实体
 *
 * @author llw
 * @date 2021/4/1 18:02
 */
public class GetDiscernResultRequest {

    private String image;//图片base64
    private String url;//图片url地址
    private int baike_num;//返回百科信息的结果数

    public GetDiscernResultRequest(String image, String url, int baike_num) {
        this.image = image;
        this.url = url;
        this.baike_num = baike_num;
    }
}
