/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hunterhope.jsonrequest.exception;

/**
 *
 * @author user
 */
public class ServerMaintainException extends Exception{

    public ServerMaintainException() {
        super("404:可能網址錯誤或伺服器維護中");
    }

    public ServerMaintainException(Throwable cause) {
        super("404:可能網址錯誤或伺服器維護中",cause);
    }
    
}
