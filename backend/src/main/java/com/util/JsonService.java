package com.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonService {
    private JsonService() {}

    public static JsonNode toJsonNode(String s)throws IOException {
        ObjectMapper om = new ObjectMapper();
        JsonNode jsonNode = om.readTree(s);
        om = null;
        return jsonNode;
    }

    public static JsonNode toJsonNode(Object object){
        ObjectMapper om = new ObjectMapper();
        JsonNode jsonNode = om.convertValue(object, JsonNode.class);
        om=null;
        return jsonNode;
    }
}
