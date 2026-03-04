#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.util;

import java.util.regex.Pattern;

/**
 * Classe: Regex<br>
 * Pacote: ${package}.application.core.util
 *
 * <p>Classe responsável por fornecer as validações por REGEX utilizadas pela aplicação.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public class Regex {
	private Regex() {
        throw new UnsupportedOperationException(Const.UTILITY_CLASS_CANNOT_BE_INSTANTIATED);
    }
	
	// Regex para email
    public static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+${symbol_dollar}");

    // Regex para telefone simples (com ou sem DDD, aceita 8 ou 9 dígitos)
    public static final Pattern TELEFONE = Pattern.compile("^(${symbol_escape}${symbol_escape}+${symbol_escape}${symbol_escape}d{1,3})?${symbol_escape}${symbol_escape}s?${symbol_escape}${symbol_escape}(?${symbol_escape}${symbol_escape}d{2}${symbol_escape}${symbol_escape})?${symbol_escape}${symbol_escape}s?${symbol_escape}${symbol_escape}d{4,5}${symbol_escape}${symbol_escape}-?${symbol_escape}${symbol_escape}d{4}${symbol_dollar}");

    // Regex para CPF (somente formato, não valida dígitos verificadores)
    public static final Pattern CPF = Pattern.compile("^${symbol_escape}${symbol_escape}d{3}${symbol_escape}${symbol_escape}.?${symbol_escape}${symbol_escape}d{3}${symbol_escape}${symbol_escape}.?${symbol_escape}${symbol_escape}d{3}-?${symbol_escape}${symbol_escape}d{2}${symbol_dollar}");

    // Regex para CNPJ (somente formato, não valida dígitos verificadores)
    public static final Pattern CNPJ = Pattern.compile("^${symbol_escape}${symbol_escape}d{2}${symbol_escape}${symbol_escape}.?${symbol_escape}${symbol_escape}d{3}${symbol_escape}${symbol_escape}.?${symbol_escape}${symbol_escape}d{3}/?${symbol_escape}${symbol_escape}d{4}-?${symbol_escape}${symbol_escape}d{2}${symbol_dollar}");
}
