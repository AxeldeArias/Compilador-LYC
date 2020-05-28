SET JAVA_HOME="C:\Program Files\Java\jdk1.8.0_251\bin"
SET PATH=%JAVA_HOME%;%PATH%
SET CLASSPATH=%JAVA_HOME%;
SET JFLEX_HOME="lib\jflex"

java -jar %JFLEX_HOME%\jflex-1.6.1.jar ".\src\Analizadores\A_lexico.jflex"
pause

java -jar "lib\jcup\java-cup-11b.jar" -parser analisis_sintactico -symbols Simbolos ".\src\Analizadores\A_Sintactico.cup"
pause
