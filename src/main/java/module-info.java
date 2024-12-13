/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */
/**
 * 此模組實現一個網路Json的請求,並回傳指定的dataClass資料
 */
module JsonRequest {
    requires com.google.gson;
    requires java.net.http;
    exports com.hunterhope.jsonrequest;
    exports com.hunterhope.jsonrequest.exception;
}
