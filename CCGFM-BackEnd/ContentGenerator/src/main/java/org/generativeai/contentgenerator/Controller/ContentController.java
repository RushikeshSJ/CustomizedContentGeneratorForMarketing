package org.generativeai.contentgenerator.Controller;

import org.generativeai.contentgenerator.Model.ContentRequest;
import org.generativeai.contentgenerator.Service.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/content")
public class ContentController {
    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping("/generate")
    public ResponseEntity<ContentRequest> generateContent(@RequestBody ContentRequest request) {
        ContentRequest generatedRequest = contentService.generateContent(request);
        return ResponseEntity.ok(generatedRequest);
    }
}
