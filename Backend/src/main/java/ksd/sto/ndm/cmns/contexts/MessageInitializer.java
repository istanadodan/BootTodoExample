package ksd.sto.ndm.cmns.contexts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageInitializer implements CommandLineRunner {
    @Autowired
    private MessageStorage messageStorage;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        String sql = "SELECT code, message FROM tb_message";
        jdbcTemplate.query(sql, (rs, rowNum) -> {
            String code = rs.getString("code");
            String message = rs.getString("message");
            messageStorage.addMessage(code, message);
            return null;
        });
    }
}

