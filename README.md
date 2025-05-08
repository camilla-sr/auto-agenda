> Atualizado em 08/05/2025

## :grey_question: O que há de novo

Nesta nova versão, foi feita com Spring Boot e com a linguagem HTML e suas ferramentas: JavaScript, CSS sob a frame BootStrap v5.3.6. Para a organização é usado o padrão MVC (Model, View e Controller).

O uso de script de Banco de Dados foram deixadas de fora deste projeto com o uso da dependência "H2 Database".


## :mag_right: Detalhes do projeto

O projeto __versão Spring__ fica localizado no caminho `**/auto-agenda/Versão Spring` enquanto a __versão para VS Code__ rodando no formato web fica em: `**/auto-agenda/Versão 1.5.`

> A existência da pasta com a `versão 1.5` serve para facilitar o acesso as interfaces web do projeto para criar e modificar funcionalidades e design usando o VS Code.





#### :computer: Abrindo pela primeira vez

No Eclipse:

```
Importar projeto > Maven > "Existing Maven Projects" > Localizar e selecionar caminho do projeto > Finalize com "Finish" 
```

> **:bulb: Dica de uso com VS Code:**
>
>A importação no VS Code é o mesmo processo como o de qualquer outro arquivo, porém caso queira fazer alterações e vê-las aparecendo em tempo real (sem necessidade de apertar a tecla F5 a cada alteração), utilize a extensão "Live Server".



#### :arrow_forward: Rodando o projeto 

Para rodar o projeto é necessário que a plataforma de desenvolvimento Eclipse esteja aberta no arquivo principal com nome `AutoagendaApplication.java`, com isso feito, basta executar o arquivo que irá inicializar o Spring Boot e, caso não tenha nenhuma falha durante as validações de arquivos (que será indicado pelo console da plataforma), abra a página inicial do projeto no navegador `index.html`.


> **:round_pushpin: Localização dos arquivos:**
> - **AutoagendaApplication.java**
> **\Versão Spring\autoagenda\src\main\java\br\com\autoagenda\autoagenda
>
> - **index.html**
> **\Versão Spring\autoagenda\src\main\resources\static



## :ledger: Padronização de commits
Para padronizar os commits, o GitHub possui convenções que deixam os logs de mudanças mais fáceis de entender, segue abaixo os commits principais para ser utilizados no projeto:

> - **"feat:** *[mensagem de commit]"*: usado para adicionar uma nova funcionalidade.
> - **"chore:** *[mensagem de commit]"*: usado para atualizações de dependências, configurações ou tarefas auxiliares.
> - **"fix:** *[mensagem de commit]*": usado para correção de bugs
> - **"refactor:** *[mensagem de commit]*": usado para melhorias da estrutura de código.
>
>
> Commits que (quase ou) não afetam a lógica do desenvolvimento:
>- **"docs:** *[mensagem de commit]*": usado para alterações na documentação.
> - **"add:** *[mensagem de commit]*": usado para adição de novos arquivos no projeto. (commit informal)
> - **"style:** *[mensagem de commit]*": usado para mudanças de formatação (espaços, vírgulas, etc.) que não afetam a lógica.
>
> *Mudanças de arquivos (limpeza de arquivos, renomeação ou ajustes de pastas) podem ser realizados usando a convenção **"chore"**.






