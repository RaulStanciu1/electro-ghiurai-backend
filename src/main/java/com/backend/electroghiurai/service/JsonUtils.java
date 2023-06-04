package com.backend.electroghiurai.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    public static <T> T convertJsonToObject(String jsonString, Class<T> objectClass) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, objectClass);
    }
}
