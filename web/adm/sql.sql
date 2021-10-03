CREATE TABLE  sorteio (
  id int(11) NOT NULL AUTO_INCREMENT,
  nome varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  telefone varchar(50) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE dadossorteio(
  id int(11) NOT NULL AUTO_INCREMENT,
  titulo varchar(50) NOT NULL,
  data_inicio date NOT NULL,
  data_Fim date,
  id sorteado,
  PRIMARY KEY (id)
);
drop table if exists agenda;
create table agenda(
	id int not null auto_increment primary key,
	titulo varchar(50) not null,
	evento varchar(50) not null,
        src varchar(50),
	dataevento varchar(50) not null
);
drop table if exists usuario;
create table usuario(
	id int not null auto_increment primary key,
	nome varchar(50) not null,
	login varchar(50) not null,
	senha varchar(50) not null
);
/*conteudoinicio*/
drop table if exists menus;
create table menus(
	id int not null auto_increment primary key,
	titulo varchar(50)
);
drop table if exists conteudo;
create table conteudo(
	id int not null auto_increment primary key,
        id_paginas int not null,
	titulo varchar(50),
	conteudo blob not null,
	inicio timestamp not null,
	fim timestamp
);
drop table if exists paginas;
create table paginas(
	id int not null auto_increment primary key,
	titulo varchar(50),
        src varchar(50)
);
alter table conteudo
add constraint fk_conteudo_pagina
foreign key (id_paginas)
references paginas(id);
/*conteudofim*/
/*fotosinicio*/
drop table if exists categoriafotos;
create table categoriafotos(
	id int not null auto_increment primary key,
	titulo varchar(50)
);
drop table if exists fotos;
create table fotos(
	id int not null auto_increment primary key,
	id_categoria int not null,
	titulo varchar(50),
	descricao varchar(50),
	src  varchar(50)
);
alter table fotos
add constraint fk_fotos
foreign key (id_categoria)
references categoriafotos(id);
/*fotosfim*/
/*bannersinicio*/
drop table if exists categoriabanners;
create table categoriabanners(
	id int not null auto_increment primary key,
	titulo varchar(50)
);
drop table if exists banners;
create table banners(
	id int not null auto_increment primary key,
	id_categoria int not null,
	titulo varchar(50),
	descricao varchar(50),
	src  varchar(50)
);
alter table banners
add constraint fk_banners
foreign key (id_categoria)
references categoriabanners(id);
/*bannersfim*/
/*patrocinadoresinicio*/
drop table if exists categoriapatrocinadores;
create table categoriapatrocinadores(
	id int not null auto_increment primary key,
	titulo varchar(50)
);
drop table if exists patrocinadores;
create table patrocinadores(
	id int not null auto_increment primary key,
	id_categoria int not null,
	titulo varchar(50),
	descricao varchar(50),
	src  varchar(50)
);
alter table patrocinadores
add constraint fk_patrocinadores
foreign key (id_categoria)
references categoriapatrocinadores(id);
/*patrocinadoresfim*/
/*videosfim*/
drop table if exists categoriavideos;
create table categoriavideos(
	id int not null auto_increment primary key,
	titulo varchar(50)
);
drop table if exists videos;
create table videos(
	id int not null auto_increment primary key,
	id_categoria int not  null,
	titulo varchar(50),
	descricao varchar(50),
	src  blob
);
alter table videos
add constraint fk_videos
foreign key (id_categoria)
references categoriavideos(id);
/*videosfim*/
create table comentarios(
    id int not null auto_increment primary key,
    titulo varchar(50),
    descricao blob,
    data_cadastro timestamp not null,
    exibir boolean
);
create table contato(
    id int not null auto_increment primary key,
    nome varchar(50),
    email varchar(50),
    ativo boolean,
    data_cadastro datetime
);
create table contagem(
    id int not null auto_increment primary key,
    ip varchar(50),
    data_cadastro Date
);
create table fotospaginas(
    id int not null auto_increment primary key,
    id_paginas int not null,
    titulo varchar(50),
    descricao varchar(50),
    src  varchar(50)
);
alter table fotospaginas
add constraint fk_fotospaginas
foreign key (id_paginas)
references paginas(id);
insert into usuario (nome,login,senha) values ('adm','adm','adm');
insert into usuario (nome,login,senha) values ('root','root','root');
