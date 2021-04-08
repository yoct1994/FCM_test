package com.example.push_with_firebase.controller;

import com.example.push_with_firebase.fcm.FCM;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FCMTestController {

    private final FCM fcm;

    @Value("${fcm.token}")
    private String token;

    @GetMapping("/fcmTest")
    public @ResponseBody String fcm_send(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Model model)
            throws Exception {
        String title = "test";
        String content = "contentTest";
        List<String> tokens = new ArrayList<>();
        tokens.add(token);

        fcm.fcm_send(tokens, title, content);

        return "test";
    }
}
