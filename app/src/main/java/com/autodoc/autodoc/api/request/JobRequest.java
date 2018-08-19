package com.autodoc.autodoc.api.request;

/**
 * Created by ilabs on 8/17/18.
 */

public class JobRequest {
    public String name;
    public String jobDescription;
    public String status;
    public long lat;
    public long lon;
    public String address;
    public String technicianId;

    public void setName(String name) {
        this.name = name;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }
}
