# Erro: Unable to determine Dialect without JDBC metadata

## 📋 Descrição do Erro

```
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/hibernate/autoconfigure/HibernateJpaConfiguration.class]: Unable to create requested service [org.hibernate.engine.jdbc.env.spi.JdbcEnvironment] due to: Unable to determine Dialect without JDBC metadata (please set 'jakarta.persistence.jdbc.url' for common cases or 'hibernate.dialect' when a custom Dialect implementation must be provided)
```

Este erro ocorre quando o **Hibernate não consegue determinar qual dialect usar** para comunicar com o banco de dados.

---

## 🔍 Causas Comuns

1. **Falta de configuração de URL do banco de dados**
   - A propriedade `spring.datasource.url` não está definida
   - O Hibernate não consegue conectar ao banco para detectar o tipo

2. **Falta do dialect explícito**
   - Quando há problemas de conexão, o dialect deve ser especificado manualmente
   - Isso é especialmente importante em fases iniciais do desenvolvimento

3. **Dependência do driver JDBC não carregada**
   - O driver do banco de dados (ex: PostgreSQL) pode estar com escopo inadequado

4. **Banco de dados indisponível**
   - Se o banco não está rodando, o Hibernate não consegue fazer a detecção automática

---

## ✅ Como Resolver

### Passo 1: Configurar o arquivo `application.properties`

Abra o arquivo: `src/main/resources/application.properties`

Adicione ou ajuste estas propriedades:

```properties
# Configuração da conexão com o banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/proposta_db
spring.datasource.username=postgres
spring.datasource.password=123

# Configuração do Hibernate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

### Passo 2: Entender cada propriedade

| Propriedade | Descrição |
|---|---|
| `spring.datasource.url` | URL de conexão com o banco de dados PostgreSQL |
| `spring.datasource.username` | Usuário do banco de dados |
| `spring.datasource.password` | Senha do banco de dados |
| `spring.jpa.properties.hibernate.dialect` | Define o dialect do Hibernate para PostgreSQL |
| `spring.jpa.hibernate.ddl-auto` | Define como o schema será gerenciado |

### Passo 3: Alterar `ddl-auto` conforme sua necessidade

```properties
# validate - apenas valida que o schema existe (RECOMENDADO para PRODUÇÃO)
spring.jpa.hibernate.ddl-auto=validate

# update - atualiza as tabelas (RECOMENDADO para DESENVOLVIMENTO)
spring.jpa.hibernate.ddl-auto=update

# create - cria novo schema (cuidado: apaga dados existentes!)
spring.jpa.hibernate.ddl-auto=create

# create-drop - cria e remove ao desligar (útil para TESTES)
spring.jpa.hibernate.ddl-auto=create-drop
```

### Passo 4: Verificar se o PostgreSQL está rodando

```bash
# Windows - verifique se o serviço PostgreSQL está ativo
# Linux/Mac - execute:
psql --version
```

### Passo 5: Verificar a dependência do driver

No arquivo `pom.xml`, certifique-se de ter:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## 🎯 Dialects Comuns

Se você usar outro banco de dados, utilize o dialect apropriado:

| Banco de Dados | Dialect |
|---|---|
| PostgreSQL | `org.hibernate.dialect.PostgreSQLDialect` |
| MySQL | `org.hibernate.dialect.MySQL8Dialect` |
| H2 (Testes) | `org.hibernate.dialect.H2Dialect` |
| Oracle | `org.hibernate.dialect.Oracle10gDialect` |
| SQL Server | `org.hibernate.dialect.SQLServer2012Dialect` |

---

## 🛠️ Para Testes (com H2)

Se quiser usar um banco em memória para testes:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
```

E adicione a dependência ao `pom.xml`:

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## 📝 Exemplo Completo (application.properties)

```properties
# Nome da aplicação
spring.application.name=proposta-app

# Configuração PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/proposta_db
spring.datasource.username=postgres
spring.datasource.password=123
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuração JPA/Hibernate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
```

---

## 🚀 Checklist para Resolver

- [ ] Arquivo `application.properties` tem `spring.datasource.url` configurado
- [ ] Arquivo `application.properties` tem `spring.jpa.properties.hibernate.dialect` definido
- [ ] PostgreSQL (ou seu banco) está rodando
- [ ] Credenciais de banco estão corretas (usuário/senha)
- [ ] Driver JDBC está no `pom.xml`
- [ ] Projeto foi recompilado após as mudanças

---

## ❓ Ainda não funciona?

1. Verifique se o banco de dados está realmente rodando
2. Teste a conexão com as credenciais fornecidas
3. Veja o log completo do erro (pode ter mais informações)
4. Considere usar um banco H2 em memória para testes iniciais

---

**Última atualização:** 20/02/2026

