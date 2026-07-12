package com.prajwal.campushub.ai;

import com.prajwal.campushub.dto.ChatRequest;
import com.prajwal.campushub.dto.ChatResponse;
import com.prajwal.campushub.entity.ChatHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {

        String answer = aiService.askAI(request.getPrompt());

        return new ChatResponse(answer);
    }
    @GetMapping("/history")
    public List<ChatHistory> getHistory() {

        return aiService.getAllChats();

    }

    @DeleteMapping("/history/{id}")
    public String deleteChat(@PathVariable Long id) {

        return aiService.deleteChat(id);

    }
}