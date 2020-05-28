SET PATH=%JAVA_HOME%;%PATH%
SET CLASSPATH=%JAVA_HOME%;
cd src\Analizadores
java -jar "..\..\lib\jcup\java-cup-11b.jar" -parser analisis_sintactico -symbols Simbolos A_Sintactico.cup
pause
