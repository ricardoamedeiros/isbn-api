# 1. Descrição

O projeto trata-se de uma API Rest que possibilita recuperar objetos ISBN's por meio de diferentes provedores de busca.

Apesar do projeto ser implantado sem nenhum dado, é possível recuperar ISBN normalmente. Ao recuperar o ISBN, de forma transparente em bases externas, o registro é armazenado na base local e quanto mais dados e pesquisas forem feitas o processo de busca fica mais rápido, pois não será necessário consultar bases externas novamente e a recuperação acontecerá no cache.

Vídeo de demostração disponível em: https://youtu.be/K5HymLjfps0


# 2. Arquitetura

Os principais componentes da arquitetura são:

* **Provedores de busca customizáveis** - É possível buscar o ISBN de diversas fontes, seja ela uma API externa, excel, banco de dados ou qualquer outra fonte. Basta apenas criar uma classe/jar que contenha um método recuperarIsbn que recebe um código ISBN e retorna uma objeto IsbnDTO. Chamamos essa classe/jar de provedor de busca. É possível criar e adicionar diversos provedores sem necessidade de parar o serviço.

* **Interface para criar provedores** - A interface **Connector** é a interface obrigatória que deve ser implementada por todo provedor de busca.

* **Banco de dados** - É possível utilizar qualquer banco de dados relacional, como: Postgres, Oracle ou Mysql. Basta adicionar as credenciais como variável de ambiente de docker. O banco é utilizado para manter os dados de ISBN recuperado pelos provedores de busca ou por inserção na interface administrativa da solução.

* **API ISBN** - Na API está o algoritmo que prioriza a recuperação do ISBN nos provedores de busca, caso o ISBN não esteja salvo na base da solução. Além disso, possui todos os métodos para manter os provedores de busca e os metadados do ISBN.

* **Interface WEB** -  Interface administrativa utilizada para manter o ISBN e adicionar valores que não foram retornados pelos provedores. Além disso, é possível declarar novos provedores de busca e determinar a prioridade dos provedores.



Diagrama de componentes da arquitetura:

![arquitetura](https://github.com/ricardoamedeiros/isbn-api/blob/master/ISBN.png)

# 3. Formatos

A API disponibiliza tanto JSON quanto XML. Exemplos:

JSON:
```shell
curl -X GET "http://localhost:8083/v1/isbns/9788566250299" -H "accept: application/json":
```
Retorno:
```javascript
{
  "codigoIsbn": "9788566250299",
  "titulo": "Começando com o linux: comando, serviços e administração",
  "ano": 2013,
  "paginas": 135,
  "tiragem": null,
  "reedicao": null,
  "reempressao": null,
  "meioDivulgacao": null,
  "url": null,
  "idioma": null,
  "pais": null,
  "naturezaObra": null,
  "naturezaConteudo": null,
  "tipoContribuicao": null,
  "paginasContribuicaoObra": null,
  "tipoEditora": null,
  "nomeEditora": "Casa do Código",
  "financiamento": null,
  "nomeFinanciador": null,
  "conselhoEditorial": null,
  "distribuicaoAcesso": null,
  "informacaoAutores": null,
  "revisaoPorPartes": null,
  "indiceRemessivo": null,
  "premiacao": null,
  "instituicaoPremiacao": null,
  "dataPremiacao": null,
  "indicacaoObraReferencia": null,
  "nomeInstituicao": null,
  "dataIndicacao": null,
  "observacao": "Essa informação foi definida pelo provedor genérico",
  "cidadeEditora": null
}
```
XML:
```shell
curl -X GET "http://localhost:8083/v1/isbns/9788566250299" -H "accept: application/xml":
```
Retorno:
```xml
<IsbnDTO>
  <codigoIsbn>9788566250299</codigoIsbn>
  <titulo>Começando com o linux: comando, serviços e administração</titulo>
  <ano>2013</ano>
  <paginas>135</paginas>
  <tiragem/>
  <reedicao/>
  <reempressao/>
  <meioDivulgacao/>
  <url/>
  <idioma/>
  <pais/>
  <naturezaObra/>
  <naturezaConteudo/>
  <tipoContribuicao/>
  <paginasContribuicaoObra/>
  <tipoEditora/>
  <nomeEditora>Casa do Código</nomeEditora>
  <financiamento/>
  <nomeFinanciador/>
  <conselhoEditorial/>
  <distribuicaoAcesso/>
  <informacaoAutores/>
  <revisaoPorPartes/>
  <indiceRemessivo/>
  <premiacao/>
  <instituicaoPremiacao/>
  <dataPremiacao/>
  <indicacaoObraReferencia/>
  <nomeInstituicao/>
  <dataIndicacao/>
  <observacao>Essa informação foi definida pelo provedor genérico</observacao>
  <cidadeEditora/>
</IsbnDTO>
```

# 4. Roadmap de implantação

Nesse tópico são apresentados os passos para gerar a versão e executar a aplicação em ambiente docker. É possível executar apenas o  passo 4.2.

## 4.1. Gerar versão

Execute os seguintes comandos na pasta raiz do projeto para gerar o jar/docker:
```shell
mvn clean package
docker built -t api-isbn:1 . #Esse comando é necessário apenas no projeto isbn-api
```

## 4.2. Execução

Para acessar o projeto basta executar o seguinte comando em uma máquina que esteja com o docker instalado
```shell
docker run -p 8083:8083 ricardoabreum/isbn-api:6
```

# 5. API e Interface Administrativa

Após executar a API é possível acessar a documentação no formato SWAGGER disponível no endereço http://localhost:8083/swagger-ui.html.

Na documentação está disponível todos os métodos da API, além da possiblidade de utilizar os métodos na própria página.

Para acessar a interface administrativa digite: http://localhost:8083/

# 6. Principais Tecnologias e frameworks

* JAVA
* Spring Boot
* Swagger
* H2 - Utilizado apenas para facilitar a implantanção, pode ser utilizado postgresql ou qualquer outro banco de dados relacional.
* Angular 7

# 7. Alta disponibilidade

O projeto foi desenhado para trabalhar como microserviço e pode ser adicionadas diversas instâncias sem causar nenhum problema de integridade ou concorrência. É recomendado utilizar o Kubernetes ou Swarm para usar mais de uma instância.

# 8. Demais projetos

Link dos projetos que compõe a solução:

Inteface Administrativa:
https://github.com/ricardoamedeiros/isbn-frontend

Interface necessária para criar os provedores de busca:
https://github.com/ricardoamedeiros/iconnect

Provedor de busca principal:
https://github.com/ricardoamedeiros/biblioteca-nacional-connector

Provedor de busca genérico:
https://github.com/ricardoamedeiros/provedor-generico

# 9. Fonte externa

A fonte principal é a Agência Brasileira de ISBN disponível no link: http://www.isbn.bn.br/website/

# 10. Informações gerais

Não foi utilizada nenhuma solução proprietária ou que infrinja direito autoral ou outras restrições de
uso. 
