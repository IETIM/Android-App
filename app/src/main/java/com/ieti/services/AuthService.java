package com.ieti.services;

import com.ieti.model.auth.LoginWrapper;
import com.ieti.model.auth.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("login")
    Call<Token> login(@Body LoginWrapper lw);

}
