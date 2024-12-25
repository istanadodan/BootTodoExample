package ksd.sto.ndm.cmns;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class MessageStorage {
    private final Map<String, String> messageMap = new ConcurrentHashMap<>();

    public void addMessage(String code, String message) {
        messageMap.put(code, message);
    }

    public String getMessage(String code) {
        return messageMap.get(code);
    }
}

