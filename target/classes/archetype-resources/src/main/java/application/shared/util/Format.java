#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.util;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;

/**
 * Classe: Format<br>
 * Pacote: ${package}.application.core.util
 *
 * <p>Classe responsável por fornecer a formatação de textos e de máscaras utilizadas pela aplicação.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public class Format {
	private Format() {
        throw new UnsupportedOperationException(Const.UTILITY_CLASS_CANNOT_BE_INSTANTIATED);
    }
	
	public static final String FORMATO_CPF = "${symbol_pound}${symbol_pound}${symbol_pound}.${symbol_pound}${symbol_pound}${symbol_pound}.${symbol_pound}${symbol_pound}${symbol_pound}-${symbol_pound}${symbol_pound}";
    public static final String FORMATO_CNPJ = "${symbol_pound}${symbol_pound}.${symbol_pound}${symbol_pound}${symbol_pound}.${symbol_pound}${symbol_pound}${symbol_pound}/${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}-${symbol_pound}${symbol_pound}";
    
    /**
     * Retorna o tipo de dado informado: EMAIL, TELEFONE, CPF, CNPJ ou DESCONHECIDO
     */
    public static String identificarTipo(String valor) {
        if (valor == null || valor.isBlank()) {
            return Const.UNKNOWN;
        }

        valor = valor.trim();

        if (Regex.EMAIL.matcher(valor).matches()) {
            return "EMAIL";
        } 
        else if (Regex.TELEFONE.matcher(valor).matches()) {
            return "TELEFONE";
        } 
        else if (Regex.CPF.matcher(valor).matches()) {
            return "CPF";
        } 
        else if (Regex.CNPJ.matcher(valor).matches()) {
            return "CNPJ";
        }

        return Const.UNKNOWN;
    }
    
    public static String formatarData(LocalDateTime data, String... formato) {
        String pattern = formato.length > 0 ? formato[0] : "dd/MM/yyyy HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        
        if (Objects.isNull(data)) {
            return null;
        }
        
        return data.format(formatter);
    }

    public static String formatarCpfCnpj(String cpfCnpj) {
        String retorno = cpfCnpj.replaceAll("${symbol_escape}${symbol_escape}D", "");
        
        if (retorno.isEmpty()) {
            return null;
        }

        String mascara = "";
        
        if (retorno.length() <= 11) {
            mascara = FORMATO_CPF;
        } 
        else if (retorno.length() <= 14) {
            mascara = FORMATO_CNPJ;
        }

        try {
            retorno = StringUtils.leftPad(retorno, StringUtils.countMatches(mascara, "${symbol_pound}"), "0");
            MaskFormatter mask = new MaskFormatter(mascara);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(retorno);
        } 
        catch (ParseException e) {
            return null;
        }
    }

    public static String removerCaracteresEspeciais(String texto) {
        return texto.replaceAll("[^a-zA-Z0-9]", "");
    }
}
