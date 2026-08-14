package com.arthur_pereira.mind_cracker_server_api.mapper;

import com.arthur_pereira.mind_cracker_server_api.data.common.Pair;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Converter
public class MatchPlayerQueueMapper implements AttributeConverter<Map<Long, Pair<Long, Long>>, String> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<Long, Pair<Long, Long>> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, Pair<Long, Long>> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(
                    dbData,
                    new TypeReference<Map<Long, Pair<Long, Long>>>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}