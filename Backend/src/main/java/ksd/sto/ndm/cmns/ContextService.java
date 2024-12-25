package ksd.sto.ndm.cmns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContextService {
    @Autowired
    private MessageStorage messageStorage;

    public String getMessage(String code) {
        // 메시지 사용 로직
        return messageStorage.getMessage(code);
    }
}

