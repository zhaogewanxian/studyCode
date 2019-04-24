package com.wanxian.webservices;

import com.wanxian.webservices.domain.User;
import com.wanxian.webservices.domain.UserIdRequest;
import com.wanxian.webservices.domain.UserResponse;
import com.wanxian.webservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.Instant;
import java.time.ZoneId;

@Endpoint
public class UserServiceEnpoint {
    @Autowired
    private UserRepository userRepository;

    @PayloadRoot(namespace = "http://www.rockrolltime.cn/schema", localPart = "UserIdRequest")
    @ResponsePayload
    public UserResponse getUser(@RequestPayload UserIdRequest userIdRequest) {
        UserResponse userResponse = new UserResponse();
        long userId = userIdRequest.getUserId();
        Instant instant = Instant.ofEpochMilli(userIdRequest.getTimestamp());
        System.out.println("用户id：" + userId + "，时间：" + instant.atZone(ZoneId.systemDefault()));
        User user = userRepository.findUserById(userId);
        userResponse.setUser(user);
        userResponse.setTimestamp(Instant.now().toEpochMilli()); //国际化时间
        return userResponse;
    }
}
