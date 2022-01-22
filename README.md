# Desafio Da Digital Innovation One & Cognizant
Sistema de gerenciamento de pessoas em API Rest

---

## Neste sistema de gerenciamento de pessoas será possivel:
Ou através do programa Postmand realizar os operações utilizando do 
arquivo *postman-collection.json* com as Coleções de *urls*, no próprio 
navegador, e até mesmo utilizando o endereço do 
*[heroku](https://www.heroku.com/)*.

### Para exceutar o projeto no terminal:
```
    mvn spring-boot:run
```

### Pré-requisitos para a execução do projeto:
* Java 11 / posterior
* Meven 3.8.2 / superior
* Intellj IDEA Community Edition
* Controle Versionamento GIT
* Conta no GitHub

### Cadastrar uma pessoa:
```
    Método POST:
    http://localhost:8080/v1/persons
```

### Encontrar pessoa usando seu ID (no caso -> 1):
```
    Método GET:
    http://localhost:8080/v1/persons/1
```

### Encontrar todas as pessoas cadastradas:
```
    Método GET:
    http://localhost:8080/v1/persons/
```

### Atualizar dado de pessoa usando seu ID (no caso -> 1):
```
    Método PUT:
    http://localhost:8080/v1/persons/1
```

### Deletar uma pessoa usando seu ID (no caso -> 1):
```
    Método DELETE
    http://localhost:8080/v1/persons/1
```