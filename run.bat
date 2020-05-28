::SET JAVA_HOME="C:\Program Files\Java\jdk1.8.0_251\bin"
::SET CLASSPATH=%JAVA_HOME%;
::Son necesarios?

SET JFLEX_JAR=".\lib\jflex\jflex-1.6.1.jar"
SET JCUP_JAR=".\lib\jcup\java-cup-11b.jar"

SET LEXICO=".\src\main\jflex\Lexico.jflex"
SET SINTAXIS=".\src\main\cup\Sintactico.cup"

SET ANALIZADORES_OUT=".\src\main\java\Analizadores"

java -jar %JFLEX_JAR% -d %ANALIZADORES_OUT% %LEXICO%
::pause

java -jar %JCUP_JAR% -destdir %ANALIZADORES_OUT% -package "Analizadores" -parser AnalizadorSintactico -symbols Simbolos %SINTAXIS%
::pause
