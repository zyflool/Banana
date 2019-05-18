package com.example.banana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.banana.Dictionary.XHDictionary;
import com.example.banana.Translation.Translate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout totranslate, todictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totranslate = findViewById(R.id.Totranslate);
        todictionary = findViewById(R.id.Todictionary);
        todictionary.setOnClickListener(this);
        totranslate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Todictionary : {
                Intent intent = new Intent(MainActivity.this, Translate.class);
                startActivity(intent);
                break;
            }
            case R.id.Totranslate : {
                Intent intent = new Intent(MainActivity.this, XHDictionary.class);
                startActivity(intent);
                break;
            }
        }
    }
}
