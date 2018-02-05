package acad277.patel.sonika.test.model;

import java.io.Serializable;

/**
 * Created by Sonika on 4/26/17.
 */

public class Spot implements Serializable{
    private String spotName;
    private String Address;
    private String Outlets;
    private String Wifi;
    private String closing_time;
    private int id;
    private String imageURL;

    private String spot_type;
    private String start_time;

    @Override
    public String toString() {
        return "spots{" +
                "spotName='" + spotName + '\'' +
                ", Address='" + Address + '\'' +
                ", Outlets=" + Outlets +
                ", closing_time_id=" + closing_time +
                ", id=" + id +
                ", imageURL='" + imageURL + '\'' +
                ", spot_type_id=" + spot_type +
                ", start_time_id=" + start_time +
                '}';
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getOutlets() {
        return Outlets;
    }

    public void setOutlets(String outlets) {
        Outlets = outlets;
    }

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getWifi() {
        return Wifi;
    }

    public void setWifi(String wifi) {
        Wifi = wifi;
    }
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSpot_type() {
        return spot_type;
    }

    public void setSpot_type(String spot_type) {
        this.spot_type = spot_type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}

