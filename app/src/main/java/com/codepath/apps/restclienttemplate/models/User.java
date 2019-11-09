package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity
public class User {
    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String screenName;

    @ColumnInfo
    public String publicImageUrl;

    // Empty constructor for Parcel
    public User() {}

    public static User fromJson(JSONObject json) throws JSONException {
        User user = new User();

        user.id = json.getLong("id");
        user.name = json.getString("name");
        user.screenName = json.getString("screen_name");
        user.publicImageUrl = json.getString("profile_image_url_https");
        return user;

    }

    public static List<User> fromJsonTweetArray(List<Tweet> tweetsFromNetwork) {
        List<User> users = new ArrayList<>();
        for (Tweet tweet: tweetsFromNetwork){
            users.add(tweet.user);
        }
        return users;
    }
}
