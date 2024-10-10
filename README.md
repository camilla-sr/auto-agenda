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
