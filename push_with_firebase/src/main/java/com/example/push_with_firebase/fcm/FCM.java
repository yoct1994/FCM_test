package com.example.push_with_firebase.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.List;

@Component
public class FCM {
    public void fcm_send(List<String> tokens, String title, String content) {
        try {
            FileInputStream fileInputStream = new FileInputStream("/Users/seogeonhui/Documents/BACKEND/push_with_firebase/src/main/resources/fcm/categorychatservice-firebase-adminsdk-pgoe7-988a07f5da.json");

            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(fileInputStream))
                    .build();

            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(firebaseOptions);
            }
            /*
            단일 폰에 보낼때
            Message message = Message.builder()
                    .setAndroidConfig(AndroidConfig.builder()
                            .setTtl(3600 * 1000)
                            .setPriority(AndroidConfig.Priority.NORMAL)
                            .setNotification(AndroidNotification.builder()
                                    .setTitle(title)
                                    .setBody(content)
                                    .setIcon("icon")
                                    .setColor("#000000")
                                    .build())
                            .build())
                    .setToken(andr)
                    .build();
            */

            //여러 폰에 전송할때
            MulticastMessage multicastMessage = MulticastMessage.builder()
                    .putData("title", title)
                    .putData("content", content)
                    .addAllTokens(tokens)
                    .build();

            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(multicastMessage);
            System.out.println(response.getSuccessCount() + " messages were sent successfully");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
