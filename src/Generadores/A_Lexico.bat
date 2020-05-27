SET JAVA_HOME="C:\Program Files\Java\jdk1.8.0_251\bin"
SET PATH=%JAVA_HOME%;%PATH%
SET CLASSPATH=%JAVA_HOME%;
SET JFLEX_HOME="C:\flex\paquetes jflex y jcup\jflex-1.6.1\lib"
cd ../Analizadores
java -jar %JFLEX_HOME%\jflex-1.6.1.jar A_lexico.jflex
pause