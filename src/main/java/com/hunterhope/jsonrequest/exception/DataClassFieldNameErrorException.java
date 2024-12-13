/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hunterhope.jsonrequest.exception;

/**
 *
 * @author user
 */
public class DataClassFieldNameErrorException extends Exception{

    public DataClassFieldNameErrorException(String jsonString) {
        super("屬性沒有一個是對。JSON string=\n"+jsonString);
    }
        
}
