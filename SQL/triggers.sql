-- Sequence bill TRIGGER

create or replace TRIGGER TRG_PK_BILL
BEFORE INSERT ON BILL
FOR EACH ROW
BEGIN
  if inserting then
      if :NEW."ID" is null then
         select BILL_SEQ.nextval into :NEW."ID" from dual;
      end if;
   end if;
END;

-- Sequence Group TRIGGER

create or replace TRIGGER TRG_PK_GRUPO
BEFORE INSERT ON GRUPO
FOR EACH ROW
  BEGIN
    IF inserting THEN
      IF :NEW."ID" IS NULL THEN
        SELECT grupo_seq.NEXTVAL INTO :NEW."ID" FROM dual;
      END IF;
    END IF;
  END;

-- Sequence Transaction TRIGGER

CREATE OR REPLACE TRIGGER TRG_PK_TX
BEFORE INSERT ON TRANSACTION
FOR EACH ROW
  BEGIN
    IF inserting THEN
      IF :NEW."ID_TRANSACTION" IS NULL THEN
        SELECT TX_SEQ.NEXTVAL INTO :NEW."ID_TRANSACTION" FROM dual;
      END IF;
    END IF;
  END;

-- Auditoria de GRUPO
create or replace TRIGGER TRG_AUDITORIA_GRUPO
BEFORE DELETE OR INSERT OR UPDATE OF LEADER_EMAIL,NAME ON GRUPO
FOR EACH ROW
BEGIN
  IF DELETING THEN
    INSERT INTO AUDITORIA_GRUPO (FECHA, CAMPO, OPERACION, VIEJO, NUEVO) VALUES (Sysdate,'ALL COLUMS','DELTE','NON','NON');
  END IF;
  IF INSERTING THEN
    INSERT INTO AUDITORIA_GRUPO (FECHA, CAMPO, OPERACION, VIEJO, NUEVO) VALUES (Sysdate,'ALL COLUMS','INSERT','NON','NON');
  END IF;
  IF UPDATING ('NAME') THEN
    INSERT INTO AUDITORIA_GRUPO (FECHA, CAMPO, OPERACION, VIEJO, NUEVO) VALUES (Sysdate,'NAME','UPDATE',:OLD.NAME,:NEW.NAME);
  END IF;
  IF UPDATING ('NAME') THEN
    INSERT INTO AUDITORIA_GRUPO (FECHA, CAMPO, OPERACION, VIEJO, NUEVO) VALUES (Sysdate,'NAME','UPDATE',:OLD.NAME,:NEW.NAME);
  END IF;
  IF UPDATING ('LEADER_EMAIL') THEN
    INSERT INTO AUDITORIA_GRUPO (FECHA, CAMPO, OPERACION, VIEJO, NUEVO) VALUES (Sysdate,'LEADER_EMAIL','UPDATE',:OLD.LEADER_EMAIL,:NEW.LEADER_EMAIL);
  END IF;
END;
