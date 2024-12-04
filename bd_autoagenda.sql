create database if not exists auto_agenda;
use auto_agenda;

create table cliente (
  id_cliente int(10) unsigned auto_increment primary key,
  nome_cliente varchar(80) not null,
  whatsapp_cliente varchar(15) not null,
  modelo_carro varchar(30) not null,
  ano_carro int(4) default 0
);

create table tipo_servico (
  id_servico int(10) unsigned auto_increment primary key,
  desc_servico varchar(200) default null
);

create table funcionario (
  id_funcionario int(10) unsigned auto_increment primary key,
  nome_funcionario varchar(50) DEFAULT NULL
);

create table peca (
  id_peca int(10) unsigned auto_increment primary key,
  desc_peca varchar(150) default null
);

create table lote(
  cod_lote varchar(10) not null default '' primary key,
  data_compra date not null,
  data_vencimento date not null,
  und_lote int(3) default 0,
  tipo_oleo varchar(30) default null
);

create table estoque (
  id_estoque int(10) unsigned auto_increment primary key,
  fk_produto int(10) unsigned default null,
  fk_lote varchar(10) default null,
  quantidade int(3) default 0,
  data_ultima_atualizacao datetime default null,
  foreign key (fk_produto) references peca (id_peca),
  foreign key (fk_lote) references lote (cod_lote)
);

create table agendamento (
  id_agendamento int(10) unsigned auto_increment primary key,
  fk_cliente int(10) unsigned default null,
  fk_servico int(10) unsigned default null,
  fk_funcionario int(10) unsigned DEFAULT NULL,
  data_cadastro date default null,
  data_previsao_entrega date default null,
  data_conclusao date default null,
  status_agendamento char(1) default 'A',
  observacao text default null,
  foreign key  (fk_cliente) references cliente (id_cliente),
  foreign key (fk_servico) references tipo_servico (id_servico),
  foreign key (fk_funcionario) references funcionario (id_funcionario)
);

create table aux_prod_usados (
  id_produto_usado int(10) unsigned auto_increment primary key,
  fk_estoque int(10) unsigned default null,
  fk_agendamento int(10) unsigned default null,
  qntd_usada int(11) default 0,
  foreign key (fk_estoque) references estoque (id_estoque),
  foreign key (fk_agendamento) references agendamento (id_agendamento)
);
