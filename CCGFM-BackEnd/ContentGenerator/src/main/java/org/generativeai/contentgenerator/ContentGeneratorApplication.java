package org.generativeai.contentgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories

public class ContentGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentGeneratorApplication.class, args);
    }

}
