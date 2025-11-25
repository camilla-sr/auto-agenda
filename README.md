> ![Atualizado em](https://img.shields.io/github/last-commit/camilla-sr/auto-agenda?label=Atualizado%20em)
```
escrito por HectorSemenssato
```
## :thinking: O que é esse projeto?

Este projeto trata-se de um sistema de oficina mecânica para gerenciamento de agendamentos e estoque que será utilizado pelos colaboradores da oficina. 

O gerenciamento de agendamentos consiste em uma funcionalidade que armazenará em banco de dados informações sobre os agendamentos de serviços realizados pela oficina mecânica contendo tipo de serviço que será feito no veículo, dados do cliente (nome, e-mail e telefone para contato, veículo), observações, data da realização do agendamento, previsão de conclusão e a conclusão. 

Para o gerenciamento de estoque, temos informações dos produtos contendo nome, código, categoria, preço de compra e venda, fornecedor, descrição e a quantidade existente no estoque atual.

O sistema possui 5 páginas de navegação: "Dashboard", "Agendamentos", "Serviços", "Funcionários" (acesso restrito ao(s) administrador(es) do sistema) e "Produtos". Elas ficam disponíveis para acesso via barra de navegação na parte superior de todas as páginas. 

> É obrigatório ter usuário e senha criados para realizar acesso a qualquer funcionalidade existente desse sistema.

## :question: O que há de novo

Na versão 1.5 temos a inserção do Spring Boot com a linguagem HTML (criação de páginas na web), JavaScript (mais interatividade para as páginas), Thymeleaf (dinamismo em páginas e integração back-end e front-end) e CSS (estilização de páginas) sob a frame Bootstrap v5.3.6. É usada a arquitetura de software no padrão MVC (Model, View e Controller) para a separação do projeto em camadas garantindo benefícios como a organização, a facilidade de manutenção e a reutilização de código.

E com a versão 2.0, é trazida a inserção de imagens dos veículos nos agendamentos — garantindo que o cliente e o funcionário tenham registros do veículo antes e depois do serviço —, inclusão dos microserviços e as correções e melhorias da versão anterior.

:scissors: O uso de script de Banco de Dados foi abandonado deste projeto com o uso da dependência "H2 Database".

Para saber mais sobre as versões do projeto consulte as [notas das versões](https://senacspedu-my.sharepoint.com/:w:/g/personal/hector_saraujo_senacsp_edu_br/EYegAuJ9oqRMpgmQJhWFQG8BGQmUio_9-MyUHb5FBYM0LQ?e=KWezNh).

## :mag_right: Detalhes do projeto

O projeto __versão com Spring Boot__ fica localizado na pasta `vSpring` enquanto a __versão para uso no VS Code__ para criação das interfaces fica na pasta `v1.5`.

> A existência da pasta `v1.5` serve para facilitar o acesso as interfaces web do projeto para criar e modificar as funcionalidades ou o design usando o VS Code.





#### :computer: Abrindo pela primeira vez 

Na IDE Eclipse:
```
Importar projeto > Maven > "Existing Maven Projects" > Localizar e selecionar caminho do projeto > Finalize clicando em "Finish" 
```

Na IDE IntelliJ:

```
File > New > "Import Project from Existing Sources..." > Localizar e selecionar caminho do projeto > Escolha "Import project from external model" + "Eclipse" > Siga com "Next" e, por fim, "Create".
```

> **:bulb: Dica de uso com VS Code:**
>
>A importação no VS Code é o mesmo processo como o de qualquer outro arquivo, porém caso queira fazer alterações e vê-las aparecendo em tempo real no navegador (dispensando a necessidade de apertar a tecla F5 a cada alteração), utilize a extensão "Live Server" disponível no próprio editor de código.



#### :arrow_forward: Rodando o projeto 

Para rodar o projeto é necessário que a plataforma de desenvolvimento Eclipse esteja aberta no arquivo principal com nome `AutoagendaApplication.java`, com isso feito, basta executar o arquivo que irá inicializar o Spring Boot e, caso não tenha nenhuma falha durante as validações de arquivos (que será indicado pelo console da plataforma), abra o navegador e na barra de endereços digite `localhost:8080` ou `127.0.0.1:8080` e tecle enter.


> **:round_pushpin: Localização do arquivo:**
> - **AutoagendaApplication.java**:
> vSpring\autoagenda\src\main\java\br\com\autoagenda\autoagenda



## :ledger: Padronização de commits
Para padronizar os commits, o GitHub possui convenções que deixam os logs de mudanças mais fáceis de entender. Segue abaixo as convenções principais para serem utilizadas no projeto:

> - "**feat:** *[mensagem de commit]*": usado para adicionar uma nova funcionalidade.
> - "**chore:** *[mensagem de commit]"*: usado para tarefas de manutenção (por exemplo: atualizar dependências como `pom.xml`, atualizar `.gitignore`, configurar arquivos do GitHub Actions).
> - "**fix:** *[mensagem de commit]*": usado para correção de bugs.
> - "**refactor:** *[mensagem de commit]*": usado para melhorias da estrutura de código mantendo o comportamento externo (mudança de nome de variável, troca de estruturas 'for' e/ou 'if', remoção de código morto, organização de imports ou formatar o código).
>
>
> Commits que (quase ou) não afetam a lógica do desenvolvimento:
>- "**docs:** *[mensagem de commit]*": usado para alterações na documentação.
> - "**add:** *[mensagem de commit]*": usado para adição de novos arquivos no projeto. (commit informal)
> - "**style:** *[mensagem de commit]*": usado para mudanças de formatação (espaços, vírgulas, etc.) e estética (cores de página, localização de elementos e valores em arquivo CSS) que não afetam a lógica.
>
> *Mudanças nos arquivos (limpeza, renomeação ou ajustes de arquivos e pastas) devem receber em seu commit a convenção **"chore"**. Essa convenção não abrange alterações diretas em código.






