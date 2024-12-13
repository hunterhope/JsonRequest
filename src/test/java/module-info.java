/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module JsonRequestTest {
    requires JsonRequest;  
    requires junit;
    requires org.mockito;
    requires java.net.http;
    exports com.hunterhope.jsonrequest.impl;
    opens com.hunterhope.jsonrequest.impl;
}
