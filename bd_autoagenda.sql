create database if not exists auto_agenda;
use auto_agenda;

create table tipo_servico(
id_servico int unsigned auto_increment primary key,
desc_servico varchar(200)
);

create table funcionario(
id_funcionario int unsigned auto_increment primary key,
nome_funcionario varchar(50)
);

create table peca(
id_peca int unsigned auto_increment primary key,
desc_peca varchar(150),
qntd_peca int(2) default 0
);

create table lote(
cod_lote int unsigned auto_increment primary key,
data_compra date not null,
data_vencimento date not null,
und_lote int(3) default 0,
tipo_oleo varchar(30),      #definição do uso para o óleo
qntd_garrafa int(2) default 0
);

create table estoque(			#tabela auxiliar para quantificar estoque
id_estoque int unsigned auto_increment primary key,
fk_produto int unsigned,
fk_lote int unsigned,
qntd_produto int(3) default 0,
qntd_total int(4) default 0,
data_ultima_atualizacao datetime,
constraint foreign key (fk_produto) references peca(id_peca),
constraint foreign key (fk_lote) references lote(cod_lote)
);
  
create table cliente(
  id_cliente int unsigned auto_increment primary key,
  nome_cliente varchar(80) not null,
  whatsapp_cliente int(11) not null,
  modelo_carro varchar(30) not null,
  ano_carro int(4) default 0
  );

create table agendamento(
id_agendamento int unsigned auto_increment primary key,
fk_cliente int unsigned,
fk_servico int unsigned,
fk_funcionario int unsigned,
data_cadastro date,		#quando o agendamento foi cadastrado no sistema
data_previsao_entrega date,		#precisão de quando será concluído o serviço
data_conclusao date,		#data de conclusão de fato, baixa do agendamento no sistema
status_agendamento char(1) default 'A',
observacao text default '',
constraint foreign key (fk_cliente) references cliente(id_cliente),
constraint foreign key (fk_servico) references tipo_servico(id_servico),
constraint foreign key (fk_funcionario) references funcionario(id_funcionario)
);

create table aux_prod_usados(       #tabela de conexão entre as duas tabelas 
id_produto_usado int unsigned auto_increment primary key,
fk_estoque int unsigned,
fk_agendamento int unsigned,
qntd_usada int default 0,
constraint foreign key (fk_estoque) references estoque(id_estoque),
constraint foreign key (fk_agendamento) references agendamento(id_agendamento)
);
