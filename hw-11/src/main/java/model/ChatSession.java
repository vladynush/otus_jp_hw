package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class ChatSession {

    @JsonProperty("chat_id")
    private int chatId;
    @JsonProperty("chat_identifier")
    private String chatIdentifier;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("is_deleted")
    private int isDeleted;
    @JsonProperty("members")
    private Set<ChatMember> members;
    @JsonProperty("messages")
    private Set<ChatMessage> messages;

    public ChatSession() {
    }

    public int getChatId() {
        return this.chatId;
    }

    public String getChatIdentifier() {
        return this.chatIdentifier;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public int getIsDeleted() {
        return this.isDeleted;
    }

    public Set<ChatMember> getMembers() {
        return this.members;
    }

    public Set<ChatMessage> getMessages() {
        return this.messages;
    }

    public String toString() {
        return "ChatSession(chatId=" + this.getChatId() + ", chatIdentifier=" + this.getChatIdentifier() + ", displayName=" + this.getDisplayName() + ", isDeleted=" + this.getIsDeleted() + ", members=" + this.getMembers() + ", messages=" + this.getMessages() + ")";
    }
}
