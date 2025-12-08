package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.service.GeminiAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final GeminiAIService geminiAIService;


    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {

        String prompt = request.get("prompt");

        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Prompt không được để trống."));
        }

        String aiResponseText = geminiAIService.ask(prompt);

        return ResponseEntity.ok(Map.of(
                "userMessage", prompt,
                "aiResponse", aiResponseText
        ));
    }
}