package ru.netology.data;


import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UserInfo {
    String login;
    String password;
    String status;
}