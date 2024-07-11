package com.example.polusServiceRequest.DTOs;

import java.sql.Timestamp;

public class CountryDTO {
    private String countryCode;
    private String countryName;
    private String currencyCode;
    private Timestamp updateTimestamp;
    private String updateUser;
    private String countryCodeIso2;

    // Constructor
    public CountryDTO(String countryCode, String countryName, String currencyCode, Timestamp updateTimestamp,
                      String updateUser, String countryCodeIso2) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.currencyCode = currencyCode;
        this.updateTimestamp = updateTimestamp;
        this.updateUser = updateUser;
        this.countryCodeIso2 = countryCodeIso2;
    }

    // Getters and Setters
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Timestamp getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getCountryCodeIso2() {
        return countryCodeIso2;
    }

    public void setCountryCodeIso2(String countryCodeIso2) {
        this.countryCodeIso2 = countryCodeIso2;
    }
}
