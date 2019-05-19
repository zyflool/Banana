package com.example.banana.network.Service;

import com.example.banana.network.Bean.TranslateBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TranslateService {
    @GET()
    Call<TranslateBean> translate(@Header("content") String content, @Header("fl") String fl, @Header("rl") String rl);
}
