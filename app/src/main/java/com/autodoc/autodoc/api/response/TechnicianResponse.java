package com.autodoc.autodoc.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ilabs on 8/19/18.
 */

public class TechnicianResponse {
    @SerializedName("jobs")
    List<JobResponse> jobResponseList;

    @SerializedName("_id")
    private String id;
    private String name;
    private String password;
    private String phone;
    private String address;
    private String type;
    private boolean isTechnician;

    public List<JobResponse> getJobResponseList() {
        return jobResponseList;
    }

    public void setJobResponseList(List<JobResponse> jobResponseList) {
        this.jobResponseList = jobResponseList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isTechnician() {
        return isTechnician;
    }

    public void setTechnician(boolean technician) {
        isTechnician = technician;
    }
}
