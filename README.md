> Atualizado pela última vez em 17/10

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

### Funcionamento
Referência à entidade Funcionamento no banco de dados com o único obejtivo de registrar os dias e horários de funcionamento da oficina. Será utilizado para que o sistema barre sozinho agendamentos em dias e/ou horários em que a oficina não está operando

- [X]  cadastrarFuncionamento();
- [X]  listarFuncionamento();
- [ ]  editarFuncionamento();
- [ ]  apagarFuncionamento();
- [X]  Getters e Setters;

### Estoque
Referência à entidade Estoque no banco de dados, seu intuito é mostrar os valores somados das entidades Peça e Lote, que irá armazenar informações das garras de óleo comercializados e utilizados na oficina

- [X]  listarProdutos();
- Métodos de Apoio:
    - [X]  contadorPecas()
    - [X]  contadorOleo()  
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