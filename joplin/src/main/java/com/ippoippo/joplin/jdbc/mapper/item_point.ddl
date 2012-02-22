CREATE TABLE ITEM_POINT (
	ID varchar(256)not null,
	ITEM_ID varchar(256) not null,
	UPDATED_COUNT Integer not null,
	POINT Integer not null,
	primary key (ID)
	unique (ITEM_ID, UPDATED_COUNT)
	);