# Authorizer

### Sobre

- Princípios SOLID: pq? Melhor divisão de responsabilidades e redução de boilerplate code
- Clean Arch: pq? Melhor divisão de responsabilidades entre camadas
- Hashset para buscas de transações: pq? O(1)

### Compilação
Como o projeto está em Kotlin geraremos um binário `.jar` para a execução multiplataforma.

Para gerar o binário, caminhe via linha de comando até a pasta do projeto.

Dado que o terminal está na raíz do projeto, execute o comando `./gradlew jar`
e o binário compilado ficará disponível em: `build/libs/authorizer.jar`

### Execução


### Bibliotecas externas

- JUnit: pq? para fazer testes
- Mockito: pq? para fazer mocks e facilitar a criação de caso de testes
- Jacoco: pq? para facilitar a geração e visualização de reports de cobertura
- Koin: pq? injeção de dependências
- Kotlinx Json: pq? para tratar request e response JSON
- Kotlinx Datetime: pq? para tratar tempo de maneira mais fácil com integração do Kotlinx JSON
