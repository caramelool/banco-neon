package br.com.neon.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class Contact implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("email")
    private String email;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("transfer")
    private double transfer;

    public Contact() {
    }

    protected Contact(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        imageUrl = in.readString();
        transfer = in.readDouble();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public boolean hasImageUrl() {
        return !TextUtils.isEmpty(imageUrl);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getTransfer() {
        return transfer;
    }

    public String getTransferFormatted() {
        return DecimalFormat.getCurrencyInstance()
                .format(transfer);
    }

    public void setTransfer(double transfer) {
        this.transfer = transfer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(imageUrl);
        dest.writeDouble(transfer);
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
