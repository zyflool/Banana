package com.example.banana.Game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.banana.MainActivity;
import com.example.banana.R;

import java.net.Inet4Address;
import java.util.Random;

public class Game extends AppCompatActivity implements View.OnClickListener{
    private ImageView[][] t = new ImageView[3][3];
    private int[][] s = {{0,0,0},{0,0,0},{0,0,0}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
    }
    private void initView() {
        t[0][0] = findViewById(R.id.tab1);
        t[0][1] = findViewById(R.id.tab2);
        t[0][2] = findViewById(R.id.tab3);
        t[1][0] = findViewById(R.id.tab4);
        t[1][1] = findViewById(R.id.tab5);
        t[1][2] = findViewById(R.id.tab6);
        t[2][0] = findViewById(R.id.tab7);
        t[2][1] = findViewById(R.id.tab8);
        t[2][2] = findViewById(R.id.tab9);
        t[0][0].setOnClickListener(this);
        t[0][1].setOnClickListener(this);
        t[0][2].setOnClickListener(this);
        t[1][0].setOnClickListener(this);
        t[1][1].setOnClickListener(this);
        t[1][2].setOnClickListener(this);
        t[2][0].setOnClickListener(this);
        t[2][1].setOnClickListener(this);
        t[2][2].setOnClickListener(this);
        nextStep();
    }

    private void nextStep() {
        Random random = new Random();
        int n = random.nextInt(10);
        while (true) {
            if( n == 0 || !isEmpty(n) ) {
                n = random.nextInt(10);
                continue;
            }
            else {
                s[(n-1)/3][(n-1)%3] = -1;
                t[(n-1)/3][(n-1)%3].setImageResource(R.drawable.close);
                if( ifLose() ){
                    Toast.makeText(Game.this,"你输了 请重新开始",Toast.LENGTH_LONG).show();
                    reNew();
                } else if ( ifFlat() ) {
                    Toast.makeText(Game.this,"打成平手 请重新开始",Toast.LENGTH_LONG).show();
                    reNew();
                }
                break;
            }
        }
    }

    public void reNew() {
        s[0][0] = 0;  s[0][1] = 0;  s[0][2] = 0;
        s[1][0] = 0;  s[1][1] = 0;  s[1][2] = 0;
        s[2][0] = 0;  s[2][1] = 0;  s[2][2] = 0;
        t[0][0].setImageResource(R.drawable.vain);
        t[0][1].setImageResource(R.drawable.vain);
        t[0][2].setImageResource(R.drawable.vain);
        t[1][0].setImageResource(R.drawable.vain);
        t[1][1].setImageResource(R.drawable.vain);
        t[1][2].setImageResource(R.drawable.vain);
        t[2][0].setImageResource(R.drawable.vain);
        t[2][1].setImageResource(R.drawable.vain);
        t[2][2].setImageResource(R.drawable.vain);
        nextStep();
    }

    private void SetIntent(){
        Intent intent = new Intent(Game.this, MainActivity.class);
        startActivity(intent);
    }

    private boolean isEmpty( int i ) {
        if ( s[(i-1)/3][(i-1)%3] == 0 )
            return true;
        return false;
    }

    private boolean ifWin() {
        if ( s[0][0] == s[0][1] && s[0][1] == s[0][2] && s[0][1] == 1 )
            return true;
        else if ( s[1][0] == s[1][1] && s[1][1] == s[1][2] && s[1][1] == 1 )
            return true;
        else if ( s[2][0] == s[2][1] && s[2][1] == s[2][2] && s[2][1] == 1 )
            return true;
        else if ( s[0][0] == s[1][0] && s[1][0] == s[2][0] && s[1][0] == 1 )
            return true;
        else if ( s[0][1] == s[1][1] && s[1][1] == s[2][1] && s[1][1] == 1 )
            return true;
        else if ( s[0][2] == s[1][2] && s[1][2] == s[2][2] && s[1][2] == 1 )
            return true;
        else if ( s[0][0] == s[1][1] && s[1][1] == s[2][2] && s[1][1] == 1 )
            return true;
        else if ( s[0][2] == s[1][1] && s[1][1] == s[2][0] && s[1][1] == 1 )
            return true;
        else
            return false;
    }

