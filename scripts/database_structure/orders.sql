PROMPT orders...
DECLARE --CREATE OR REPLACE
nCount NUMBER;
BEGIN
SELECT COUNT(*)
  INTO nCount
  FROM USER_TABLES
  WHERE TABLE_NAME = 'ORDERS';

IF nCount <> 0 THEN
  EXECUTE IMMEDIATE 'DROP TABLE ORDERS CASCADE CONSTRAINTS';
END IF;

EXECUTE IMMEDIATE 'CREATE TABLE ORDERS(
ord_id NUMBER(10),
clnt_clnt_id NUMBER(10),
order_date DATE,
delivery_date DATE,
description VARCHAR2(200 CHAR),
CONSTRAINT ord_id_pk PRIMARY KEY (ord_id),
CONSTRAINT ord_clnt_id_fk FOREIGN KEY (clnt_clnt_id)
REFERENCES CLIENTS (clnt_id) ON DELETE CASCADE)';

nCount:=0;--sequence and trigger for primary key
SELECT COUNT(*)
  INTO nCount
  FROM USER_SEQUENCES
  WHERE SEQUENCE_NAME = 'ORD_SEQ';

IF nCount <> 0 THEN
  EXECUTE IMMEDIATE 'DROP SEQUENCE ORD_SEQ';
END IF;

EXECUTE IMMEDIATE 'CREATE SEQUENCE ORD_SEQ
START WITH 1
INCREMENT BY 1
NOMAXVALUE';

EXECUTE IMMEDIATE 'CREATE OR REPLACE TRIGGER ord_id_trg
BEFORE INSERT ON orders
FOR EACH ROW
BEGIN
  IF :new.ord_id IS NULL THEN
    SELECT ord_seq.nextval INTO :new.ord_id FROM dual;
  END IF;
END;';
END;
/