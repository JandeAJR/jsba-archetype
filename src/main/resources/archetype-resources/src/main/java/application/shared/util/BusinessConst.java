#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.util;

/**
 * Classe: Const<br>
 * Pacote: ${package}.application.core.util
 *
 * <p>Classe responsável por fornecer as contantes para as de regras de negócio utilizadas pela aplicação.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public class BusinessConst {
	private BusinessConst() {
		throw new UnsupportedOperationException(Const.UTILITY_CLASS_CANNOT_BE_INSTANTIATED);
	}
	
	// Constantes para as exception das regras de negócio da aplicação
	public static final String RESOURCE_NOT_FOUND_ERROR= "Recurso não encontrado";
	public static final String RESOURCE_NOT_FOUND_MESSAGE = "O Recurso da aplicação não foi encontrado.";
	
	public static final String RESOURCE_ALREADY_EXISTS_ERROR = "Recurso já existe";
	public static final String RESOURCE_ALREADY_EXISTS_MESSAGE = "Este recurso já está cadastrado.";
		
	public static final String ROLE_NOT_FOUND_MESSAGE = "Role não encontrada. Verifique os dados da role e tente novamente.";
	
	public static final String USER_NOT_FOUND_MESSAGE = "Usuário não encontrado. Verifique os dados do usuário e tente novamente.";
	
	// Constantes de mensagens padrão o módulo base domínio roles
	public static final String ROLE_ALREADY_EXISTS = "Role já existe.";
	public static final String ROLE_NOT_FOUND = "Role não encontrada.";
	public static final String ROLE_CREATED = "Role cadastrada";
	public static final String ROLE_UPDATED = "Role atualizada";
	public static final String ROLE_DELETED = "Role excluída";
	public static final String ROLES_LIST = "Lista de roles";
	public static final String ROLE_BY_ID = "Consultar role pelo Id";
	public static final String ROLE_BY_NAME = "Consultar role pelo nome";
		
	// Constantes de mensagens padrão o módulo base domínio usuários
	public static final String USER_ALREADY_EXISTS = "Usuário já existe.";
	public static final String USER_NOT_FOUND = "Usuário não encontrado";	
	public static final String USER_CREATED = "Usuário cadastrado";
	public static final String USER_UPDATED = "Usuário atualizado";
	public static final String USER_DELETED = "Usuário excluído";
	public static final String USERS_LIST = "Lista de usuários";
	public static final String USER_BY_ID = "Consultar usuário pelo Id";
	public static final String USER_BY_USER_NAME = "Consultar usuário pelo UserName";
}
