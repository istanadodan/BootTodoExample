package ksd.sto.ndm.domain.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class BizUtils {
    public static String generateJwt(String privateKeyPath, String user, String email,
            int expirationMinutes) {

        PrivateKey publicKey = null;
        try (Reader reader = new FileReader(privateKeyPath);
                PEMParser pemParser = new PEMParser(reader)) {

            Object obj = pemParser.readObject();
            byte[] decodedKey = ((PEMKeyPair) obj).getPrivateKeyInfo().getEncoded();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            // PKCS8 형식의 개인 키 스펙 생성
            publicKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));

            // JWT 생성
            Date now = new Date();
            Date expiration = new Date(now.getTime() + (expirationMinutes * 60 * 1000));
            return Jwts
                .builder()
                .subject(email)
                .claim("user", user)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(publicKey)
                .compact();
        } catch (FileNotFoundException e) {
            log.error("private-key를 불러올 수 없습니다");
        } catch (IOException e) {
        } catch (NoSuchAlgorithmException e) {
            log.error("RSA 키생성 알고리즘이 존재하지 않습니다");
        } catch (InvalidKeySpecException e) {
            log.error("개인키 생성에 실패했습니다");
        }
        return null;
    }
}