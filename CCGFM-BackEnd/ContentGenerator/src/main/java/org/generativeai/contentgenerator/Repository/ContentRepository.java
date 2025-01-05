package org.generativeai.contentgenerator.Repository;

import org.generativeai.contentgenerator.Model.ContentRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContentRepository extends MongoRepository<ContentRequest, String> {

}
