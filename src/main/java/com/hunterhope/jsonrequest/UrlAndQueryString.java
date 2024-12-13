/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hunterhope.jsonrequest;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 代表一個查詢網址。<br>
 * 此物件由baseUrl加上查詢字串。單傳表示此字串，不會做網址格式檢查。
 * @author hunterhope
 */
public class UrlAndQueryString {

    private final String baseUrl;
    private final Map<String, String> queryParams = new HashMap<>();

    /**
     * 本物件不會檢查URI的格式是否正確，使用時要自行確認。
     */
    public UrlAndQueryString(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 加入查詢參數。
     */
    public void addParam(String key, String value) {
        queryParams.put(key, value);
    }

    /**
     * 取得URI。<br>
     * URI是比URL更廣的的一種定址模式。
     */
    public URI getUri(){
        String qs = buildQueryString();
        return URI.create(qs.isBlank()? baseUrl : baseUrl + "?" +qs );
    }

    private String buildQueryString() {
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            if (query.length() > 0) {
                query.append("&");
            }
            query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            query.append("=");
            query.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return query.toString();
    }

}
