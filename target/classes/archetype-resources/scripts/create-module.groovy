#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
${symbol_pound}!/usr/bin/env groovy
/**
 * Script: create-module.groovy <br>
 * Autor: Alexandre José da Rocha (modelo gerado)
 * 
 * <p>Função: <br>
 *   Criar automaticamente um novo módulo seguindo os padrões <br>
 *   Clean Architecture definidos no JSBA.</p>
 *
 * <p>Como usar: <br>
 *   groovy create-module.groovy {module-name]</p>
 * 
 * <p>Exemplo: <br>
 *   groovy create-module.groovy cliente<p>
 */

if (args.length == 0) {
    println "Uso correto: groovy create-module.groovy <nome-do-modulo>"
    System.exit(1)
}

def moduleName = args[0].toLowerCase()

println "${symbol_escape}n[JSBA] Criando módulo: ${symbol_dollar}{moduleName} ...${symbol_escape}n"

// Localiza pom.xml
def pom = new File("pom.xml")
if (!pom.exists()) {
    println "[ERRO] pom.xml não encontrado no diretório atual!"
    System.exit(1)
}

// Extrai o package base (groupId)
def groupId = pom.text.find("<groupId>(.+?)</groupId>") { full, g -> g }
def basePackage = groupId.replace('.', '/')

def moduleBaseDir = "src/main/java/${symbol_dollar}{basePackage}/application/modules/${symbol_dollar}{moduleName}"

// Lista de pacotes que devem ser criados
def structure = [
    "api/dto/request",
    "api/dto/response",
    "api/mapper",
    "api/messaging",
    "api/rest",
    "api/rest/assembler",

    "application/event",
    "application/mapper",
    "application/query",
    "application/query/filter",
    "application/query/output",
    "application/query/repository",
    "application/query/spec",
    "application/service",
    "application/usecase",
    "application/usecase/usecase1/input",
    "application/usecase/usecase1/output",

    "domain/model",
    "domain/repository",

    "infrastructure/entity",
    "infrastructure/mapper",
    "infrastructure/projection",
    "infrastructure/repository",
    "infrastructure/repository/impl",
    "infrastructure/service",
    "infrastructure/service/impl"
]

// Cria diretórios
structure.each { path ->
    def dir = new File("${symbol_dollar}{moduleBaseDir}/${symbol_dollar}{path}")
    if (!dir.exists()) {
        dir.mkdirs()
        println " [+] Criado: ${symbol_dollar}{dir}"
    }
}

// Criar classe básica Application.java
def pkg = "${symbol_dollar}{groupId}.application.modules.${symbol_dollar}{moduleName}"

def readmeContent = """${symbol_escape}
package ${symbol_dollar}{pkg}.application.service;

/**
 * Serviço de exemplo criado automaticamente pelo JSBA.
 */
public class ${symbol_dollar}{moduleName.capitalize()}Service {
    
}
"""

def serviceFile = new File("${symbol_dollar}{moduleBaseDir}/application/service/${symbol_dollar}{moduleName.capitalize()}Service.java")
serviceFile.text = readmeContent

println "${symbol_escape}n[JSBA] Módulo '${symbol_dollar}{moduleName}' criado com sucesso!"
println "[JSBA] Estrutura gerada em: ${symbol_dollar}{moduleBaseDir}${symbol_escape}n"
