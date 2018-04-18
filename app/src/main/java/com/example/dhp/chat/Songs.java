package com.example.dhp.chat;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Songs extends AppCompatActivity {

    String url;
    String json_url;
    MainActivity mainActivity;
    ChatActivity chatActivity;
    ArrayList<String> songsList;

    public Songs(String emotion, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        songsList = new ArrayList<>();
        getSongs(emotion);
    }

    public Songs(String emotion, ChatActivity chatActivity) {
        Log.d(MainActivity.customLog, "It calls songs ca");
        this.chatActivity = chatActivity;
        songsList = new ArrayList<>();
        getSongsX(emotion);

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
                        try {
                            jsonObject = json_string.getJSONObject("message").getJSONObject("body");
                            jsonArray = jsonObject.getJSONArray("track_list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                JSONObject track = obj.getJSONObject("track");
                                String trackName = track.getString("track_name");
                                String artistName = track.getString("artist_name");
                                songsList.add(trackName + " : " + artistName);
                                Log.v(MainActivity.customLog, trackName + " : " + artistName);
                            }
                            Log.d(MainActivity.customLog, json_string + "::ye toh json_sting hain");


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
                Log.i(MainActivity.customLog, errorMessage);
                error.printStackTrace();
            }
        });
        SingletonRequestQueue.getInstance(this.mainActivity.getApplicationContext()).addToRequestQueue(stringRequest);

    }

    public void getSongsX(String emotion) {
        url = getURL(emotion);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject json_string) {
                        JSONObject jsonObject;
                        JSONArray jsonArray;
                        try {
                            jsonObject = json_string.getJSONObject("message").getJSONObject("body");
                            jsonArray = jsonObject.getJSONArray("track_list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                JSONObject track = obj.getJSONObject("track");
                                String trackName = track.getString("track_name");
                                String artistName = track.getString("artist_name");
                                songsList.add(trackName + " : " + artistName);
                                Log.v(MainActivity.customLog, trackName + " : " + artistName);

                            }

                            chatActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    chatActivity.songsHistory.setText("These are recommended songs:");
                                    for (int i = 0; i < songsList.size(); i++) {
                                        chatActivity.songsHistory.append("\n" + songsList.get(i));
                                    }

                                }
                            });

                            Log.d(MainActivity.customLog, json_string + "::ye toh json_sting hain");

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
                Log.i(MainActivity.customLog, errorMessage);
                error.printStackTrace();
            }
        });
        SingletonRequestQueue.getInstance(this.chatActivity.getApplicationContext()).addToRequestQueue(stringRequest);
    }


}
