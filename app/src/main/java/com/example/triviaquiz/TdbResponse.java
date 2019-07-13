package com.example.triviaquiz;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/****************************************************
    this class represents a Tdb query's json result
    as a POJO
 ***************************************************/

public class TdbResponse {

    private float response_code;
    List<QuestionData> results = new ArrayList<>();

    public TdbResponse(){

    }

    public TdbResponse(float response_code, List<QuestionData> results){
        this.results = results;
        this.response_code = response_code;

    }


    // Getter Methods

    public float getResponse_code() {
        return response_code;
    }

    // Setter Methods

    public void setResponse_code(float response_code) {
        this.response_code = response_code;
    }

    public List<QuestionData> getResults() {
        return results;
    }
}
