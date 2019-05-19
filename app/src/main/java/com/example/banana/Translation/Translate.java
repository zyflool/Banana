package com.example.banana.Translation;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banana.R;
import com.example.banana.network.Bean.TranslateBean;
import com.example.banana.network.RetrofitWrapper;
import com.example.banana.network.Service.TranslateService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Translate extends AppCompatActivity implements View.OnClickListener{
    private Button from, to, translate;
    private EditText fromText;
    private TextView toText;
    private Toolbar mtoolbar;
    private String fromId = "en", toId = "zh";
    String[] items = {"中文",    "英语",   "粤语",     "文言文",      "日语",
                      "韩语",    "法语",   "西班牙语",  "泰语",       "阿拉伯语",
                      "俄语",    "葡萄牙语","德语",     "意大利语",    "希腊语",
                      "荷兰语",  "波兰语",  "保加利亚语","爱沙尼亚语",  "丹麦语",
                      "芬兰语",  "捷克语",  "罗马尼亚语","斯洛文尼亚语","瑞典语",
                      "匈牙利语","繁体中文", "越南语"};
    String[] Id = {"zh", "en","yue","wyw","jp", "kor","fra","spa","th", "ara",
                   "ru", "pt","de", "it", "el", "nl", "pl", "bul","est","dan",
                   "fin","cs","rom","slo","swe","hu", "cht","vie"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_translate);
        initView();
    }

    private void initView(){
        from = findViewById(R.id.button_from);
        to = findViewById(R.id.button_to);
        translate = findViewById(R.id.button_translate);
        fromText = findViewById(R.id.edittext_from);
        toText = findViewById(R.id.textview_to);

        mtoolbar = findViewById(R.id.toolbar_translate);
        setSupportActionBar(mtoolbar);
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        from.setOnClickListener(this);
        to.setOnClickListener(this);
        translate.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_from : {
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("请选择语言")
                        .setSingleChoiceItems(items,-1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                from.setText(items[which]);
                                fromId =Id[which];
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                break;
            }
            case R.id.button_to : {
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("请选择语言")
                        .setSingleChoiceItems(items,-1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                to.setText(items[which]);
                                toId =Id[which];
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                break;
            }
            case R.id.button_translate :{
                String mfrom = fromText.getText().toString();
                request(mfrom);
                break;
            }
        }
    }

    private void request (String mfrom) {
        TranslateService translateService = RetrofitWrapper.getInstance().create(TranslateService.class);
        Call<TranslateBean> call = translateService.translate(mfrom,fromId,toId);
        call.enqueue(new Callback<TranslateBean>() {
            @Override
            public void onResponse(Call<TranslateBean> call, Response<TranslateBean> response) {
                if ( response.isSuccessful() ) {

                } else
                    Toast.makeText(Translate.this,"加载失败，请重试", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<TranslateBean> call, Throwable t) {
                Toast.makeText(Translate.this,"请检查网络连接", Toast.LENGTH_LONG).show();
            }
        });
    }
}