    private boolean ifLose() {
        if ( s[0][0] == s[0][1] && s[0][1] == s[0][2] && s[0][1] == -1 )
            return true;
        else if ( s[1][0] == s[1][1] && s[1][1] == s[1][2] && s[1][1] == -1 )
            return true;
        else if ( s[2][0] == s[2][1] && s[2][1] == s[2][2] && s[2][1] == -1 )
            return true;
        else if ( s[0][0] == s[1][0] && s[1][0] == s[2][0] && s[1][0] == -1 )
            return true;
        else if ( s[0][1] == s[1][1] && s[1][1] == s[2][1] && s[1][1] == -1 )
            return true;
        else if ( s[0][2] == s[1][2] && s[1][2] == s[2][2] && s[1][2] == -1 )
            return true;
        else if ( s[0][0] == s[1][1] && s[1][1] == s[2][2] && s[1][1] == -1 )
            return true;
        else if ( s[0][2] == s[1][1] && s[1][1] == s[2][0] && s[1][1] == -1 )
            return true;
        else
            return false;
    }

    private boolean ifFlat() {
        if ( s[0][0] != 0 && s[0][1] != 0 && s[0][2] != 0 &&
                s[1][0] != 0 && s[1][1] != 0 && s[1][2] != 0 &&
                s[2][0] != 0 && s[2][1] != 0 && s[2][2] != 0 )
            return true;
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1: {
                if ( isEmpty(1) ) {
                    t[0][0].setImageResource(R.drawable.circle);
                    s[0][0] = 1;
                    if ( ifWin() ) {
                        Toast.makeText(Game.this,"您赢了",Toast.LENGTH_SHORT).show();
                        SetIntent();
                    } else
                        nextStep();
                }
                break;
            }
            case R.id.tab2: {
                if ( isEmpty(2) ) {
                    t[0][1].setImageResource(R.drawable.circle);
                    s[0][1] = 1;
                    if ( ifWin() ) {
                        Toast.makeText(Game.this, "您赢了", Toast.LENGTH_SHORT).show();
                        SetIntent();
                    } else
                        nextStep();
                }
                break;
            }
            case R.id.tab3: {
                if ( isEmpty(3) ) {
                    t[0][2].setImageResource(R.drawable.circle);
                    s[0][2] = 1;
                    if ( ifWin() ) {
                        Toast.makeText(Game.this,"您赢了",Toast.LENGTH_SHORT).show();
                        SetIntent();
                    } else
                        nextStep();
                }
                break;
            }
            case R.id.tab4: {
                if ( isEmpty(4) ) {
                    t[1][0].setImageResource(R.drawable.circle);
                    s[1][0] = 1;
                    if (ifWin()){
                        Toast.makeText(Game.this,"您赢了",Toast.LENGTH_SHORT).show();
                        SetIntent();
                    } else
                        nextStep();
                }
                break;
            }
            case R.id.tab5: {
                if ( isEmpty(5) ) {
                    t[1][1].setImageResource(R.drawable.circle);
                    s[1][1] = 1;
                    if (ifWin()){
                        Toast.makeText(Game.this,"您赢了",Toast.LENGTH_SHORT).show();
                        SetIntent();
                    } else
                        nextStep();
                }
                break;
            }
            case R.id.tab6: {
                if ( isEmpty(6) ) {
                    t[1][2].setImageResource(R.drawable.circle);
                    s[1][2] = 1;
                    if (ifWin()){
                        Toast.makeText(Game.this,"您赢了",Toast.LENGTH_SHORT).show();
                        SetIntent();
                    } else
                        nextStep();
                }
                break;
            }
            case R.id.tab7: {
                if ( isEmpty(7) ) {
                    t[2][0].setImageResource(R.drawable.circle);
                    s[2][0] = 1;
                    if (ifWin()){
                        Toast.makeText(Game.this,"您赢了",Toast.LENGTH_SHORT).show();
                        SetIntent();
                    } else
                        nextStep();
                }
                break;
            }
            case R.id.tab8: {
                if ( isEmpty(8) ) {
                    t[2][1].setImageResource(R.drawable.circle);
                    s[2][1] = 1;
                    if (ifWin()){
                        Toast.makeText(Game.this,"您赢了",Toast.LENGTH_SHORT).show();
                        SetIntent();
                    } else
                        nextStep();
                }
                break;
            }
            case R.id.tab9: {
                if ( isEmpty(9) ) {
                    t[2][2].setImageResource(R.drawable.circle);
                    s[2][2] = 1;
                    if (ifWin()){
                        Toast.makeText(Game.this,"您赢了",Toast.LENGTH_SHORT).show();
                        SetIntent();
                    } else
                        nextStep();
                }
                break;
            }
        }
    }
}
