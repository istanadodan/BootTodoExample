package ksd.sto.ndm.domain.service;

public interface JwtGenerateService {
    public String generateToken(String privateKeyPath, String user, String email,
            int expirationMinutes);
}