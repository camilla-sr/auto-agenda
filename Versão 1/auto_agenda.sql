# Host: localhost  (Version 5.5.5-10.4.28-MariaDB)
# Date: 2024-10-20 03:16:16
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "aux_prod_usados"
#

CREATE TABLE `aux_prod_usados` (
  `id_produto_usado` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fk_estoque` int(10) unsigned DEFAULT NULL,
  `fk_agendamento` int(10) unsigned DEFAULT NULL,
  `qntd_usada` int(11) DEFAULT 0,
  PRIMARY KEY (`id_produto_usado`),
  KEY `fk_estoque` (`fk_estoque`),
  KEY `fk_agendamento` (`fk_agendamento`),
  CONSTRAINT `aux_prod_usados_ibfk_1` FOREIGN KEY (`fk_estoque`) REFERENCES `estoque` (`id_estoque`),
  CONSTRAINT `aux_prod_usados_ibfk_2` FOREIGN KEY (`fk_agendamento`) REFERENCES `agendamento` (`id_agendamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "aux_prod_usados"
#


#
# Structure for table "cliente"
#

CREATE TABLE `cliente` (
  `id_cliente` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome_cliente` varchar(80) NOT NULL,
  `whatsapp_cliente` int(11) NOT NULL,
  `modelo_carro` varchar(30) NOT NULL,
  `ano_carro` int(4) DEFAULT 0,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "cliente"
#


#
# Structure for table "funcionario"
#

CREATE TABLE `funcionario` (
  `id_funcionario` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome_funcionario` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_funcionario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "funcionario"
#

INSERT INTO `funcionario` VALUES (3,'Fulano');

#
# Structure for table "lote"
#

CREATE TABLE `lote` (
  `cod_lote` varchar(10) NOT NULL DEFAULT '0',
  `data_compra` date NOT NULL,
  `data_vencimento` date NOT NULL,
  `und_lote` int(3) DEFAULT 0,
  `tipo_oleo` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`cod_lote`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "lote"
#

INSERT INTO `lote` VALUES ('09asjd','2024-03-30','2025-07-12',20,'Óleo de motor'),('981273','2024-08-23','2027-02-10',20,'Óleo pra motor'),('98742','2024-08-30','2026-05-23',20,'Óleo para limpar carburador');

#
# Structure for table "peca"
#

CREATE TABLE `peca` (
  `id_peca` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `desc_peca` varchar(150) DEFAULT NULL,
  `qntd_peca` int(2) DEFAULT 0,
  PRIMARY KEY (`id_peca`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "peca"
#

INSERT INTO `peca` VALUES (1,'Chave de rpda',15),(2,'Parafuso 0.3',230),(4,'Paralama',40);

#
# Structure for table "estoque"
#

CREATE TABLE `estoque` (
  `id_estoque` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fk_produto` int(10) unsigned DEFAULT NULL,
  `fk_lote` varchar(10) DEFAULT NULL,
  `qntd_produto` int(3) DEFAULT 0,
  `qntd_total` int(4) DEFAULT 0,
  `data_ultima_atualizacao` datetime DEFAULT NULL,
  PRIMARY KEY (`id_estoque`),
  KEY `fk_produto` (`fk_produto`),
  KEY `fk_lote` (`fk_lote`),
  CONSTRAINT `estoque_ibfk_1` FOREIGN KEY (`fk_produto`) REFERENCES `peca` (`id_peca`),
  CONSTRAINT `estoque_ibfk_2` FOREIGN KEY (`fk_lote`) REFERENCES `lote` (`cod_lote`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "estoque"
#


#
# Structure for table "tipo_servico"
#

CREATE TABLE `tipo_servico` (
  `id_servico` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `desc_servico` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_servico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "tipo_servico"
#


#
# Structure for table "agendamento"
#

CREATE TABLE `agendamento` (
  `id_agendamento` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fk_cliente` int(10) unsigned DEFAULT NULL,
  `fk_servico` int(10) unsigned DEFAULT NULL,
  `fk_funcionario` int(10) unsigned DEFAULT NULL,
  `data_cadastro` date DEFAULT NULL,
  `data_previsao_entrega` date DEFAULT NULL,
  `data_conclusao` date DEFAULT NULL,
  `status_agendamento` char(1) DEFAULT 'A',
  `observacao` text DEFAULT '',
  PRIMARY KEY (`id_agendamento`),
  KEY `fk_cliente` (`fk_cliente`),
  KEY `fk_servico` (`fk_servico`),
  KEY `fk_funcionario` (`fk_funcionario`),
  CONSTRAINT `agendamento_ibfk_1` FOREIGN KEY (`fk_cliente`) REFERENCES `cliente` (`id_cliente`),
  CONSTRAINT `agendamento_ibfk_2` FOREIGN KEY (`fk_servico`) REFERENCES `tipo_servico` (`id_servico`),
  CONSTRAINT `agendamento_ibfk_3` FOREIGN KEY (`fk_funcionario`) REFERENCES `funcionario` (`id_funcionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "agendamento"
#

