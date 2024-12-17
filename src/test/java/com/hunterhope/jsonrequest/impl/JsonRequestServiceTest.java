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
            mock_2323_suggestion_id_request(StockNoSuggestion.class, 400);
            //驗證
            Assert.fail("錯誤，沒有拋出例外");
        } catch (NoInternetException e) {
            System.out.println("NoInternetException=\n" + e.getMessage());
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
    public void refactor_get2323_suggestion_id_ok() {

        try {
            Optional<StockNoSuggestion> ds = mock_2323_suggestion_id_request(StockNoSuggestion.class, 200);
            //驗證，只要不是空的
            Assert.assertTrue(ds.isPresent());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void get2323_price_info_ok() {

        try {
            //準備假物件
            MyHttpClient myHttpClient = Mockito.mock(MyHttpClient.class);
            JsonRequestService jrs = new JsonRequestService(myHttpClient);
            UrlAndQueryString qs = new UrlAndQueryString("https://www.twse.com.tw/rwd/zh/afterTrading/STOCK_DAY");
            qs.addParam("stockNo", "2323");
            qs.addParam("response", "json");
            qs.addParam("date", "20241217");        
            Mockito.when(myHttpClient.sendRequest2(qs)).thenReturn(priceJson_2323);
            Optional<OneMonthPrice> ds = jrs.getData(qs, OneMonthPrice.class);
            //驗證，只要不是空的
            Assert.assertTrue(ds.isPresent());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * 此測試只是提醒自己，缺少部分queryString仍然會有回應
     */
    @Test
    public void get2323_price_info_lack_queryString(){
        try {
            //準備假物件
            MyHttpClient myHttpClient = Mockito.mock(MyHttpClient.class);
            JsonRequestService jrs = new JsonRequestService(myHttpClient);
            UrlAndQueryString qs = new UrlAndQueryString("https://www.twse.com.tw/rwd/zh/afterTrading/STOCK_DAY");
            qs.addParam("stockNo", "2323");
            qs.addParam("response", "json");
//            qs.addParam("date", "20241217");        
            Mockito.when(myHttpClient.sendRequest2(qs)).thenReturn(priceJson_2323);
            Optional<OneMonthPrice> ds = jrs.getData(qs, OneMonthPrice.class);
            System.out.println(ds.get());
            Assert.assertTrue(ds.isPresent());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        
    }
    private <T> Optional<T> mock_2323_suggestion_id_request(Class<T> dataClass, int stateCode) throws InterruptedException, ServerMaintainException, IOException, DataClassFieldNameErrorException, NoInternetException {
        //準備假物件
        MyHttpClient myHttpClient = Mockito.mock(MyHttpClient.class);
        HttpResponse<String> rsp = Mockito.mock(HttpResponse.class);
        //準備物件
        JsonRequestService jrs = new JsonRequestService(myHttpClient);
        UrlAndQueryString qs = new UrlAndQueryString("https://www.twse.com.tw/rwd/zh/api/codeQuery");
        qs.addParam("query", "2323");
        String jsonString = "{\n"
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
                + "}";
        Mockito.when(myHttpClient.sendRequest(qs)).thenReturn(rsp);
        switch (stateCode) {
            case 200:
                Mockito.when(myHttpClient.sendRequest2(qs)).thenReturn(jsonString);
                break;
            case 404:
                Mockito.when(myHttpClient.sendRequest2(qs)).thenThrow(new ServerMaintainException());
                break;
            case 400:
                Mockito.when(myHttpClient.sendRequest2(qs)).thenThrow(new ConnectException());
                break;
            default:
                throw new AssertionError();
        }

        //跑起來
        Optional<T> ds = jrs.getData(qs, dataClass);
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
//            //準備物件
//            UrlAndQueryString qs = new UrlAndQueryString("https://www.twse.com.tw/rwd/zh/api/codeQuery");
//            qs.addParam("query", "2323");
//            //準備假物件  
//            MyHttpClient myHttpClient = Mockito.mock(MyHttpClient.class);
//            //準備物件
//            JsonRequestService jrs = new JsonRequestService(myHttpClient);
//            HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
//            Mockito.when(myHttpClient.sendRequest(qs)).thenReturn(httpResponse);
//            Mockito.when(httpResponse.statusCode()).thenReturn(404);
//            //跑起來
            Optional<StockNoSuggestion> ds = mock_2323_suggestion_id_request(StockNoSuggestion.class, 404);
            //驗證，只要不是空的
            Assert.fail("沒有拋出例外");
        } catch (ServerMaintainException e) {
            Assert.assertTrue(e.getMessage().contains("404"));
        } catch (Exception e) {
            Assert.fail("拋出我不要的例外 ex\n" + e.getClass() + e.getMessage());
        }
    }

    @Test
    public void dataClassError() {

        try {

            Optional<ErrorDataClass> ds = mock_2323_suggestion_id_request(ErrorDataClass.class, 200);
            //驗證，只要不是空的
            Assert.fail("沒有拋出例外");
        } catch (DataClassFieldNameErrorException ex) {
            System.out.println("DataClassFieldNameErrorException=\n" + ex.getMessage());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private final String priceJson_2323 = "{\n"
            + "\"stat\": \"OK\",\n"
            + "\"date\": \"20241217\",\n"
            + "\"title\": \"113年12月 2323 中環             各日成交資訊\",\n"
            + "\"fields\": [\n"
            + "\"日期\",\n"
            + "\"成交股數\",\n"
            + "\"成交金額\",\n"
            + "\"開盤價\",\n"
            + "\"最高價\",\n"
            + "\"最低價\",\n"
            + "\"收盤價\",\n"
            + "\"漲跌價差\",\n"
            + "\"成交筆數\"\n"
            + "],\n"
            + "\"data\": [\n"
            + "[\n"
            + "\"113/12/02\",\n"
            + "\"7,976,208\",\n"
            + "\"89,738,569\",\n"
            + "\"11.30\",\n"
            + "\"11.40\",\n"
            + "\"11.10\",\n"
            + "\"11.40\",\n"
            + "\"+0.15\",\n"
            + "\"2,220\"\n"
            + "],\n"
            + "[\n"
            + "\"113/12/03\",\n"
            + "\"2,516,243\",\n"
            + "\"28,687,756\",\n"
            + "\"11.40\",\n"
            + "\"11.45\",\n"
            + "\"11.35\",\n"
            + "\"11.45\",\n"
            + "\"+0.05\",\n"
            + "\"1,025\"\n"
            + "],\n"
            + "[\n"
            + "\"113/12/04\",\n"
            + "\"2,489,006\",\n"
            + "\"28,523,571\",\n"
            + "\"11.55\",\n"
            + "\"11.55\",\n"
            + "\"11.40\",\n"
            + "\"11.45\",\n"
            + "\" 0.00\",\n"
            + "\"819\"\n"
            + "],\n"
            + "[\n"
            + "\"113/12/05\",\n"
            + "\"2,139,402\",\n"
            + "\"24,466,253\",\n"
            + "\"11.50\",\n"
            + "\"11.55\",\n"
            + "\"11.35\",\n"
            + "\"11.45\",\n"
            + "\" 0.00\",\n"
            + "\"734\"\n"
            + "],\n"
            + "[\n"
            + "\"113/12/06\",\n"
            + "\"2,489,249\",\n"
            + "\"28,648,354\",\n"
            + "\"11.50\",\n"
            + "\"11.60\",\n"
            + "\"11.45\",\n"
            + "\"11.50\",\n"
            + "\"+0.05\",\n"
            + "\"754\"\n"
            + "],\n"
            + "[\n"
            + "\"113/12/09\",\n"
            + "\"2,776,092\",\n"
            + "\"31,884,439\",\n"
            + "\"11.50\",\n"
            + "\"11.60\",\n"
            + "\"11.40\",\n"
            + "\"11.50\",\n"
            + "\" 0.00\",\n"
            + "\"782\"\n"
            + "],\n"
            + "[\n"
            + "\"113/12/10\",\n"
            + "\"2,762,608\",\n"
            + "\"31,602,593\",\n"
            + "\"11.60\",\n"
            + "\"11.60\",\n"
            + "\"11.30\",\n"
            + "\"11.30\",\n"
            + "\"-0.20\",\n"
            + "\"791\"\n"
            + "],\n"
            + "[\n"
            + "\"113/12/11\",\n"
            + "\"3,807,443\",\n"
            + "\"42,642,876\",\n"
            + "\"11.25\",\n"
            + "\"11.40\",\n"
            + "\"11.10\",\n"
            + "\"11.10\",\n"
            + "\"-0.20\",\n"
            + "\"1,272\"\n"
            + "],\n"
            + "[\n"
            + "\"113/12/12\",\n"
            + "\"2,614,680\",\n"
            + "\"29,173,199\",\n"
            + "\"11.20\",\n"
            + "\"11.25\",\n"
            + "\"11.05\",\n"
            + "\"11.10\",\n"
            + "\" 0.00\",\n"
            + "\"970\"\n"
            + "],\n"
            + "[\n"
            + "\"113/12/13\",\n"
            + "\"5,278,537\",\n"
            + "\"57,551,424\",\n"
            + "\"11.05\",\n"
            + "\"11.05\",\n"
            + "\"10.80\",\n"
            + "\"10.90\",\n"
            + "\"-0.20\",\n"
            + "\"1,638\"\n"
            + "],\n"
            + "[\n"
            + "\"113/12/16\",\n"
            + "\"3,112,968\",\n"
            + "\"33,786,671\",\n"
            + "\"10.90\",\n"
            + "\"11.00\",\n"
            + "\"10.70\",\n"
            + "\"10.70\",\n"
            + "\"-0.20\",\n"
            + "\"985\"\n"
            + "],\n"
            + "[\n"
            + "\"113/12/17\",\n"
            + "\"1,916,649\",\n"
            + "\"20,695,739\",\n"
            + "\"10.80\",\n"
            + "\"10.90\",\n"
            + "\"10.70\",\n"
            + "\"10.80\",\n"
            + "\"+0.10\",\n"
            + "\"650\"\n"
            + "]\n"
            + "],\n"
            + "\"notes\": [\n"
            + "\"符號說明:+/-/X表示漲/跌/不比價\",\n"
            + "\"當日統計資訊含一般、零股、盤後定價、鉅額交易，不含拍賣、標購。\",\n"
            + "\"ETF證券代號第六碼為K、M、S、C者，表示該ETF以外幣交易。\",\n"
            + "\"權證證券代號可重複使用，權證顯示之名稱係目前存續權證之簡稱。\"\n"
            + "],\n"
            + "\"total\": 12\n"
            + "}";
}
