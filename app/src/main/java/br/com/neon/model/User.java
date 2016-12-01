package br.com.neon.model;

import android.text.TextUtils;

public class User {
    private String name;
    private String email;
    private String imageUrl;
    private String token;

    public User(String name, String email, String imageUrl) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public User(String name, String email, String imageUrl, String token) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getToken() {
        return token;
    }

    public boolean hasToken() {
        return !TextUtils.isEmpty(token);
    }

    public void updateToken(String token) {
        this.token = token;
    }
}
