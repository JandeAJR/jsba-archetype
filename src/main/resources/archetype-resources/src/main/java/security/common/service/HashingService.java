#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.common.service;

/**
 * Interface: HashingService <br>
 * Pacote: ${package}.security.jwt.core.service
 *
 * <p>Interface responsável por fornecer o contrato para o serviço de hash.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public interface HashingService {
	String hashSha256Hex(String value);
	String hash(String rawValue);
	Boolean matches(String rawValue, String hashedValue);
}
