package com.ieti.model.auth;

public class Token {

    String accessToken;
    public Token() {

    }
    public Token( String accessToken ) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken( String accessToken ) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "Token{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }
}
