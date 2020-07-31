package com.ashik.surveyapp.API.Client;

import com.ashik.surveyapp.API.Model.SurveyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface JSONPlaceholderApi {

    @GET("getSurvey")
    Call<List<SurveyModel>> getSurveyQuestion(@Header("timestamp") String Header);

}
