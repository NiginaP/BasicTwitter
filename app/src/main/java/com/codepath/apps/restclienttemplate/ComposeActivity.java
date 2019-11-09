package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.parceler.Parcel;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    public static final String TAG = "ComposeActivity";

    Button btnTweet;
    EditText etCompose;
    TextInputLayout tiLayout;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        btnTweet = findViewById(R.id.btnTweet);
        etCompose = findViewById(R.id.etCompose);
        tiLayout = findViewById(R.id.tiLayout);
        client = TwitterApplication.getRestClient(this);





        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetText = etCompose.getText().toString();
                if (tweetText.isEmpty()){
                    Toast.makeText( ComposeActivity.this, "Sorry, cannot tweet empty text :( ", Toast.LENGTH_LONG).show();
                    return;
                }else  if (tweetText.length() > 140){
                    Toast.makeText( ComposeActivity.this, "Sorry, your tweet is too long :( ", Toast.LENGTH_LONG).show();
                    return;
                }
                client.publishTweet(tweetText, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess publishTweet!");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Tweet published: " + tweet.body);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));

                            setResult(RESULT_OK, intent);
                            finish();

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure publishTweet", throwable);
                    }
                });

            }
        });



    }

}
