%{
#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <string.h>
#include "y.tab.h"

#include "./listaDinamica/listaHeader.h"
#include "./tablaSimbolos/tablaHeader.h"

int yystopparser = 0;
FILE *yyin;

/* Variables TABLA DE SIMBOLOS */
int tipoId;
int lengString;
char valor[100];

tLista listaSimbolos;

%}

//Estructura yylval
%union {
int int_val;
double double_val;
char *str_val;
}

%token <str_val>ID
%token <int_val>CONST_INT
%token <double_val>CONST_FLOAT
%token <str_val>CONST_STR

%token DEFVAR ENDDEF
%token TIPO_INTEGER TIPO_FLOAT 

%token WHILE
%token IF ELSE
%token OP_AND OP_OR OP_NOT

%token OP_ASIG
%token OP_SUMA OP_RESTA 
%token OP_MUL OP_DIV
%token OP_IGUAL OP_DISTINTO
%token OP_MENOR OP_MENOR_IGUAL OP_MAYOR OP_MAYOR_IGUAL 
%token OP_ASIG_SUM OP_ASIG_RES OP_ASIG_MUL OP_ASIG_DIV
%token LL_A LL_C P_A P_C
%token PUNTO_COMA 
%token DOS_PUNTOS
%token COMA

%token GET
%token DISPLAY

%%
start   : programa { recorrerLista(&listaSimbolos); guardarTabla(&listaSimbolos); printf("Regla Compilacion OK\n");}
		;

programa	: seccion_declaraciones bloque { printf("Regla PROGRAMA \n");}
			;

seccion_declaraciones 	: DEFVAR bloque_declaraciones ENDDEF { printf("Regla SECCION DECLARACION \n");}
						;

bloque_declaraciones	: declaracion
						| bloque_declaraciones declaracion  { printf("Regla BLOQUE DECLARACION \n");}
						;

declaracion : tipo_dato DOS_PUNTOS lista_ids  { printf("Regla DECLARACION \n");}
			;

tipo_dato 	: TIPO_INTEGER { tipoId = T_INTEGER;}  { printf("Regla TIPO DECLARACION \n");}
			| TIPO_FLOAT { tipoId = T_FLOAT;}  { printf("Regla TIPO DECLARACION \n");}
			;

lista_ids	: ID { agregarVarATabla(&listaSimbolos, yylval.str_val, NULL, tipoId, (int) NULL); printf("Regla LISTA IDS \n");}
			| lista_ids PUNTO_COMA ID { agregarVarATabla(&listaSimbolos, yylval.str_val, NULL, tipoId, (int) NULL); printf("Regla LISTA IDS \n");}
			;

bloque  : sentencia
		| bloque sentencia { printf("Regla BLOQUE \n");}

sentencia   : asignacion { printf("Regla SENTENCIA \n");}
			| asignacion_especial { printf("Regla SENTENCIA \n");}
			| bloque_if { printf("Regla SENTENCIA \n");}
			| bloque_while { printf("Regla SENTENCIA \n");}
			| expresion { printf("Regla SENTENCIA \n");}
			| escritura { printf("Regla SENTENCIA \n");}
			| lectura { printf("Regla SENTENCIA \n");}
			;

bloque_if	: IF P_A expresion_logica P_C LL_A bloque LL_C { printf("Regla BLOQUE_IF \n");}
			| IF P_A expresion_logica P_C LL_A bloque LL_C
				ELSE LL_A bloque LL_C { printf("Regla BLOQUE IF ELSE \n");}
			;

bloque_if_unario	: IF P_A expresion_logica COMA expresion COMA expresion P_C { printf("Regla BLOQUE IF UNARIO \n");}

bloque_while	: WHILE P_A expresion_logica P_C LL_A bloque LL_C { printf("Regla BLOQUE_WHILE \n");}
				;

op_booleano 	: OP_MENOR
				| OP_MENOR_IGUAL
				| OP_MAYOR
				| OP_MAYOR_IGUAL
				| OP_IGUAL   
				| OP_DISTINTO
				;

expresion_logica	: expresion_logica OP_AND termino_logico { printf("Regla EXPRESION_LOGICA \n");}
					| expresion_logica OP_OR termino_logico { printf("Regla EXPRESION_LOGICA \n");}
					| termino_logico { printf("Regla EXPRESION_LOGICA \n");}
					;

termino_logico	: OP_NOT termino_logico { printf("Regla TERMINO LOGICO \n");}
				| expresion op_booleano expresion { printf("Regla TERMINO LOGICO \n");}
				;
	

asignacion 	: ID OP_ASIG expresion { chequearVarEnTabla(&listaSimbolos, $1); printf("Regla ASIGNACION \n");}
			;

asignacion_especial : ID operadores_especiales expresion  { chequearVarEnTabla(&listaSimbolos, $1); 
															printf("Regla ASIGNACION ESPECIAL\n"); }
					;
															
operadores_especiales   : OP_ASIG_SUM | OP_ASIG_RES | OP_ASIG_MUL | OP_ASIG_DIV
						;

		
expresion	: termino
			| expresion OP_SUMA termino { printf("Regla EXPRESION ARITMETICA \n");}
			| expresion OP_RESTA termino { printf("Regla EXPRESION ARITMETICA \n");}
			| bloque_if_unario
			;

		
termino	: termino OP_MUL factor { printf("Regla TERMINO \n");}
		| termino OP_DIV factor  { printf("Regla TERMINO \n");}
		| factor
		;
			
factor	: P_A expresion P_C { printf("Regla FACTOR \n");}
		;

factor	: ID { chequearVarEnTabla(&listaSimbolos, $1);}
		| CONST_INT { sprintf(valor, "%d", yylval.int_val); agregarVarATabla(&listaSimbolos, NULL, valor, (int) NULL, (int) NULL);}
		| CONST_FLOAT { sprintf(valor, "%f", yylval.double_val); agregarVarATabla(&listaSimbolos, NULL, valor, (int) NULL, (int) NULL );}	
		;

escritura	: DISPLAY ID { printf("Regla ESCRITURA\n");}
			| DISPLAY CONST_STR { lengString = (strlen(yylval.str_val)-2); agregarVarATabla(&listaSimbolos, NULL, yylval.str_val, (int) NULL, lengString); printf("Regla EXPRESION CADENA \n");}
			;

lectura	: GET ID { printf("Regla LECTURA\n");}
		;

%%

int main(int argc, char *argv[])
{
	if ((yyin = fopen(argv[1], "rt")) == NULL)
	{
		printf("\nNo se puede abrir el archivo: %s\n", argv[1]);
	}
	else
	{
	  	crearLista(&listaSimbolos);
		yyparse();
  	}
  	fclose(yyin);
  	return 0;
}


