#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import ${package}.security.common.service.HashingService;
import lombok.RequiredArgsConstructor;

/**
 * Classe: JwtBCryptHashingServiceImpl <br>
 * Pacote: ${package}.security.jwt.core.infrastructure.service.impl
 * 
 * <p>Classe responsável por implementar o serviço de hash e match para criptografica de senhas. <br>
 * Utiliza o algoritmo BCrypt. <br>
 * Implementa a interface {@link HashingService}</p>
 * 
 * <p>Referências: <br>
 * <a>https://bcrypt-generator.com</a><br>
 * <a>https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/password-encoder.html${symbol_pound}page-title</a></p>
 * 
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
@RequiredArgsConstructor
public class JwtBCryptHashingServiceImpl implements HashingService {
	private final BCryptPasswordEncoder encoder;
	
	@Override
	public String hashSha256Hex(String value) {
		return DigestUtils.sha256Hex(value);
	}
	
	@Override
	public String hash(String rawValue) {
		// Returns the string hash.
        return encoder.encode(rawValue);
	}

	@Override
	public Boolean matches(String rawValue, String hashedValue) {
		// Compares the hashed string to the entered string.
		return encoder.matches(rawValue, hashedValue);
	}
}
