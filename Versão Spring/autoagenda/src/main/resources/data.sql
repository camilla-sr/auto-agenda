create table if not exists cliente (
  id_cliente int primary key auto_increment,
  nome_cliente varchar(80) not null,
  whatsapp_cliente varchar(15) not null,
  modelo_carro varchar(30) not null,
  ano_carro int default 0
);

create table if not exists servico (
  id_servico int primary key auto_increment,
  desc_servico varchar(200)
);

create table if not exists funcionario (
  id_funcionario int primary key auto_increment,
  nome_funcionario varchar(50) default null,
  usuario       varchar(15) not null unique,
  senha         varchar(15) not null,
  acesso		varchar(5) default 'comum'
);

create table if not exists produto (
	id_produto int primary key auto_increment,
	codigo_produto varchar(50) not null,
	categoria varchar(50) not null,
	nome_produto varchar(100) not null,
	preco_custo decimal(10, 2) not null,
	preco_venda decimal(10, 2) not null,
	fornecedor varchar(100),
	estoque_atual int default 0,
	estoque_minimo int default 0,
	descricao text
);

create table if not exists agendamento (
  id_agendamento int primary key auto_increment,
  fk_cliente int,
  fk_servico int,
  data_cadastro date,
  data_previsao_entrega date,
  data_conclusao date,
  status_agendamento varchar(20) default 'Ativo',
  observacao text default null,
  foreign key (fk_cliente) references cliente (id_cliente),
  foreign key (fk_servico) references servico (id_servico)
);
