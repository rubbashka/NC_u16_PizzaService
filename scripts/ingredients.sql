PROMPT ingredients...
DECLARE --CREATE OR REPLACE
nCount NUMBER;
BEGIN
SELECT COUNT(*)
  INTO nCount
  FROM USER_TABLES
  WHERE TABLE_NAME = 'INGREDIENTS';

IF nCount <> 0 THEN
  EXECUTE IMMEDIATE 'DROP TABLE INGREDIENTS CASCADE CONSTRAINTS';
END IF;

EXECUTE IMMEDIATE 'CREATE TABLE INGREDIENTS(
ingrd_id NUMBER(5),
def VARCHAR2(50 CHAR) CONSTRAINT ingrd_def_nn NOT NULL,
comments VARCHAR2(200 CHAR),
CONSTRAINT ingrd_id_pk PRIMARY KEY (ingrd_id))';

nCount:=0;--sequence and trigger for primary key
SELECT COUNT(*)
  INTO nCount
  FROM USER_SEQUENCES
  WHERE SEQUENCE_NAME = 'INGRD_SEQ';

IF nCount <> 0 THEN
  EXECUTE IMMEDIATE 'DROP SEQUENCE INGRD_SEQ';
END IF;

EXECUTE IMMEDIATE 'CREATE SEQUENCE INGRD_SEQ
START WITH 1
INCREMENT BY 1
NOMAXVALUE';

EXECUTE IMMEDIATE 'CREATE OR REPLACE TRIGGER ingrd_id_trg
BEFORE INSERT ON ingredients
FOR EACH ROW
BEGIN
  IF :new.ingrd_id IS NULL THEN
    SELECT ingrd_seq.nextval INTO :new.ingrd_id FROM dual;
  END IF;
END;';
END;
/
