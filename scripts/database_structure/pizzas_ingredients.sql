PROMPT pizzas_ingredients...
DECLARE --CREATE OR REPLACE
nCount NUMBER;
BEGIN
SELECT COUNT(*)
  INTO nCount
  FROM USER_TABLES
  WHERE TABLE_NAME = 'PIZZAS_INGREDIENTS';

IF nCount <> 0 THEN
  EXECUTE IMMEDIATE 'DROP TABLE PIZZAS_INGREDIENTS CASCADE CONSTRAINTS';
END IF;

EXECUTE IMMEDIATE 'CREATE TABLE PIZZAS_INGREDIENTS(
pz_pz_id NUMBER(10),
ingrd_ingrd_id NUMBER(10),
amount NUMBER(10),
msru_msru_id NUMBER(10),
CONSTRAINT pz_ingrd_pz_id_fk FOREIGN KEY (pz_pz_id)
REFERENCES PIZZAS (pz_id) ON DELETE CASCADE,
CONSTRAINT pz_ingrd_ingrd_id_fk FOREIGN KEY (ingrd_ingrd_id)
REFERENCES INGREDIENTS (ingrd_id) ON DELETE CASCADE,
CONSTRAINT pz_ingrd_msru_id_fk FOREIGN KEY (msru_msru_id)
REFERENCES MEASUREMENT_UNITS (msru_id) ON DELETE CASCADE)';
END;
/