package com.wanxian.webservices.client;

import com.wanxian.webservices.domain.User;
import com.wanxian.webservices.domain.UserIdRequest;
import com.wanxian.webservices.domain.UserResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.time.Instant;

/**
 * web service 客户端实现
 */
public class WebServicesClient {


    public static void main(String[] args) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(UserIdRequest.class, UserResponse.class, User.class);
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        //构造 SOAP请求
        UserIdRequest userIdRequest = new UserIdRequest();
        userIdRequest.setUserId(1);
        userIdRequest.setTimestamp(Instant.now().toEpochMilli());
        UserResponse userResponse = (UserResponse) webServiceTemplate.marshalSendAndReceive("http://localhost:8080/user/", userIdRequest);
        System.out.printf("返回结果:" + userResponse);
    }

}
