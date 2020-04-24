%{
#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include "y.tab.h"
#include "string.h"

#include "listaDinamica\func.c"
#include "tablaSimbolos\func.c"

#define TAMMAX 100

FILE  *yyin;

/* Variables TABLA DE SIMBOLOS */
tLista lista;
char longitudCadena[10];

%}
%option noyywrap  
%option yylineno 


DIGITO		[0-9]
LETRA	    [a-zA-Z]
ID			{LETRA}({LETRA}|{DIGITO}|_)*

%%
"="	        {printf("Token =\n"); return ASIG;}     
"+"			{printf("Token +\n"); return OP_SUMA;}       
"-"			{printf("Token -\n"); return OP_RESTA;}       
"*"			{printf("Token *\n"); return OP_MUL;}       
"/"			{printf("Token /\n"); return OP_DIV;}       
"("			{printf("Token (\n"); return P_A;}   
")"			{printf("Token )\n"); return P_C;}   
";"			{printf("Token ;\n"); return PYC;}     
"IF"	    {printf("Token IF\n"); return IF;}     
"ELSE"	    {printf("Token IF\n"); return ELSE;}     
"{"	        {printf("Token {\n"); return LLAVE_A;}     
"}"	        {printf("Token }\n"); return LLAVE_C;}   
"<"         {printf("Token <\n"); return OP_MENOR;}
"<="        {printf("Token <=\n"); return OP_MENOROIGUAL;}
">"         {printf("Token >\n"); return OP_MAYOR;}
">="        {printf("Token >=\n"); return OP_MAYOROIGUAL;}
"=="        {printf("Token ==\n"); return OP_IGUAL;}
"DEFVAR"    {printf("Token DEFVAR\n"); return DEF_VAR;}
"ENDVAR"    {printf("Token ENDVAR\n"); return END_VAR;}
":"         {printf("Token :\n"); return DOS_PUNTOS;}
"Float"     {printf("Token Float\n"); return Float;}
"Integer"   {printf("Token Integer\n"); return Integer;}
<<EOF>>     {printf("Token EOF\n");recorrerLista(&lista); yyterminate();}


{ID}			{ 
    yylval.str_val = yytext;
    printf("Token ID(%s)\n",yylval.str_val) ;
    return ID;
}

{DIGITO}+		{ 
    yylval.intval = atoi(yytext);
    printf("Token ENTERO(%d)\n",yylval.intval) ; 
    insertarEnTablaDeSimbolos(&lista, NULL, yytext, NULL, NULL);
    return ENTERO;
}

"'".*"'"		{    
    yylval.str_val = yytext; 
    printf("Token CADENA(%s)\n", yylval.str_val) ;
    sprintf(longitudCadena,"%d", strlen(yytext));
    insertarEnTablaDeSimbolos(&lista, NULL, yytext, NULL, longitudCadena);
    return STRING;
}

{DIGITO}+"."{DIGITO}* { 
    yylval.val = atof(yytext);
    printf("Token FLOAT(%f)\n", yylval.val) ; 
    insertarEnTablaDeSimbolos(&lista, NULL, yytext, NULL,  NULL);
    return FLOAT;
}

"\n"      		
"\t"		

%%

