/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hunterhope.jsonrequest;

import com.google.gson.Gson;
import com.hunterhope.jsonrequest.exception.DataClassFieldNameErrorException;
import com.hunterhope.jsonrequest.exception.NoInternetException;
import com.hunterhope.jsonrequest.exception.ServerMaintainException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * 此模組的對外服務類。
 * @author hunterhope
 */
public class JsonRequestService {

    private final MyHttpClient myHttpClient;

    /**
     * 預設使用MyHttpClient物件。
     */
    public JsonRequestService() {
        this(new MyHttpClient());
    }
    /**
     * 此建構子主要是用於測試。
     */
    public JsonRequestService(MyHttpClient myHttpClient) {
        this.myHttpClient = myHttpClient;
    }

    /**
     * 根據URL取得對應的JSON dataClass。<br>
     */
    public <T> Optional<T> getData(UrlAndQueryString url, Class<T> dataClass) throws NoInternetException, ServerMaintainException, DataClassFieldNameErrorException {
        try {
            //發送請求
            HttpResponse<String> rsp = myHttpClient.sendRequest(url);
            if (rsp.statusCode() == 404) {
                throw new ServerMaintainException();
            }
            //將取得的json回應,變成對應物件
            Gson gson = new Gson();
            T jsonObj = gson.fromJson(rsp.body(), dataClass);
            if (jsonObj == null) {
                return Optional.empty();
            }
            //假如解析出來的屬性都是null，通知使用者此dataClass是無效的
            if(isAllFieldNull(jsonObj)){
                throw new DataClassFieldNameErrorException(rsp.body());
            }
            
            return Optional.of(jsonObj);
        } catch (ConnectException e) {
            throw new NoInternetException(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            return Optional.empty();
        }
    }
    //利用反射，檢查轉換的dataClass不是全部都null。全部都null目前認定為無效dataClass
    private <T> boolean isAllFieldNull(T jsonObj) {
        try {
            for (Field f : jsonObj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(jsonObj) != null) {
                    return false;
                }
            }
            return true;
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException ex) {
            return false;
        } 
    }

}
