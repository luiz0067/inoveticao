/*conteudoinicio*/
create table menus(
	id int not null auto_increment primary key,
	Titulo varchar(50)
);
create table paginas(
	id int not null auto_increment primary key,	
	id_menu int not null,
	Titulo varchar(50)
);
create table conteudo(
	id int not null auto_increment primary key,
	Titulo varchar(50),
	conteudo blob not null,
	inicio timestamp not null,
	fim timestamp
);
create table paginasconteudo(
	id_conteudo int not null,
	id_paginas int not null
);
ALTER TABLE menuspaginas
ADD CONSTRAINT fk_menuspaginas
FOREIGN KEY (id_menu)
REFERENCES menus(id);

ALTER TABLE paginasconteudo
ADD CONSTRAINT fk_paginasconteudo_1
FOREIGN KEY (id_conteudo)
REFERENCES conteudo(id);

ALTER TABLE paginasconteudo
ADD CONSTRAINT fk_paginasconteudo_2
FOREIGN KEY (id_paginas)
REFERENCES paginas(id);
/*conteudofim*/
/*fotosinicio*/
create table categoriafotos(
	id int not null auto_increment primary key,
	Titulo varchar(50)
);
create table Fotos(
	id int not null auto_increment primary key,
	id_categoria int not,
	Titulo varchar(50),
	Descricao varchar(50),
	src  varchar(50)
);
ALTER TABLE Fotos
ADD CONSTRAINT fk_Fotos
FOREIGN KEY (id_categoria)
REFERENCES categoriafotos(id);
/*fotosfim*/
/*videosfim*/
create table categoriavideos(
	id int not null auto_increment primary key,
	Titulo varchar(50)
);
create table videos(
	id int not null auto_increment primary key,
	id_categoria int not,
	Titulo varchar(50),
	Descricao varchar(50),
	src  blob
);
ALTER TABLE videos
ADD CONSTRAINT fk_videos
FOREIGN KEY (id_categoria)
REFERENCES categoriavideos(id);
/*videosfim*/


