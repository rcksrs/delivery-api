# Executando o projeto

Com o docker instalado, execute o comando abaixo:

    docker-compose up

Caso realize o build do projeto manualmente, será necessário alterar a variável de ambiente **DATABASE_POSTGRES_URL** para conexão com o Postgres.
Por padrão, o usuário e senha de acesso ao banco é **admin** e o schema é **public**.

# Documentação

A documentação da API está disponível através do [Swagger UI](http://localhost:8080/api/swagger-ui/index.html). Para acessar os endpoints é necessário autenticação por meio de email e senha cadastrados.

## Criar usuário e obter token de acesso

Para obter o token de acesso é necessário estar cadastrado. É possível também, utilizar o usuário **admin** criado previamente através do email **admin@email.com** e da senha **password**.

> Informações adicionais sobre os endpoists estão disponíveis na documentação do [Swagger](http://localhost:8080/api/swagger-ui/index.html).

**Cadastrar novo usuário**:

    POST api/v1/user

**Obter token de acesso**:

    POST api/v1/auth/token

## Perfis de acesso

Todo novo usuário é criado com perfil de acesso **USER**, podendo ser alterado através do endpoint abaixo:

    PATCH api/v1/user/{id}/role
É possível também que o usuário seja criado sem perfil de acesso através do endpoint abaixo, neste caso ele não será um usuário ativo e seu perfil de acesso estará como **NO_ACCOUNT**.

    POST api/v1/order/no-account

Abaixo está a lista de todos os endpoints do sistema e com quais perfis de acesso é permitido acessá-los.

**Lista de endpoints públicos**:
- `POST api/v1/user`
- `POST api/v1/auth/token`

**Lista de endpoints disponíveis para o perfil de acesso USER**:
- todos os endpoints públicos
- `POST api/v1/order`

**Lista de endpoints disponíveis para o perfil de acesso MANAGER**:
- todos do perfil USER
- `api/v1/store/**`
- `api/v1/order/**`
- `api/v1/delivery/**`

**Lista de endpoints disponíveis para o perfil de acesso ADMIN**:
- todos do perfil MANAGER
- `api/v1/user/**`