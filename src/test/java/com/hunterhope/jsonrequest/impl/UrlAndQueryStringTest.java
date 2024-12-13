/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hunterhope.jsonrequest.impl;

import com.hunterhope.jsonrequest.UrlAndQueryString;
import java.net.URI;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author user
 */
public class UrlAndQueryStringTest {

    @Test
    public void baseUrlCombinQueryString_ok() {
        //準備物件
        UrlAndQueryString qs = new UrlAndQueryString("https://www.twse.com.tw/rwd/zh/api/codeQuery");
        qs.addParam("query", "2323");
        //跑起來
        URI uri = qs.getUri();
        //驗證
        Assert.assertEquals("https://www.twse.com.tw/rwd/zh/api/codeQuery?query=2323", uri.toString());
    }

    @Test
    public void onlyBaseUrl() {
        //準備物件
        UrlAndQueryString qs = new UrlAndQueryString("https://www.twse.com.tw/rwd/zh/api/codeQuery");
        //跑起來
        URI uri = qs.getUri();
        //驗證
        Assert.assertEquals("https://www.twse.com.tw/rwd/zh/api/codeQuery", uri.toString());
    }

}
