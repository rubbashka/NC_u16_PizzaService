PROMPT drinks...
DECLARE --CREATE OR REPLACE
nCount NUMBER;
BEGIN
SELECT COUNT(*)
  INTO nCount
  FROM USER_TABLES
  WHERE TABLE_NAME = 'DRINKS';

IF nCount <> 0 THEN
  EXECUTE IMMEDIATE 'DROP TABLE DRINKS CASCADE CONSTRAINTS';
END IF;

EXECUTE IMMEDIATE 'CREATE TABLE DRINKS(
drnk_id NUMBER(10),
volume NUMBER(10),
price NUMBER(10),
title VARCHAR2(50 CHAR) CONSTRAINT drnk_title_nn NOT NULL,
description VARCHAR2(200 CHAR),
CONSTRAINT drnk_id_pk PRIMARY KEY (drnk_id))';

nCount:=0;--sequence and trigger for primary key
SELECT COUNT(*)
  INTO nCount
  FROM USER_SEQUENCES
  WHERE SEQUENCE_NAME = 'DRNK_SEQ';

IF nCount <> 0 THEN
  EXECUTE IMMEDIATE 'DROP SEQUENCE DRNK_SEQ';
END IF;

EXECUTE IMMEDIATE 'CREATE SEQUENCE DRNK_SEQ
START WITH 1
INCREMENT BY 1
NOMAXVALUE';

EXECUTE IMMEDIATE 'CREATE OR REPLACE TRIGGER drnk_id_trg
BEFORE INSERT ON drinks
FOR EACH ROW
BEGIN
  IF :new.drnk_id IS NULL THEN
    SELECT drnk_seq.nextval INTO :new.drnk_id FROM dual;
  END IF;
END;';
END;
/
