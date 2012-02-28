CREATE TABLE MATCH_HISTORY (
	ID varchar(256)not null,
	USER_ID varchar(256)not null,
	ARTICLE_ID varchar(256)not null,
	VIDEO_ID varchar(256) not null,
	RANK integer not null default 0,
	primary key (ID),
	unique (USER_ID, ARTICLE_ID, VIDEO_ID)
	);