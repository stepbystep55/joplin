CREATE TABLE ARTICLE (
	ID varchar(256)not null,
	SUBJECT varchar(256) not null,
	ACTIVE boolean default false,
	primary key (ID));