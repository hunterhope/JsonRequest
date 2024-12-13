/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hunterhope.jsonrequest.impl;

import com.hunterhope.jsonrequest.JsonRequestService;
import com.hunterhope.jsonrequest.MyHttpClient;
import com.hunterhope.jsonrequest.UrlAndQueryString;
import com.hunterhope.jsonrequest.exception.DataClassFieldNameErrorException;
import com.hunterhope.jsonrequest.exception.NoInternetException;
import com.hunterhope.jsonrequest.exception.ServerMaintainException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.http.HttpResponse;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author user
 */
public class JsonRequestServiceTest {

//    @Test
//    public void connectError() throws Exception {
//        try {
//            //準備物件
//            JsonRequestService jrs = new JsonRequestService();
//            //跑起來
//            jrs.getData(new UrlAndQueryString("https://abc.def"), StockNoSuggestion.class);
//            //驗證
//            Assert.fail("錯誤，沒有拋出例外");
//        } catch (NoInternetException e) {
//            return;
//        }
//        Assert.fail("沒有捕獲到NoInternetException例外");
//    }
    @Test
    public void refactor_connectError() throws Exception {
        try {
            //準備假物件
            MyHttpClient myHttpClient = Mockito.mock(MyHttpClient.class);
            //準備物件
            JsonRequestService jrs = new JsonRequestService(myHttpClient);
            UrlAndQueryString url = new UrlAndQueryString("https://abc.def");
            Mockito.when(myHttpClient.sendRequest(url)).thenThrow(ConnectException.class);
            //跑起來
            jrs.getData(url, StockNoSuggestion.class);
            //驗證
            Assert.fail("錯誤，沒有拋出例外");
        } catch (NoInternetException e) {
            System.out.println("NoInternetException=\n"+e.getMessage());
            return;
        }
        Assert.fail("沒有捕獲到NoInternetException例外");
    }

//    //正常取得2323回應
//    @Test
//    public void get2323_ok() throws Exception {
//
//        try {
//            //準備物件
//            JsonRequestService jrs = new JsonRequestService();
//            UrlAndQueryString qs = new UrlAndQueryString("https://www.twse.com.tw/rwd/zh/api/codeQuery");
//            qs.addParam("query", "2323");
//            //跑起來
//            Optional<StockNoSuggestion> ds = jrs.getData(qs, StockNoSuggestion.class);
//            //驗證，只要不是空的
//            ds.ifPresent(e -> System.out.println(e));
//            Assert.assertTrue(ds.isPresent());
//        } catch (NoInternetException e) {
//            Assert.fail(e.getMessage());
//        }
//    }

