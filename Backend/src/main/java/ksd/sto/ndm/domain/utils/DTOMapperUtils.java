package ksd.sto.ndm.domain.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DTOMapperUtils {
    public static <T,R> R convertFrom(T src, Class<R> targetClass) {
        ObjectMapper mapper = new ObjectMapper();        
        return mapper.convertValue(src, targetClass);
    }
    
    public static <R> R json2ObjMapper(String jsonString, Class<R> targetClass) {
        ObjectMapper mapper = new ObjectMapper();        
        try {
            return mapper.readValue(jsonString,  targetClass);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
            return null;
        }
    }
}
