package dev.marcelomarinho.petapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class PetApiTestingUtils {

    public static <T> T fromJson(String json, TypeReference<T> type) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, type);
    }

}
