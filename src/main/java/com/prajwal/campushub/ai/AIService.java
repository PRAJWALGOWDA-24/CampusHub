package com.prajwal.campushub.ai;

import com.prajwal.campushub.entity.ChatHistory;
import com.prajwal.campushub.repository.ChatHistoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AIService {

    private final ChatClient chatClient;

    @Autowired
    private ChatHistoryRepository repository;

    public AIService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String askAI(String prompt) {

        String finalPrompt = """
                You are CampusHub AI.

                You help engineering students with:
                - Java
                - Spring Boot
                - SQL
                - DSA
                - Resume reviews
                - Placement preparation
                - Interview questions
                - Career guidance

                Answer clearly and in simple English.

                Student Question:
                """ + prompt;

        String answer = chatClient
                .prompt(finalPrompt)
                .call()
                .content();

        ChatHistory chat = new ChatHistory();
        chat.setPrompt(prompt);
        chat.setResponse(answer);
        chat.setCreatedAt(LocalDateTime.now());

        repository.save(chat);

        return answer;
    }
    public List<ChatHistory> getAllChats() {

        return repository.findAll();

    }

    public String deleteChat(Long id) {

        repository.deleteById(id);

        return "Chat deleted successfully.";

    }
}


/*
  u can replce above retunr u add mean then only partculir senten request it accepts hepful for stduents
String finalPrompt = """
You are CampusHub AI.

You help engineering students with:
- Java
- Spring Boot
- SQL
- DSA
- Resume reviews
- Placement preparation
- Interview questions
- Career guidance

Answer clearly and in simple English.

Student Question:
""" + prompt;

return chatClient
        .prompt(finalPrompt)
        .call()
        .content();
 */