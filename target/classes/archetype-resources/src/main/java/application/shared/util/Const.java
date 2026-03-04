#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.util;

/**
 * Classe: Const <br>
 * Pacote: ${package}.application.core.util
 *
 * <p>Classe responsável por fornecer as contantes utilizadas pela aplicação.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public class Const {
	private Const() {
		throw new UnsupportedOperationException(Const.UTILITY_CLASS_CANNOT_BE_INSTANTIATED);
	}

	// Mensagens para classes que não podem ser instanciadas
	public static final String UTILITY_CLASS_CANNOT_BE_INSTANTIATED = "This is a utility class and cannot be instantiated";
	public static final String ASSEMBLER_CLASS_CANNOT_BE_INSTANTIATED = "This is a assembler class and cannot be instantiated";	
	public static final String SPECIFICATIONS_CLASS_CANNOT_BE_INSTANTIATED = "This is a specifications class and cannot be instantiated";
	public static final String WRAPPER_CLASS_CANNOT_BE_INSTANTIATED = "This is a wrapper class and cannot be instantiated";
		
	// Constantes da aplicação
	public static final int PAGE_SIZE = 10; // Tamanho máximo permitido para paginação
	public static final String ERROR = "error";
	public static final String SUCCESS = "success";
	public static final String UNKNOWN = "unknown";
	public static final String UNAUTHORIZED = "unauthorized";
	public static final String FORBIDDEN = "forbidden";
	public static final String NOT_CONFIGURED = "not-configured";
	
	// Constantes para símbolos
	public static final String LEFT_ARROWS = ">>>>>>>>>> ";
	public static final String RIGHT_ARROWS = " <<<<<<<<<<";
	
	// Códigos ANSI para cores
	public static final String ANSI_RESET = "${symbol_escape}u001B[0m";
    public static final String ANSI_GREEN = "${symbol_escape}u001B[32m";
    public static final String ANSI_BLUE = "${symbol_escape}u001B[34m";
    public static final String ANSI_CYAN = "${symbol_escape}u001B[36m";
    public static final String ANSI_RED = "${symbol_escape}u001B[31m";    
    public static final String ANSI_YELLOW = "${symbol_escape}u001B[33m";
    public static final String ANSI_PURPLE = "${symbol_escape}u001B[35m";
    public static final String ANSI_WHITE = "${symbol_escape}u001B[37m";
    public static final String ANSI_BOLD = "${symbol_escape}u001B[1m";
	
	// Constantes para o Swagger Security Bearer Scheme
	public static final String SWAGGER_SECURITY_BEARER_SCHEME = "bearer";
	public static final String SWAGGER_SECURITY_BEARER_SCHEME_NAME = "bearerAuth";
	public static final String SWAGGER_SECURITY_BEARER_SCHEME_FORMAT = "JWT";	
	
	// Constantes para Refresh Token
	public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";
	public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
	
	// Constantes para JWT e OAuth2
	public static final String JWT_IDENTIFIER = "JWT";
	public static final String OAUTH2_IDENTIFIER = "OAUTH2";
	public static final String KEYCLOAK_IDENTIFIER = "KEYCLOAK";
	public static final String OAUTH2_INVALID_AUDIENCE_TOKEN = "Token não contém audience (client_id) válida para esta API";
	
	// Constantes de Roles para o Spring Security (PREFIXO "ROLE_" é adicionado automaticamente pelo Spring Security)
	public static final String SPRING_SECURITY_ROLE_PREFIX = "ROLE_";
	
	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_BASIC_USER = "BASIC_USER"; 
	
	// Nome e descrição das duas rodes default da aplicação
	public static final String ROLE_ADMIN_FULLNAME = "ROLE_ADMIN";
	public static final String ROLE_BASIC_USER_FULLNAME = "ROLE_BASIC_USER";
	
	public static final String ROLE_ADMIN_FULLDESCRIPTION = "Role para os usuários adminstradores da aplicação.";
	public static final String ROLE_BASIC_USER_FULLDESCRIPTION = "Role para usuários básicos básicos da aplicação."; 
	
	// Constantes para as exception da aplicação
	public static final String CONFIGURATION_SECURITY_FILTER_CHAIN_ERROR = "Erro ao configurar o SecurityFilterChain";
	public static final String CONFIGURATION_AUTHENTICATION_MANAGER_ERROR = "Erro ao configurar o AuthenticationManager";
	
	public static final String UNAUTHORIZED_ERROR = "Acesso não autorizado";
	public static final String UNAUTHORIZED_SECURITY_ERROR = "Falha na segurança durante a requisição de acesso";
	public static final String UNAUTHORIZED_REFRESH_TOKEN_ERROR = "Erro de refresh token";
	
	public static final String UNAUTHORIZED_MESSAGE = "Token de autenticação ausente ou inválido.";
	public static final String UNAUTHORIZED_SECURITY_MESSAGE = "Houve alguma falha na segurança durante a requisição de acesso";
	public static final String UNAUTHORIZED_REFRESH_TOKEN_MESSAGE = "Houve um erro ao processar o refresh token";
		
	public static final String FORBIDDEN_ERROR = "Acesso negado";
	public static final String FORBIDDEN_MESSAGE = "Sem permissão para acessar este recurso.";
	
	public static final String INVALID_CREDENTIALS_ERROR = "Credenciais inválidas";
	public static final String INVALID_CREDENTIALS_MESSAGE = "Usuário ou senha incorretos. Verifique as credenciais e tente novamente.";
	
	public static final String USER_NOT_AUTHENTICATED_ERROR = "Usuário não autenticado";
	public static final String USER_NOT_AUTHENTICATED_MESSAGE = "O usuário não está autenticado. Faça login e tente novamente.";
	
	public static final String USER_CONTEXT_NOT_FOUND_ERROR = "Usuário não encontrado no contexto de segurança da aplicação"; // Spring Security Context
	public static final String USER_CONTEXT_NOT_FOUND_MESSAGE = "O usuário especificado não foi encontrado. Verifique o nome de usuário e tente novamente.";
	
	public static final String BAD_REQUEST_ERROR = "Requisição inválida";
	public static final String BAD_REQUEST_INVALID_PARAMETERS_ERROR = "Parâmetros de requisição inválidos";
	public static final String BAD_REQUEST_MISSING_PARAMETERS_ERROR = "Parâmetro obrigatório ausente: ";
	public static final String BAD_REQUEST_JSON_PARSE_ERROR = "Erro ao processar o JSON da requisição";
	public static final String BAD_REQUEST_REFRESH_TOKEN_NOT_FOUND_ERROR = "Refresh Token não localizado";
	
	public static final String BAD_REQUEST_MESSAGE = "A requisição contém dados inválidos. Verifique os dados informados.";
	public static final String BAD_REQUEST_VALIDATION_MESSAGE = "Uma ou mais regras de validação foram violadas. Verifique os dados informados.";
	public static final String BAD_REQUEST_INVALID_PARAMETERS_MESSAGE = "Parâmetros de requisição inválidos. Verifique os dados informados.";
	public static final String BAD_REQUEST_INVALID_ARGUMENT_MESSAGE = "Argumentos inválidos. Verifique os dados informados.";
	public static final String BAD_REQUEST_MISSING_PARAMETERS_MESSAGE = "Verifique os parâmetros da requisição.";
	public static final String BAD_REQUEST_JSON_PARSE_MESSAGE = "Verifique o formato do JSON enviado e tente novamente.";
	public static final String BAD_REQUEST_REFRESH_TOKEN_NOT_FOUND_MESSAGE = "Não foi localizado um Refresh Token no cookie do usuário.";
	
	public static final String HANDLER_NOT_FOUND_MESSAGE = "O recurso solicitado não foi encontrado. Verifique a URL e tente novamente.";
	
	public static final String METHOD_NOT_ALLOWED_ERROR = "Método HTTP não suportado";
	public static final String METHOD_NOT_ALLOWED_MESSAGE = "O método HTTP utilizado não é permitido para este endpoint.";
	
	public static final String CONFLICT_DATABASE_ERROR = "Database error";
	public static final String CONFLICT_DATABASE_MESSAGE = "Ocorreu um erro ao acessar o banco de dados. Verifique os dados e tente novamente.";
	
	public static final String CONFLICT_DATA_INTEGRITY_VIOLATION_ERROR = "Erro por violação de integridade de dados";
	public static final String CONFLICT_DATA_INTEGRITY_VIOLATION_MESSAGE = "Ocorreu um erro de integridade de dados. Verifique os dados enviados e tente novamente.";
	
	public static final String UNSUPPORTED_MEDIA_TYPE_ERROR = "Tipo de mídia não suportado";
	public static final String UNSUPPORTED_MEDIA_TYPE_MESSAGE = "O tipo de mídia do corpo da requisição não é suportado. Verifique o Content-Type e tente novamente.";
	
	public static final String INTERNAL_SERVER_ERROR_SECURITY_CONFIGURATION_ERROR = "Erro na configuração de segurança da aplicação";
	public static final String INTERNAL_SERVER_ERROR_SECURITY_CONFIGURATION_MESSAGE = "Ocorreu um erro na configuração de segurança da aplicação.";
	
	public static final String INTERNAL_SERVER_ERROR_AUTH_CONFIGURATION_ERROR = "Erro na configuração de autenticação da aplicação";
	public static final String INTERNAL_SERVER_ERROR_AUTH_CONFIGURATION_MESSAGE = "Ocorreu um erro na configuração de autenticação da aplicação.";
	
	public static final String INTERNAL_SERVER_ERROR_BIND_VALIDATION_ERROR = "Erro na configuração da aplicação";
	public static final String INTERNAL_SERVER_ERROR_BIND_VALIDATION_MESSAGE = "Ocorreram erros de validação nas propriedades de configuração da aplicação.";
	
	public static final String INTERNAL_SERVER_ERROR = "Erro interno do servidor";
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Ocorreu um erro inesperado. Se o erro persistir entre em contato com o administrador do sistema.";
		
	// Urls constantes da aplicação
	public static final String FRONTEND_HOME_PAGE_URL = "http://localhost:3000/home";
	public static final String FRONTEND_LOGIN_PAGE_URL = "http://localhost:3000/login";
	
	// Constantes de mensagens padrão da aplicação	
	public static final String RESOURCE_NOT_FOUND = "Recurso não encontrado.";	
	public static final String METHOD_NOT_IMPLEMENTED = "Método não implementado.";
	public static final String ENDPOINT_IN_DEVELOPMENT = "Endpoint em desenvolvimento.";
	public static final String APPLICATION_INFO = "Informações acerca da aplicação."; 
	public static final String JWT_AUTHENTICATION_TEST_RUNNING_MESSAGE = "Teste de inicialização do módulo de autenticação interna (JWT) carregado com sucesso.";
	public static final String OAUTH2_AUTHENTICATION_TEST_RUNNING_MESSAGE = "Teste de inicialização do módulo de autenticação externa (OAUTH2) carregado com sucesso.";
	public static final String REFRESH_TOKEN_COOKIE_VALUE_NOT_FOUND = "Ausência de Refresh Token no cookie de usuário.";
	public static final String USER_CONTEXT_NOT_FOUND = "Usuário autenticado não encontrado no contexto de segurança da aplicação.";
	public static final String HIJACKING_SECURITY_FAIL = "Reuse attack detectado (hijacking). Todas as sessões foram encerradas.";
	public static final String INVALID_AUTH_REFRESH = "Refresh inválido.";
	public static final String EXPIRED_AUTH_REFRESH = "Refresh expirado.";
	public static final String INVALID_OPERATOR = "Operador inválido: ";
	public static final String NUMERIC_OR_DATE_INVALID_OPERATOR = "Operador incompatível com campos numéricos ou de data: ";
}
