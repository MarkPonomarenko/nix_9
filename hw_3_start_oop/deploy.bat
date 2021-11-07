#echo off
call chcp 1251
call mvn clean install
call java -jar .\target\maven.jar