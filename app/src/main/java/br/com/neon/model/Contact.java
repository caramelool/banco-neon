package br.com.neon.model;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class Contact {
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("email")
    private String email;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("transfer")
    private float transfer;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getTransfer() {
        return transfer;
    }

    public String getTransferFormatted() {
        return DecimalFormat.getCurrencyInstance()
                .format(transfer);
    }
}
