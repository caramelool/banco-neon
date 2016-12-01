package br.com.neon.model;

import android.text.TextUtils;

public class User {
    private String nome;
    private String email;
    private String imageUrl;
    private String token;

    public User(String nome, String email, String imageUrl, String token) {
        this.nome = nome;
        this.email = email;
        this.imageUrl = imageUrl;
        this.token = token;
    }

    public String getNome() {
        return nome;
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
