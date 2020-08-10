package com.aliceberg.instasamka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    String usernameString = null;
    String passwordString = null;

    SharedPreferences instagramPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        instagramPrefs = getApplicationContext().getSharedPreferences("InstagramPrefs" ,MODE_PRIVATE);



    }

    void submitClick(View view) throws IOException {

        usernameString = username.getText().toString();
        passwordString = password.getText().toString();

        SharedPreferences.Editor editor = instagramPrefs.edit();
        editor.putString("username", usernameString);
        editor.putString("password", passwordString);
        editor.apply();

        instagramDataCollection(usernameString, passwordString);

    }

    void instagramDataCollection(String username, String password) throws IOException {

        Instagram4j instagram4j = Instagram4j.builder().username(username).password(password).build();
        instagram4j.setup();
        instagram4j.login();

        InstagramSearchUsernameResult usernameResult = instagram4j.sendRequest(new InstagramSearchUsernameRequest(username));

        Log.e("TAG", "instagramDataCollection: FOLLOWERS: " + usernameResult.getUser().getFollower_count());


    }

}