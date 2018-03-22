@echo off

rem 进入到这个bat存放的目录
cd %~dp0 

rem 运行打包好的jar，也就是java可执行的东西....
java -jar Expression.jar

rem 停止
pause