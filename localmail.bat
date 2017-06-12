SET hostname=mcanki01
SET port=25
SET username=anki@localhost.com
SET password=anki
SET recepient=anki@localhost.com
SET ssl=false
SET tlsmode=STARTTLS
SET sender=anki@localhost.com
SET JRE_HOME=C:\ARIS10.0\server\jre

java.exe -jar MailClient-1.0-SNAPSHOT-onejar.jar %hostname% %port% %username% %password% %recepient% %sender% %ssl% %tlsmode%
