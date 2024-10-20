> Atualizado pela última vez em 18/10

# Classes

### Peças
Referência à entidade Peca no banco de dados que irá armazenar todas as peças e ferramentas da oficina para informatizar o controle de estoque, atualmente feito por memória dos funcionários

- [X]  cadastrarPeca();
- [X]  listarPecas();
- [X]  editarPeca();
- [ ]  apagarPeca();
- [ ]  Métodos de apoio:
    - [X]  validarID();
- [X]  Getters e Setters;

### Funcionários
Referência à entidade Funcionario no banco de dados que irá armazenar somente os nomes dos funcionários para identificá-los como responsáveis nos agendamentos

- [X]  cadastrarFuncionario();
- [X]  listarFuncionario();
- [ ]  editarFuncionario();
- [ ]  apagarFuncionario();
- [ ]  Métodos de apoio:
    - [ ]  validarID();
- [X]  Getters e Setters;

### ~~Funcionamento~~
~~Referência à entidade Funcionamento no banco de dados com o único obejtivo de registrar os dias e horários de funcionamento da oficina. Será utilizado para que o sistema barre sozinho agendamentos em dias e/ou horários em que a oficina não está operando~~

### Estoque
Referência à entidade Estoque no banco de dados, seu intuito é mostrar os valores somados das entidades Peça e Lote, que irá armazenar informações das garras de óleo comercializados e utilizados na oficina

- [X]  listarProdutos();
- Métodos de Apoio:
    - [X]  contadorPecas()
    - [X]  contadorOleo()  
- [X]  Getters e Setters;

### Auxiliar Produtos Usados
Referência à entidade auxiliar Auxiliar Produtos Usados, que tem por finalidade indicar os produtos usados no serviço agendado **após a finalizaçao do agendamento no sistema**, por produtos entende-se peças e/ou garrafas de óleo. Essa tabela vai relacionar as Peças e Garrafas de Óleo com o agendamento finalizado indicando as quantidades gastas

- [ ]  registarUsosInsumo();
- [ ]  listarUsos();
- [ ]  editarUso();
- [ ]  apagarUso();
- Métodos de Apoio:
    - [ ]  validarID()
- [ ]  Getters e Setters;

### Cliente
Referência à entidade Cliente no banco de dados, vai ser utilizada para guardar as informações básicas dos clientes que receberão o atendimento na oficina somente para referência dos funcionários, uma vez que os cliente **não terão** acesso ao sistema.

- [ ]  cadastrarCliente();
- [ ]  listarClientes();
- [ ]  editarCliente();
- [ ]  apagarCliente();
- Métodos de Apoio:
    - [ ]  validarID()  
- [X]  Getters e Setters;

### Lote
Referência à entidade Lote no bando de dados, ao contrário do que o nome sugere, será usada para armazenar as informações das garrafas de óleo automotivo. Foi necessária essa criação esses óleos possuem data de validade e finalidades diferentes, além de serem compradas em lotes.Por essa razão, a tabela não possui intenção de identificar as unidades especificamente, esse é o motivo de não haver um ID nem um auto_increment no banco.

- [X]  cadastrarLote();
- [X]  listarLote();
- [X]  editarLote();
- [ ]  apagarLote();
- Métodos de Apoio:
    - [X]  contadorPecas()
    - [X]  contadorOleo()  
- [X]  Getters e Setters;


### Tipo de Serviço
Referência à entidade Tipo Serviço no banco de dados, será utilizada para referenciar qual o serviço agendado para um determinado cliente, descreverá como funciona o serviço somente. Não haverá vínculo de valores, pois seria uma informação inútil para o sistema como ele está hoje, uma vez que não há controle de OS (Ordem de Serviço)

- [X]  cadastrarTipoServico();
- [X]  listarTiposServico();
- [ ]  editarTipoServico();
- [ ]  apagarTipoServico();
- Métodos de Apoio:
    - [ ]  validarID() 
- [X]  Getters e Setters;

---

### Linha Evolutiva do código
- Inicialmente, a conexão com o banco e envio de dados seria feita a partir das classes principais e assim estava estruturado. Na última reunião do grupo, houveram mudanças discutidas a serem implementadas seguindo os novos diagramas do repositório oficial do projeto, obtido através de

```
    git clone https://github.com/senac-sp-cas-sa/Equipe_A-Auto_Agenda.git
```

- Decidi adaptar as classes já desenvolvidas para serem as DAOs de nossas principais, trocando de lugar no caso da função, ou seja
    - [1] as classes já codadas serão a camada mais profunda que trata da conexão com o banco de dados propriamente dito
    - [2] as novas classes principais funcionarão como um filtro para o que for digitado de forma que a main fique limpa em questão de validações

---

### Histórico de alterações

- A ideia de trabalhar com logs automáticos para **todas** as operações do sistema foi descartada
    - Razão: conceitos e algoritmos complicados demais para o nível geral do grupo e por segurança de todo o projeto

- Uma tabela para armazenar as informações do Cliente foi criada (ninguém se lembrou disso antes) isso envolveu implementações no código
    - Razão: cobrança do professor orientador do PI

- A forma como o sistema conversa com o estoque foi melhor definida do que antes, o tratamento a partir de dados do banco foi readaptada
    - Razão: um exercício de banco de dados mostrou ser possível uma dinâmica que não tinhamos conhecimento antes

- Uma nova tabela auxiliar foi implementada para informar os produtos utilizados em um serviço após um agendamento ser concluído para criar uma conexão entre todas as entidades criadas no projeto
    - Razão: alerta vindo pelo professor orientador do projeto, uma vez que, desde o início não era um intuito trabalhar com um algoritmo de gerenciamento de OS

- A entidade e classe para funcionamento, que seria utilizada para comparar os dias da semana no momento do agendamento, foi descartada
    - Razão: é possível validar a informação sem a necessidade de salvar dados no banco, isso será tratado no código agora

- Tinha retirado todos os getters e setters pois não estávamos utilizando adequadamente, mas implementei novamente
    - Razão: obrigatório na documentação, mas eu não tinha visto