CREATE TABLE ARTICLE (
	ID varchar(256)not null,
	SUBJECT varchar(256) not null,
	CHOICE_TYPE integer not null default 0,
	ACTIVE boolean default false,
	primary key (ID));
COMMENT ON TABLE ARTICLE IS '課題マスタ'
COMMENT ON COLUMN SUBJECT IS '題名'
COMMENT ON COLUMN CHOICE_TYPE IS '選択方式（０：毎度シャッフル、１：勝ち抜き）'
COMMENT ON COLUMN ACTIVE IS '実施状態（false：停止中、true：実施中）'