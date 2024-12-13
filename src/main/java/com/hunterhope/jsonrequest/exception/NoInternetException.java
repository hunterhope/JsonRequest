/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hunterhope.jsonrequest.exception;

/**
 * 網路連線異常，或是地址有誤都會產生ConnectException
 * 將其包裝成我的例外
 * @author hunterhope
 */
public class NoInternetException extends Exception{

    public NoInternetException(Throwable cause) {
        super("目前沒有網路，或找不到指定的地址",cause);
    }
    
}
