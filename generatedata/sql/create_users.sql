CREATE USER c##voly IDENTIFIED BY voly;
GRANT ALL PRIVILEGES TO c##voly;


CREATE USER c##amazon IDENTIFIED BY voly;
GRANT ALL PRIVILEGES TO c##amazon;

SELECT username, account_status FROM dba_users;