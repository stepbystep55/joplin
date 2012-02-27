CREATE TABLE HOT_ITEM (
	ID varchar(256)not null,
	USER_ID varchar(256)not null,
	ARTICLE_ID varchar(256)not null,
	SELECTED_AT date not null,
	VIDEO_ID varchar(256) not null,
	primary key (ID),
	unique (USER_ID, ARTICLE_ID, SELECTED_AT)
	);