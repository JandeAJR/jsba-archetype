#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ${package}.application.bootstrap.BaseBootstrap;
import ${package}.application.shared.util.Const;
import ${package}.security.jwt.core.application.usecase.role.CreateRoleUseCase;
import ${package}.security.jwt.core.application.usecase.role.input.RoleInput;
import ${package}.security.jwt.core.application.usecase.role.output.RoleOutput;
import ${package}.security.jwt.core.application.usecase.user.CreateUserUseCase;
import ${package}.security.jwt.core.application.usecase.user.input.UserInput;
import lombok.RequiredArgsConstructor;

/**
 * Classe: JwtBootstrap <br>
 * Pacote: ${package}.security.jwt.core.bootstrap
 *
 * <p><strong>Profile: test</strong> <br>
 * Executa a aplicação através do profile test (application-test.yml). <br>
 * Utiliza a estratégia de autenticação JWT (security.strategy=JWT no arquivo application-jwt.yml).</p>
 *  
 * <p>Estende {@link BaseBootstrap}</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
@Profile("test")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "security.strategy", havingValue = "JWT", matchIfMissing = false)
public class JwtBootstrap extends BaseBootstrap {
	private final CreateRoleUseCase createRoleUseCase;
	private final CreateUserUseCase createUserUseCase;
	
	@Override
	protected void startRunning() {
		// Cria a role ROLE_ADMIN
    	RoleInput roleAdminInput = new RoleInput(Const.ROLE_ADMIN_FULLNAME, Const.ROLE_ADMIN_FULLDESCRIPTION);
    	RoleOutput roleAdminOutput = createRoleUseCase.execute(roleAdminInput);
    	
    	// Cria a role "ROLE_BASIC_USER"
    	RoleInput roleBasicUserInput = new RoleInput(Const.ROLE_BASIC_USER_FULLNAME, Const.ROLE_BASIC_USER_FULLDESCRIPTION);
    	RoleOutput roleBasicUserOutput = createRoleUseCase.execute(roleBasicUserInput);
    	
    	// Configura a lista de roles para o usuário Admin
    	List<RoleOutput> rolesAdmin = new ArrayList<>();
    	rolesAdmin.addAll(List.of(roleAdminOutput, roleBasicUserOutput));
    	
    	// Configura a lista de roles para o usuário BasicUser
    	List<RoleOutput> rolesBasicUser = new ArrayList<>();
    	rolesBasicUser.add(roleBasicUserOutput);
    	
    	// Cria os usuários admin e basic no repositório de dados
    	// Password generator by https://bcrypt-generator.com
    	// Factor 12 	
    	
    	// Usuário admin
        UserInput userAdminInput = new UserInput(
        		"admin",               // userName
        		"admin@${artifactId}.com.br",   // email
        		"Administrador",       // name
        		"admin",               // password
        		true,                  // accountNonExpired
        		true,                  // accountNonLocked 
        		true,                  // credentialsNonExpired
        		false,                 // emailVerified
        		true,                  // enabled
        		rolesAdmin             // roles
        ); 
        
        // Usuário básico
        UserInput basicUserInput = new UserInput(
        		"basic",                   // userName
        		"basic.user@${artifactId}.com.br",  // email
        		"Basico",                  // name
        		"basic",                   // password
        		true,                      // accountNonExpired
        		true,                      // accountNonLocked 
        		true,                      // credentialsNonExpired
        		false,                     // emailVerified
        		true,                      // enabled
        		rolesBasicUser             // roles
        );
        
        // Executa os casos de uso para cadastrar os usuários (admin e user)
        createUserUseCase.execute(userAdminInput);
        createUserUseCase.execute(basicUserInput);        
	}

    @Override
    public void run(String... args) throws Exception {
    	startRunning();
    	writeLog("test", Const.JWT_IDENTIFIER, Const.JWT_AUTHENTICATION_TEST_RUNNING_MESSAGE, null);
    }
}
