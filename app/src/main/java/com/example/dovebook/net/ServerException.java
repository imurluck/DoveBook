package com.example.dovebook.net;

public class ServerException extends RuntimeException {
    public int code;
    public String message;
}
