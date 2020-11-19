package com.ieti.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ieti.model.auth.LoginWrapper;
import com.ieti.model.auth.Token;
import com.ieti.services.AuthService;
import com.ieti.ui.MainActivity;
import com.ieti.ui.R;
import com.ieti.ui.userprofile.UserProfile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }

    public void validate(){
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE );
        if (sharedPref.contains("TOKEN_KEY")){
            Intent intent = new Intent(this,UserProfile.class);
        }
    }

    public void login(View view) {
        System.out.println("hola");
        EditText email = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        if (email.getText().toString().isEmpty()) {
            email.setError("Email cannot be empty");
        }
        if (email.getText().toString().isEmpty()) {
            password.setError("Password cannot be empty");
        }else{

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http:/10.0.2.2:8080") //localhost for emulator
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AuthService authService = retrofit.create(AuthService.class);
            executorService.execute(authenticate(authService,email,password));
        }
    }

    private void saveToken(Token token) {
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TOKEN_KEY",token.getAccessToken());
        editor.commit();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE );
        if (sharedPref.contains("TOKEN_KEY")){
            onBackPressed();
        }
    }


    private Runnable authenticate(AuthService authService, EditText email, EditText password) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Token> response =authService.login(new LoginWrapper(email.getText().toString(), password.getText().toString())).execute();
                    Token token = response.body();
                    saveToken(token);
                    if (token!=null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                                startActivity(intent);
                            }
                        });
                        finish();
                    }
                    else{
                        email.setError("Verify Your email!");
                        password.setError("verify Your Password!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

    }
}
