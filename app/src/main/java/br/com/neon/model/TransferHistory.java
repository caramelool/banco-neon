package br.com.neon.model;

import com.google.gson.annotations.SerializedName;

public class TransferHistory {
    @SerializedName("id")
    private int id;
    @SerializedName("ClienteId")
    private int clientId;
    @SerializedName("Valor")
    private double value;
    @SerializedName("Token")
    private String token;
    @SerializedName("Data")
    private String date;

    public String getClientId() {
        if (clientId != 0) {
            return String.valueOf(clientId);
        } else {
            return "";
        }
    }

    public double getValue() {
        return value;
    }
}
