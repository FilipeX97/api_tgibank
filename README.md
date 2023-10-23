# TGI Bank
### API para simulação simples de banco

O TGI Bank é uma aplicação de demonstração que simula operações bancárias, como criar empresas, clientes, realizar saques e depósitos. O projeto é construído usando Spring Boot e Java.

## Requisições da API
O TGI Bank oferece um conjunto de endpoints que permitem realizar operações bancárias simuladas. Abaixo estão as principais requisições disponíveis na API:

### Criar Empresa
URL: POST /tgibank/empresa   
Descrição: Cria uma nova empresa com base nos dados fornecidos.   
Exemplo de Corpo da Requisição:   

`{
  "cnpj": "12345678901234",
  "nome": "Minha Empresa",
  "saldo": 100000.00,
  "taxaSistema": 2.5
}`

### Criar Cliente
URL: POST /tgibank/cliente   
Descrição: Cria um novo cliente associado a uma empresa existente.   
Exemplo de Corpo da Requisição:   

`{
  "cpf": "12345678901",
  "nome": "João Silva",
  "empresa": 1,
  "email": "joao@email.com",
  "telefone": "(11) 1234-5678"
}`

### Realizar Saque
URL: POST /tgibank/saque   
Descrição: Simula um saque na conta do cliente. A empresa é notificada.   
Exemplo de Corpo da Requisição:   

`{
  "idCliente": 1,
  "valor": 500.00
}`

### Realizar Depósito
URL: POST /tgibank/deposito   
Descrição: Simula um depósito na conta do cliente. A empresa é notificada.   
Exemplo de Corpo da Requisição:   

`{
  "idCliente": 1,
  "valor": 1000.00
}`
