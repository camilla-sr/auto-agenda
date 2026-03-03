create table if not exists cliente (
  id_cliente 	int primary key auto_increment,
  nome_cliente 	varchar(80) not null,
  telefone		varchar(15) not null,
  email			varchar(255) not null
);

create table if not exists veiculo(
	id_veiculo	int primary key auto_increment,
	fk_cliente	int,
	modelo		varchar(35) not null,
	marca		varchar(20) not null,
	placa		varchar(7) not null,
	foreign key (fk_cliente) references cliente (id_cliente)
);

create table if not exists servico (
  id_servico 		int primary key auto_increment,
  nome_servico		varchar(30) not null,
  desc_servico 		varchar(200)
);

create table if not exists funcionario (
  id_funcionario int primary key auto_increment,
  nome_funcionario 	varchar(50) default null,
  usuario       	varchar(15) not null unique,
  email				varchar(255) not null unique,
  cpf				varchar(11) not null,
  senha     	    varchar(255) null,
  acesso			varchar(5) default 'comum',
  primeiro_login	boolean default true
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
  id_agendamento 		int primary key auto_increment,
  fk_cliente 			int,
  fk_veiculo			int,
  fk_servico 			int,
  data_cadastro 		date,
  data_previsao 		date,
  data_conclusao 		date,
  status_agendamento 	varchar(20) default 'agendado',
  observacao 			text default null,
  foreign key (fk_servico) references servico (id_servico),
  foreign key (fk_cliente) references cliente (id_cliente),
  foreign key (fk_veiculo) references veiculo (id_veiculo)
);

create table if not exists fotos_agendamento (
  id_foto 			int primary key auto_increment,
  fk_agendamento 	int null,
  nome_arquivo 		varchar(100) not null,
  data_criacao 		datetime default current_timestamp,
  token_temp 		varchar(50) null,
  foreign key (fk_agendamento) references agendamento (id_agendamento)
);
