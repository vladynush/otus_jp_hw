import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.Chat;
import model.ChatMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            File file = new File("hw-11\\src\\main\\resources\\sms.json");
            String jsonContent = Files.readString(file.toPath());

            Chat chatData = objectMapper.readValue(jsonContent, Chat.class);

            Set<ChatMessage> uniqueMessages = chatData.getChatSessions().stream()
                    .flatMap(chatSession -> chatSession.getMessages().stream())
                    .collect(Collectors.toSet());


            String outputJson = objectMapper.writeValueAsString(uniqueMessages);
            Files.writeString(new File("hw-11\\src\\main\\resources\\output.json").toPath(), outputJson);

            String deserializedJson = Files.readString(new File("output.json").toPath());
            Set<ChatMessage> deserializedMessages = objectMapper.readValue(
                    deserializedJson, objectMapper.getTypeFactory().constructCollectionType(Set.class, ChatMessage.class)
            );
            deserializedMessages.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}