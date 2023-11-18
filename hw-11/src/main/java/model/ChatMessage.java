package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatMessage {

    @JsonProperty("ROWID")
    private int rowId;
    @JsonProperty("attributedBody")
    private String attributeBody;
    @JsonProperty("belong_number")
    private String belongNumber;
    @JsonProperty("date")
    private long date;
    @JsonProperty("date_read")
    private long dateRead;
    @JsonProperty("guid")
    private String guid;
    @JsonProperty("handle_id")
    private int handleId;
    @JsonProperty("has_dd_results")
    private int hasDdResults;
    @JsonProperty("is_deleted")
    private int isDeleted;
    @JsonProperty("is_from_me")
    private int isForMe;
    @JsonProperty("send_date")
    private String sendDate;
    @JsonProperty("send_status")
    private int sendStatus;
    @JsonProperty("service")
    private String service;
    @JsonProperty("text")
    private String text;

    public ChatMessage() {
    }

    public int getRowId() {
        return this.rowId;
    }

    public String getAttributeBody() {
        return this.attributeBody;
    }

    public String getBelongNumber() {
        return this.belongNumber;
    }

    public long getDate() {
        return this.date;
    }

    public long getDateRead() {
        return this.dateRead;
    }

    public String getGuid() {
        return this.guid;
    }

    public int getHandleId() {
        return this.handleId;
    }

    public int getHasDdResults() {
        return this.hasDdResults;
    }

    public int getIsDeleted() {
        return this.isDeleted;
    }

    public int getIsForMe() {
        return this.isForMe;
    }

    public String getSendDate() {
        return this.sendDate;
    }

    public int getSendStatus() {
        return this.sendStatus;
    }

    public String getService() {
        return this.service;
    }

    public String getText() {
        return this.text;
    }

    public String toString() {
        return "ChatMessage(rowId=" + this.getRowId() + ", attributeBody=" + this.getAttributeBody() + ", belongNumber=" + this.getBelongNumber() + ", date=" + this.getDate() + ", dateRead=" + this.getDateRead() + ", guid=" + this.getGuid() + ", handleId=" + this.getHandleId() + ", hasDdResults=" + this.getHasDdResults() + ", isDeleted=" + this.getIsDeleted() + ", isForMe=" + this.getIsForMe() + ", sendDate=" + this.getSendDate() + ", sendStatus=" + this.getSendStatus() + ", service=" + this.getService() + ", text=" + this.getText() + ")";
    }
}
