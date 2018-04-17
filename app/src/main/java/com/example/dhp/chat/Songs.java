package com.example.dhp.chat;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by NISHANT on 17-04-2018.
 */

public class Songs extends AppCompatActivity {

    String url;
    String json_url;
    MainActivity mainActivity;
    ArrayList <String> songsList;

    public Songs(String emotion, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        songsList = new ArrayList<>();
        getSongs(emotion);
    }

    public String getURL(String emotionResult) {

        if (emotionResult == "Anger") { //Rock
            json_url = "http://api.musixmatch.com/ws/1.1/track.search?apikey=caefbed415c8923511986a670ebfc6c0&chart.tracks.get?page=1&page_size=20&country=us&f_has_lyrics=1&f_music_genre_id=21&s_track_rating=DESC";
        } else if (emotionResult == "Happy") { // Pop
            json_url = "http://api.musixmatch.com/ws/1.1/track.search?apikey=caefbed415c8923511986a670ebfc6c0&chart.tracks.get?page=1&page_size=20&country=us&f_has_lyrics=1&f_music_genre_id=14&s_track_rating=DESC";
        } else if (emotionResult == "Contempt") { // Pop
            json_url = "http://api.musixmatch.com/ws/1.1/track.search?apikey=caefbed415c8923511986a670ebfc6c0&chart.tracks.get?page=1&page_size=20&country=us&f_has_lyrics=1&f_music_genre_id=14&s_track_rating=DESC";
        } else if (emotionResult == "Disgust") { // Hip-Hop
            json_url = "http://api.musixmatch.com/ws/1.1/track.search?apikey=caefbed415c8923511986a670ebfc6c0&chart.tracks.get?page=1&page_size=20&country=us&f_has_lyrics=1&f_music_genre_id=18&s_track_rating=DESC";
        } else if (emotionResult == "Fear") { // Country
            json_url = "http://api.musixmatch.com/ws/1.1/track.search?apikey=caefbed415c8923511986a670ebfc6c0&chart.tracks.get?page=1&page_size=20&country=us&f_has_lyrics=1&f_music_genre_id=14&s_track_rating=DESC";
        } else if (emotionResult == "Neutral") { // Popular tracks
            json_url = "http://api.musixmatch.com/ws/1.1/track.search?apikey=caefbed415c8923511986a670ebfc6c0&chart.tracks.get?page=1&page_size=20&country=us&f_has_lyrics=1&s_track_rating=DESC";
        } else if (emotionResult == "Sad") { // Pop
            json_url = "http://api.musixmatch.com/ws/1.1/track.search?apikey=caefbed415c8923511986a670ebfc6c0&chart.tracks.get?page=1&page_size=20&country=us&f_has_lyrics=1&f_music_genre_id=14&s_track_rating=DESC";
        } else if (emotionResult == "Surprise") { // Alternative
            json_url = "http://api.musixmatch.com/ws/1.1/track.search?apikey=caefbed415c8923511986a670ebfc6c0&chart.tracks.get?page=1&page_size=20&country=us&f_has_lyrics=1&f_music_genre_id=20&s_track_rating=DESC";
        } else { // Popular
            json_url = "http://api.musixmatch.com/ws/1.1/track.search?apikey=caefbed415c8923511986a670ebfc6c0&chart.tracks.get?page=1&page_size=20&country=us&f_has_lyrics=1&s_track_rating=DESC";
        }
        return json_url;
    }


    public void getSongs(String emotion) {
        url = getURL(emotion);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json_string) {
                JSONObject jsonObject;
                JSONArray jsonArray;
//                json_string = getIntent().getExtras().getString("json_data");
                try {
                    jsonObject = json_string.getJSONObject("message").getJSONObject("body");
                    jsonArray = jsonObject.getJSONArray("track_list");
                    for(int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        JSONObject track = obj.getJSONObject("track");
                        String trackName = track.getString("track_name");
                        String artistName = track.getString("artist_name");
                        songsList.add(trackName + " : " + artistName);
                        Log.v("AAAAAAAAAAA", trackName + " : " + artistName);
                    }
//                    jsonObject = new JSONObject(json_string);
                    Log.d(MainActivity.customLog, json_string+"::ye toh json_sting hain");
//                    jsonArray = jsonObject.getJSONObject("message").getJSONObject("body").getJSONArray("track_list");
//                    int count = 0;
//                    String songList = "";
//                    while (count < jsonArray.length()) {
//                        JSONObject JO1 = jsonArray.getJSONObject(count);
//                        String title, artist, duration;
//                        title = JO1.getJSONObject("track").getString("track_name");
////                        duration = JO1.getJSONObject("track").getString("track_length");
////                        conv = Float.parseFloat(duration);
////                        int minutes = (int)conv / (60);
////                        int seconds = (int) conv % 60;
////                        duration = String.format("%d:%02d", minutes, seconds);
//                        artist = JO1.getJSONObject("track").getString(("commontrack_vanity_id"));
//                        artist = artist.split("/")[0];
//                        artist = artist.replaceAll("-", " ");
//                        songList += title;
////                        Toast.makeText(Songs.this, songList, Toast.LENGTH_SHORT).show();
//                        Log.d(MainActivity.customLog, songList);
////                        Songs songs = new Songs(title, duration, artist);
////                        songsAdapter.add(songs);
////                        count++;
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        });
        SingletonRequestQueue.getInstance(this.mainActivity.getApplicationContext()).addToRequestQueue(stringRequest);

    }
}
