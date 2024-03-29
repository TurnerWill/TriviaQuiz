package com.example.triviaquiz;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements QuestionAdapter.OnAnswerClicked {

    ImageView samApproves;
    ImageView samDisapproves;
    TextView textView;
    Button button1, button2, button3, button4; // answer choices to a trivia question
    String TAG = "test1";
    String TAG_LIST_MADE = "response";
    RecyclerView recyclerView;
    int layoutId = R.layout.activity_main;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("layoutId", layoutId);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //force landscape orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // save state of views when paging
        if (savedInstanceState != null) {
            layoutId = savedInstanceState.getInt("layoutId", R.layout.activity_main);
        }
        setContentView(layoutId);


        // http request to trivia db initiated
        volleyRequest();

        textView = findViewById(R.id.tv_question);

        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);
        button3 = findViewById(R.id.btn3);
        button4 = findViewById(R.id.btn4);
        samApproves = findViewById(R.id.sam1);
        samDisapproves = findViewById(R.id.sam2);

        recyclerView = findViewById(R.id.trivia_question);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setHasFixedSize(true);


    }

    @Override
    public void answerClicked(String answer) {
        //Toast.makeText(this, "Check if this button's text is answer", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void playSong(boolean flag) {
        MediaPlayer mp1 = new MediaPlayer();

        if(flag ){ // correct answer boolean true

            if(mp1.isPlaying()){
                mp1.stop();
                mp1.release();
            }
                mp1 = MediaPlayer.create(this, R.raw.smart_mofo);
                mp1.start();



        }
        else { // incorrect answer boolean false
            if(mp1.isPlaying()){
                mp1.stop();
                mp1.release();
            }
                mp1 = MediaPlayer.create(this, R.raw.error);
                mp1.start();
        }


    }

    private void loadRecyclerView(List<QuestionData> responses) {

        QuestionAdapter questionAdapter = new QuestionAdapter(responses, (QuestionAdapter.OnAnswerClicked) MainActivity.this);
        recyclerView.setAdapter(questionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.HORIZONTAL, false));

        // add view pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

    }

    //**************************************
    //      Volley  http url request method
    //*************************************

    public void volleyRequest() {
        //1. setup url
        String url = "https://opentdb.com/api.php?amount=50";

        //2. create request queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        //3. create json array request or json object request
        // then init it with new json array request or json object request
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET
                , url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    // Param 2: success listener
                    @Override
                    public void onResponse(JSONObject response) {
                        List<QuestionData> questionDataList = new ArrayList<>();
                        Log.d(TAG, "onResponse " + response);
                        try {
                            Log.d(TAG, "onResponse response code is " + response.getInt("response_code"));
                            Log.d(TAG, "onResponse response result is " + response.getJSONArray("results"));

                            // Since the results a is array we store it in this JSONArray Object
                            JSONArray jsonQuestionArray = response.getJSONArray("results");

                            // Create type which we are expecting from the Json
                            Type type = new TypeToken<List<QuestionData>>() {
                            }.getType();
                            // Use Gson to convert the json String into the type we are expecting
                            questionDataList = new Gson().fromJson(jsonQuestionArray.toString(), type);
                            Log.d(TAG, "onResponse: questionDataList is " + questionDataList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loadRecyclerView(questionDataList);
                    }
                },
                new Response.ErrorListener() { // Param 3: error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        // 4. pass the request object from step 3 into the requestQueue object from step 2
        requestQueue.add(request);


    }

}
