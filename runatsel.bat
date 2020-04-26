flex Lexico.L
bison -dyv Sintactico.y
gcc.exe lex.yy.c y.tab.c -o TP.exe
TP.exe Prueba.txt
pause
del lex.yy.c y.tab.c y.output y.tab.h TP.exe