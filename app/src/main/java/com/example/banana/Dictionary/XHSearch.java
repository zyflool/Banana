package com.example.banana.Dictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banana.R;
import com.example.banana.Dictionary.SearchHistoryAdapter;
import com.example.banana.Dictionary.SearchData;
import com.example.banana.Dictionary.CustomerListView;

public class XHSearch extends Activity implements OnClickListener {
    private EditText etSearch;
    private ImageView ivDeleteSearch;
    private TextView tvSearch;
    private TextView tvHistory;

    private CustomerListView lvHistory;
    private Button btnClearHistory;

    private static final String SEARCH_HISTORY = "search_history";
    private SharedPreferences sp;
    private SearchHistoryAdapter mAdapter;
    private List<SearchData> lstHistory;
    private List<SearchData> lstAllHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xhsearch);
        initViews();
        initData();
        setListener();
    }

    private void initViews() {
        tvSearch = (TextView) findViewById(R.id.tv_search);
        etSearch = (EditText) findViewById(R.id.et_search);
        tvHistory = (TextView) findViewById(R.id.tv_history);
        lvHistory = (CustomerListView) findViewById(R.id.lv_history);
        btnClearHistory = (Button) findViewById(R.id.btn_clear_history);
    }

    private void initData() {
        sp = getSharedPreferences(SEARCH_HISTORY, 0);
        lstAllHistory = new ArrayList<SearchData>();
        lstHistory = new ArrayList<SearchData>();
        readSearchHistory();
        lstHistory.addAll(lstAllHistory);
        mAdapter = new SearchHistoryAdapter(this, lstHistory);
        lvHistory.setAdapter(mAdapter);
        Log.i("TEST", "长度---" + lstHistory.size());
        if (lstHistory.size() < 1) {
            lvHistory.setVisibility(View.GONE);
            btnClearHistory.setVisibility(View.GONE);
        }
    }

    private void setListener() {
        ivDeleteSearch.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        btnClearHistory.setOnClickListener(this);

        lvHistory.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                SearchData data = (SearchData) mAdapter.getItem(position);
                etSearch.setText(data.getContent());
                tvSearch.performClick();
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performFiltering(s);
                if (s.length() > 0) {
                    ivDeleteSearch.setVisibility(View.VISIBLE);
                    tvSearch.setText("搜索");
                    tvHistory.setText("猜你想搜");
                } else {
                    ivDeleteSearch.setVisibility(View.INVISIBLE);
                    tvSearch.setText("取消");
                    tvHistory.setText("历史记录");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_delete_search:// 清空搜索内容
                etSearch.setText("");
                ivDeleteSearch.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_search:// 搜索按钮
                String searchContent = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(searchContent)) {
                    XHSearch.this.finish();
                } else {
                    saveSearchHistory(searchContent);
                    readSearchHistory();
                    if (lstHistory.size() > 0) {
                        lstHistory.clear();
                    }
                    lstHistory.addAll(lstAllHistory);
                    mAdapter.notifyDataSetChanged();
                    lvHistory.setVisibility(View.VISIBLE);
                    btnClearHistory.setVisibility(View.VISIBLE);
                    Toast.makeText(XHSearch.this, searchContent, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_clear_history:// 清除历史记录
                clearAllSearchHistory();
                readSearchHistory();
                if (lstHistory.size() > 0) {
                    lstHistory.clear();
                }
                lstHistory.addAll(lstAllHistory);
                mAdapter.notifyDataSetChanged();
                lvHistory.setVisibility(View.GONE);
                btnClearHistory.setVisibility(View.GONE);
                Toast.makeText(XHSearch.this, "清空历史记录成功", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    /**
     * 匹配过滤搜索内容
     *
     *
     *            输入框中输入的内容
     */
    public void performFiltering(CharSequence inputContent) {
        if (TextUtils.isEmpty(inputContent)) {// 搜索框内容为空的时候显示所有历史记录
            if (lstHistory.size() > 0) {
                lstHistory.clear();
            }
            lstHistory.addAll(lstAllHistory);
        } else {
            String inputContentString = inputContent.toString().toLowerCase();
            int count = lstAllHistory.size();
            List<SearchData> lstFilterHistory = new ArrayList<SearchData>(count);
            for (int i = 0; i < count; i++) {
                String value = lstAllHistory.get(i).getContent();
                String valueText = value.toLowerCase();
                if (valueText.contains(inputContentString)) {// 包含输入的内容
                    // 这个和下面的判断根据设计来做选择
                }
                if (valueText.startsWith(inputContentString)) {// 以输入的内容开头
                    lstFilterHistory.add(new SearchData().setContent(valueText));
                } else {// 判断有空格的搜索内容中某一段是否包含输入内容开头的
                    String[] words = valueText.split(" ");
                    int wordCount = words.length;
                    for (int k = 0; k < wordCount; k++) {
                        if (words[k].startsWith(inputContentString)) {
                            lstFilterHistory.add(new SearchData().setContent(value));
                            break;
                        }
                    }
                }
                // if (mMaxMatch > 0) {// 过滤后的历史记录只显示mMaxMatch条
                // if (newValues.size() > mMaxMatch - 1) {
                // break;
                // }
                // }
            }
            if (lstHistory.size() > 0) {
                lstHistory.clear();
            }
            lstHistory.addAll(lstFilterHistory);
        }
        mAdapter.notifyDataSetChanged();
        if (lstHistory.size() < 1) {
            lvHistory.setVisibility(View.GONE);
            btnClearHistory.setVisibility(View.GONE);
        }else{
            lvHistory.setVisibility(View.VISIBLE);
            btnClearHistory.setVisibility(View.VISIBLE);
        }
    }

    /*
     * 保存搜索记录
     */
    private void saveSearchHistory(String searchContent) {
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longhistory.split(",");

        List<String> lstHistory = new ArrayList<String>(Arrays.asList(tmpHistory));
        if (lstHistory.size() > 0) {// 移除历史，添加新数据
            for (int i = 0; i < lstHistory.size(); i++) {
                if (searchContent.equals(lstHistory.get(i))) {
                    lstHistory.remove(i);
                    break;
                }
            }
            lstHistory.add(0, searchContent);
        }
        if (lstHistory.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < lstHistory.size(); i++) {
                sb.append(lstHistory.get(i) + ",");
            }
            sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
        } else {// 添加新数据
            sp.edit().putString(SEARCH_HISTORY, searchContent + ",").commit();
        }
    }

    /**
     * 读取历史搜索记录
     */
    public void readSearchHistory() {
        if (lstAllHistory.size() > 0) {
            lstAllHistory.clear();
        }
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        if (TextUtils.isEmpty(longhistory)) {
            return;
        }
        String[] hisArrays = longhistory.split(",");
        for (int i = 0; i < hisArrays.length; i++) {
            lstAllHistory.add(new SearchData().setContent(hisArrays[i]));
        }
    }

    /**
     * 清空全部历史搜索记录
     */
    public void clearAllSearchHistory() {
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

}
