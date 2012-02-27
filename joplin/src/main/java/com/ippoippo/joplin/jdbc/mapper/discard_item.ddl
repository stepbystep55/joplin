CREATE TABLE DISCARD_ITEM (
	ID varchar(256)not null,
	USER_ID varchar(256)not null,
	ARTICLE_ID varchar(256)not null,
	VIDEO_ID varchar(256) not null,
	primary key (ID),
	unique (USER_ID, ARTICLE_ID, VIDEO_ID)
	);