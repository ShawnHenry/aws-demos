package com.example.demo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationResource {

    @RequestMapping("/notification")
    public ResponseEntity receiveNotification() {
        return null;
    }
}
