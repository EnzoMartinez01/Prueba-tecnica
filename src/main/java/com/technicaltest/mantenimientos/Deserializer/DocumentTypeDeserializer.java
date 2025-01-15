package com.technicaltest.mantenimientos.Deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.technicaltest.mantenimientos.Models.DocumentType;
import com.technicaltest.mantenimientos.Repositories.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DocumentTypeDeserializer extends JsonDeserializer<DocumentType> {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public DocumentType deserialize(JsonParser p, DeserializationContext context) throws IOException, JsonProcessingException {
        Integer id = p.getIntValue();
        return documentTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid DocumentType id: " + id));
    }
}
