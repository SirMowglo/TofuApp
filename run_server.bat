@ECHO OFF
TITLE Server run
cd .\api_tofu_app
call mvn clean install

call mvn spring-boot:run