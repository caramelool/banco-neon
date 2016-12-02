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
    private double transfer = - 1;

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
        //TODO MOCK
        if (transfer == -1) {
            if (id.equals("2")) {
                return 90.90;
            } else if (id.equals("3")) {
                return 320.10;
            } else if (id.equals("10")) {
                return 402.00;
            }
            return 0;
        }
        return transfer;
    }

    public String getTransferFormatted() {
        return DecimalFormat.getCurrencyInstance()
                .format(getTransfer());
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
