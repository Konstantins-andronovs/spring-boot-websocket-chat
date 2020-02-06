package com.example.springbootwebsocketchat.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * WebSocket message model
 */
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String msg;
    @Getter
    @Setter
    private int onlineCount;
}
