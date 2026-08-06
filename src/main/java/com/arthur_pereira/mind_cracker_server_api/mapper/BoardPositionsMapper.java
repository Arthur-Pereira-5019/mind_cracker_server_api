package com.arthur_pereira.mind_cracker_server_api.mapper;

import com.arthur_pereira.mind_cracker_server_api.data.board.BoardPositionType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Converter
public class BoardPositionsMapper implements AttributeConverter<Map<Integer, BoardPositionType>, String> {
        private static final ObjectMapper mapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(Map<Integer, BoardPositionType> attribute) {
            try {
                return mapper.writeValueAsString(attribute);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Map<Integer, BoardPositionType> convertToEntityAttribute(String dbData) {
            try {
                return mapper.readValue(
                        dbData,
                        new TypeReference<Map<Integer, BoardPositionType>>() {}
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

}
