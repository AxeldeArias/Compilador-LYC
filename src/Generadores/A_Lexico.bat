SET JAVA_HOME="C:\Program Files\Java\jdk1.8.0_251\bin"
SET PATH=%JAVA_HOME%;%PATH%
SET CLASSPATH=%JAVA_HOME%;
SET JFLEX_HOME="..\..\lib\jflex"
cd src/Analizadores
java -jar %JFLEX_HOME%\jflex-1.6.1.jar A_lexico.jflex
pause