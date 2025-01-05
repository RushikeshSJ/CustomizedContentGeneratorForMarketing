package org.generativeai.contentgenerator.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.generativeai.contentgenerator.Model.ContentRequest;
import org.generativeai.contentgenerator.Repository.ContentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ContentService {
    @Value("${openai.api.key}")
    private String apiKey;

    private final ContentRepository contentRepository;
    private static final OkHttpClient client = new OkHttpClient();

    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public ContentRequest generateContent(ContentRequest request) {
        // Input validation
        validateRequest(request);

        try {
            String prompt = buildPrompt(request);
            String content = fetchFromOpenAI(prompt);
            request.setGeneratedContent(content);
            return contentRepository.save(request);
        } catch (IOException e) {
            // More specific error handling
            throw new RuntimeException("Failed to generate content", e);
        }
    }

    // Add input validation method
    private void validateRequest(ContentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Content request cannot be null");
        }
        if (request.getFormat() == null || request.getFormat().isEmpty()) {
            throw new IllegalArgumentException("Content format must be specified");
        }
        if (request.getTargetAudience() == null || request.getTargetAudience().isEmpty()) {
            throw new IllegalArgumentException("Target audience must be specified");
        }
        if (request.getTone() == null || request.getTone().isEmpty()) {
            throw new IllegalArgumentException("Tone must be specified");
        }
        if (request.getLength() == null || request.getLength().isEmpty()) {
            throw new IllegalArgumentException("Content length must be specified");
        }
    }

    private String buildPrompt(ContentRequest request) {
        // Escape special characters in the prompt to prevent JSON injection
        return String.format("Generate a %s %s for a %s audience in %s tone.",
                escapeJsonString(request.getLength()),
                escapeJsonString(request.getFormat()),
                escapeJsonString(request.getTargetAudience()),
                escapeJsonString(request.getTone()));
    }

    private String escapeJsonString(String input) {
        return input.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String fetchFromOpenAI(String prompt) throws IOException {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("OpenAI API key is not configured.");
        }

        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = String.format(
                "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"max_tokens\": 150}",
                prompt
        );

        RequestBody body = RequestBody.create(mediaType, jsonBody);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (!response.isSuccessful()) {
                throw new IOException("OpenAI API Error: " + responseBody);
            }

            // Parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode choices = root.path("choices");

            if (choices.isArray() && choices.size() > 0) {
                String content = choices.get(0).path("message").path("content").asText();
                return content.trim(); // Trim to remove any leading/trailing whitespace
            }

            throw new IOException("No content found in OpenAI response");
        }
    }
}
