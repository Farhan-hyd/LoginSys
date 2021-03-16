package com.example.mfrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void onBackPressed() {
        Intent Login = new Intent(start.this,MainActivity.class);
        startActivity(Login);
        super.onBackPressed();
        this.finish();
    }
}