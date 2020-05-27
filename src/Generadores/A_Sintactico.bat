SET PATH=%JAVA_HOME%;%PATH%
SET CLASSPATH=%JAVA_HOME%;
cd ../Analizadores
java -jar "C:\flex\paquetes jflex y jcup\java-cup-11b.jar" -parser analisis_sintactico -symbols Simbolos A_Sintactico.cup
pause