    //正常取得2323回應
    @Test
    public void refactor_get2323_ok() {

        try {
            Optional<StockNoSuggestion> ds = mock_2323_ok_request(StockNoSuggestion.class);
            //驗證，只要不是空的
            Assert.assertTrue(ds.isPresent());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private <T> Optional<T> mock_2323_ok_request(Class<T> dataClass) throws InterruptedException, ServerMaintainException, IOException, DataClassFieldNameErrorException, NoInternetException {
        //準備假物件
        MyHttpClient myHttpClient = Mockito.mock(MyHttpClient.class);
        HttpResponse<String> rsp = Mockito.mock(HttpResponse.class);
        //準備物件
        JsonRequestService jrs = new JsonRequestService(myHttpClient);
        UrlAndQueryString qs = new UrlAndQueryString("https://www.twse.com.tw/rwd/zh/api/codeQuery");
        qs.addParam("query", "2323");
        Mockito.when(myHttpClient.sendRequest(qs)).thenReturn(rsp);
        Mockito.when(rsp.body()).thenReturn("{\n"
                + "\"query\": \"2323\",\n"
                + "\"suggestions\": [\n"
                + "\"2323\\t中環\",\n"
                + "\"2323A\\t環甲特\",\n"
                + "\"2323R\\t中環甲\",\n"
                + "\"2323S\\t中環乙\",\n"
                + "\"2323T\\t中環丙\",\n"
                + "\"2323V\\t中環戊\",\n"
                + "\"2323W\\t中環己\",\n"
                + "\"2323X\\t中環庚\"\n"
                + "]\n"
                + "}");
        //跑起來
        Optional<T> ds = jrs.getData(qs,dataClass);
        return ds;
    }

//    //網站維護中
//    @Test
//    public void server404() {
//        try {
//            //準備物件
//            JsonRequestService jrs = new JsonRequestService();
//            UrlAndQueryString qs = new UrlAndQueryString("https://www.twse.com.tw/rwd/zh/api/codeQuery");
//            qs.addParam("query", "2323");
//            //準備假物件         
//            HttpRequest req = Mockito.mock(HttpRequest.class);
//            HttpResponse.BodyHandler<String> bodyHandler = Mockito.mock(HttpResponse.BodyHandler.class);
//            HttpClient.Builder cb = Mockito.mock(HttpClient.Builder.class);
//            HttpRequest.Builder rb = Mockito.mock(HttpRequest.Builder.class);
//            Mockito.mockStatic(HttpRequest.class).when(HttpRequest::newBuilder).thenReturn(rb);
//            Mockito.mockStatic(HttpClient.class).when(HttpClient::newBuilder).thenReturn(cb);
//            HttpClient httpClient = Mockito.mock(HttpClient.class);
//            Mockito.when(cb.build()).thenReturn(httpClient);
//            Mockito.when(rb.uri(qs.getUri())).thenReturn(rb);
//            Mockito.when(rb.GET()).thenReturn(rb);
//            Mockito.when(rb.build()).thenReturn(req);
//            HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
//            Mockito.mockStatic(HttpResponse.BodyHandlers.class).when(HttpResponse.BodyHandlers::ofString).thenReturn(bodyHandler);
//            Mockito.when(httpClient.send(req, bodyHandler)).thenReturn(httpResponse);
//            Mockito.when(httpResponse.statusCode()).thenReturn(404);
//            //跑起來
//            Optional<StockNoSuggestion> ds = jrs.getData(qs, StockNoSuggestion.class);
//            System.out.println("ds" + ds);
//            //驗證，只要不是空的
//            Assert.fail("沒有拋出例外");
//        } catch (NullPointerException e) {
//            Assert.fail("拋出我不要的例外 null ex");
//        } catch (ServerMaintainException e) {
//            Assert.assertTrue(e.getMessage().contains("404"));
//        } catch (Exception e) {
//            Assert.fail("拋出我不要的例外 ex");
//        }
//    }
    //網站維護中
    @Test
    public void refactor_server404() {
        try {
            //準備物件
            UrlAndQueryString qs = new UrlAndQueryString("https://www.twse.com.tw/rwd/zh/api/codeQuery");
            qs.addParam("query", "2323");
            //準備假物件  
            MyHttpClient myHttpClient = Mockito.mock(MyHttpClient.class);
            //準備物件
            JsonRequestService jrs = new JsonRequestService(myHttpClient);
            HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
            Mockito.when(myHttpClient.sendRequest(qs)).thenReturn(httpResponse);
            Mockito.when(httpResponse.statusCode()).thenReturn(404);
            //跑起來
            Optional<StockNoSuggestion> ds = jrs.getData(qs, StockNoSuggestion.class);
            //驗證，只要不是空的
            Assert.fail("沒有拋出例外");
        } catch (ServerMaintainException e) {
            Assert.assertTrue(e.getMessage().contains("404"));
        } catch (Exception e) {
            Assert.fail("拋出我不要的例外 ex\n" + e.getClass() + e.getMessage());
        }
    }

    @Test
    public void dataClassError(){

        try {
            
            Optional<ErrorDataClass> ds = mock_2323_ok_request(ErrorDataClass.class);
            //驗證，只要不是空的
            Assert.fail("沒有拋出例外");
        } catch (DataClassFieldNameErrorException ex) {
            System.out.println("DataClassFieldNameErrorException=\n"+ex.getMessage());
        }catch(Exception e){
            Assert.fail(e.getMessage());
        }
    }

}
