package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatMember {

    @JsonProperty("first")
    private String first;
    @JsonProperty("handle_id")
    private int handleId;
    @JsonProperty("image_path")
    private String imagePath;
    @JsonProperty("last")
    private String last;
    @JsonProperty("middle")
    private String middle;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("service")
    private String service;
    @JsonProperty("thumb_path")
    private String thumbPath;

    public ChatMember() {
    }

    public String getFirst() {
        return this.first;
    }

    public int getHandleId() {
        return this.handleId;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public String getLast() {
        return this.last;
    }

    public String getMiddle() {
        return this.middle;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getService() {
        return this.service;
    }

    public String getThumbPath() {
        return this.thumbPath;
    }

    public String toString() {
        return "ChatMember(first=" + this.getFirst() + ", handleId=" + this.getHandleId() + ", imagePath=" + this.getImagePath() + ", last=" + this.getLast() + ", middle=" + this.getMiddle() + ", phoneNumber=" + this.getPhoneNumber() + ", service=" + this.getService() + ", thumbPath=" + this.getThumbPath() + ")";
    }
}
