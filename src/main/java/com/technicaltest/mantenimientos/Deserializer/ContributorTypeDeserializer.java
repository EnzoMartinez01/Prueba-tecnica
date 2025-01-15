package com.technicaltest.mantenimientos.Deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.technicaltest.mantenimientos.Models.ContributorType;
import com.technicaltest.mantenimientos.Repositories.ContributorTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ContributorTypeDeserializer extends JsonDeserializer<ContributorType> {

    @Autowired
    private ContributorTypeRepository contributorTypeRepository;

    @Override
    public ContributorType deserialize(JsonParser p, DeserializationContext context) throws IOException, JsonProcessingException {
        Integer id = p.getIntValue();
        return contributorTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid ContributorType id: " + id));
    }
}
