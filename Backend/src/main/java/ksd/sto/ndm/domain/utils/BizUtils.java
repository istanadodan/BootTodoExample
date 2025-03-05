package ksd.sto.ndm.domain.utils;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
public class BizUtils {
    public static String generateJwt(String privateKeyPath, String issuer, String subject,
            int expirationMinutes) throws Exception {
        // 개인 키 파일 읽기 byte[]
        String privateKeyContent = Files
            .readString(Paths.get(privateKeyPath))
            .replace("-----BEGIN RSA PRIVATE KEY-----", "")
            .replace("-----END RSA PRIVATE KEY-----", "")
            .replaceAll("\\s", "");

        Key key = Keys.hmacShaKeyFor(privateKeyContent.getBytes(StandardCharsets.UTF_8));

        // JWT 생성
        Date now = new Date();
        Date expiration = new Date(now.getTime() + (expirationMinutes * 60 * 1000));
        return Jwts
            .builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(now)
            .expiration(expiration)
            .signWith(key)
            .compact();
    }
}