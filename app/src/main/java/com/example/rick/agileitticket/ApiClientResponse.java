package com.example.rick.agileitticket;
import com.example.rick.agileitticket.android.Global;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import retrofit2.http.Field;

public class ApiClientResponse {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("EventDate")
    @Expose
    private String eventDate;
    @SerializedName("EventTime")
    @Expose
    private String eventTime;
    @SerializedName("EventName")
    @Expose
    private String eventName;
    @SerializedName("WasInPast")
    @Expose
    private Boolean wasInPast;
    @SerializedName("IsPresent")
    @Expose
    private Boolean isPresent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Boolean getWasInPast() {
        return wasInPast;
    }

    public void setWasInPast(Boolean wasInPast) {
        this.wasInPast = wasInPast;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("firstName", firstName).append("lastName", lastName).append("companyName", companyName).append("eventDate", eventDate).append("eventTime", eventTime).append("eventName", eventName).append("wasInPast", wasInPast).append("isPresent", isPresent).toString();
    }

}