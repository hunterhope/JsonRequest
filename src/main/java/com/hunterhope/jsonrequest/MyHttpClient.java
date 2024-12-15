/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hunterhope.jsonrequest;

import com.hunterhope.jsonrequest.exception.ServerMaintainException;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 使用Java API來實作發送GET請求取回JSON資料
 *
 * @author hunterhope
 */
public class MyHttpClient {

    /**
     * 建立請求物件。 一個方便方法，根據給定URL，建立HttpRequest。
     */
    public HttpRequest createRequest(UrlAndQueryString url) {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(url.getUri()).GET().build();
        return httpRequest;
    }

    /**
     * 發送請求。<br>
     * 一個方便方法，如要自己客製化request請使用sendRequest(HttpRequest httpRequest)方法
     */
    public HttpResponse<String> sendRequest(UrlAndQueryString url) throws InterruptedException, IOException {
        return sendRequest(createRequest(url));
    }

    /**
     * 發送請求。<br>
     * 根據指定的request發送請求。
     */
    public HttpResponse<String> sendRequest(HttpRequest httpRequest) throws InterruptedException, IOException {
        HttpResponse<String> rsp = HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return rsp;
    }

    /**
     * 此方法與sendRequest(UrlAndQueryString url)功能相同，只是為了讓service不意依賴於HttpResponse介面。<br>
     * 未來若是使用其他實作，service則不需要修改。
     */
    public String sendRequest2(UrlAndQueryString url) throws ServerMaintainException, InterruptedException, IOException {
        HttpResponse<String> rsp = sendRequest(url);
        if (rsp.statusCode() == 404) {
            throw new ServerMaintainException();
        }
        return rsp.body();
    }
}
