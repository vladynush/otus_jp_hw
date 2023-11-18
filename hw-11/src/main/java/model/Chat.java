package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class Chat {

    @JsonProperty("chat_sessions")
    private Set<ChatSession> chatSessions;

    public Chat() {
    }

    public Set<ChatSession> getChatSessions() {
        return this.chatSessions;
    }
}
