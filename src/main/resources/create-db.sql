CREATE TABLE "PUBLIC".CONTACTS
(
    ID INT PRIMARY KEY NOT NULL IDENTITY,
    NAME VARCHAR(25) NOT NULL,
    SURNAME VARCHAR(25) NOT NULL,
    PHONE VARCHAR(25) NOT NULL
);
--ALTER TABLE CONTACTS ADD CONSTRAINT unique_ID UNIQUE (ID);

