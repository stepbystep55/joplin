CREATE TABLE YOUTUBE_ITEM (
	ID varchar(256)not null,
	ARTICLE_ID varchar(256)not null,
	VIDEO_ID varchar(256) not null,
	primary key (ID),
	unique (ARTICLE_ID, VIDEO_ID),
	foreign key (ARTICLE_ID) REFERENCES ARTICLE(ID)
	);