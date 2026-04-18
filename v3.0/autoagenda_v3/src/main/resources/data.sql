create table if not exists superadmin (
    id_superadmin    int primary key auto_increment,
    nome             varchar(80) not null,
    usuario          varchar(30) not null unique,
    email            varchar(255) not null unique,
    senha            varchar(255) not null,
    ativo            boolean default true,
    primeiro_login   boolean default true,
    data_cadastro    timestamp default current_timestamp
);

create table if not exists oficina(
	id_oficina          int primary key auto_increment,
    razao_social        varchar(150) not null,
    nome_fantasia       varchar(100) not null,
    slug				varchar(20) unique not null,
    cnpj                varchar(18) not null unique,
    inscricao_municipal varchar(20),
    inscricao_estadual  varchar(20),
    logradouro          varchar(100),
    numero              varchar(10),
    complemento         varchar(50),
    bairro              varchar(50),
    cidade              varchar(60),
    uf                  char(2),
    cep                 varchar(9),
    telefone_principal  varchar(15) not null,
    telefone_secundario varchar(15),
    email_contato       varchar(100),
    ativo				boolean default true,
    logotipo            varchar(255) default '',
	usar_produtos       boolean default true,
    usar_financeiro     boolean default true,
    usar_auth           boolean default false,
    data_cadastro       timestamp default current_timestamp
);

create table if not exists cliente (
  id_cliente 		int primary key auto_increment,
  fk_oficina    	int not null,
  nome_cliente 		varchar(80) not null,
  telefone			varchar(15) not null,
  email				varchar(255) not null,
  foreign key (fk_oficina) references oficina (id_oficina)
);

create table if not exists veiculo(
	id_veiculo		int primary key auto_increment,
	fk_cliente		int,
	modelo			varchar(35) not null,
	marca			varchar(20) not null,
	placa			varchar(7) not null,
	foreign key (fk_cliente) references cliente (id_cliente)
);

create table if not exists servico (
  id_servico 		int primary key auto_increment,
  fk_oficina    	int not null,
  nome_servico		varchar(50) not null,
  desc_servico 		varchar(200),
  foreign key (fk_oficina) references oficina (id_oficina)
);

create table if not exists funcionario (
  id_funcionario 	int primary key auto_increment,
  fk_oficina    	int not null,
  nome_funcionario 	varchar(50) default null,
  telefone			varchar(20),
  usuario       	varchar(30) not null unique,
  email				varchar(255) not null unique,
  cpf				varchar(11) not null,
  senha     	    varchar(255) null,
  acesso			varchar(20) default 'comum',
  primeiro_login	boolean default true,
  ativo				boolean default true,
  foreign key (fk_oficina) references oficina (id_oficina)
);

create table if not exists produto (
	id_produto 		int primary key auto_increment,
	fk_oficina    	int not null,
	codigo_produto 	varchar(50) not null,
	categoria 		varchar(50) not null,
	nome_produto 	varchar(100) not null,
	preco_custo 	decimal(10, 2) not null,
	preco_venda 	decimal(10, 2) not null,
	fornecedor 		varchar(100),
	estoque_atual 	int default 0,
	estoque_minimo 	int default 0,
	descricao 		text,
	foreign key (fk_oficina) references oficina (id_oficina)
);

create table if not exists agendamento (
  id_agendamento 		int primary key auto_increment,
  fk_oficina    		int not null,
  fk_funcionario		int not null,
  fk_cliente 			int not null,
  fk_veiculo			int not null,
  fk_servico 			int not null,
  data_cadastro 		date,
  data_previsao 		date,
  data_conclusao 		date,
  status_agendamento 	varchar(20) default 'agendado',
  observacao 			text default null,
  foreign key (fk_servico) references servico (id_servico),
  foreign key (fk_funcionario) references funcionario (id_funcionario),
  foreign key (fk_cliente) references cliente (id_cliente),
  foreign key (fk_veiculo) references veiculo (id_veiculo),
  foreign key (fk_oficina) references oficina (id_oficina)
);

create table if not exists fotos_agendamento (
  id_foto 			int primary key auto_increment,
  fk_agendamento 	int null,
  nome_arquivo 		varchar(100) not null,
  data_criacao 		datetime default current_timestamp,
  token_temp 		varchar(50) null,
  foreign key (fk_agendamento) references agendamento (id_agendamento)
);

create table if not exists log_sistema (
  id_log          bigint primary key auto_increment,
  data_evento     datetime default current_timestamp,
  tipo_usuario    varchar(20),
  id_usuario      int null,
  fk_oficina      int null,
  acao            varchar(50),
  tipo_alvo       varchar(50),
  alvo_id     	  int null,
  descricao       text
);