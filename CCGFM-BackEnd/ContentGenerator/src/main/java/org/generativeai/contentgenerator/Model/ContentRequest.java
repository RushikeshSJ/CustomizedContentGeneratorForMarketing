package org.generativeai.contentgenerator.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "content_requests")
public class ContentRequest {
    @Id
    private String id;
    private String tone;
    private String targetAudience;
    private String format;
    private String length;
    private String generatedContent;
}
