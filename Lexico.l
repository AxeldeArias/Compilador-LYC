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

void logError(char* msg);

%}
%option noyywrap  
%option yylineno 

DIGITO		    [0-9]
LETRA	        [a-zA-Z]

CARACTERES_ESPECIALES           [\@\$\_\~\\\,\%]
CARACTERES_NO_PERMITIDOS        [#\^\*\(\)\-\+\=\|\/\?\>\<\!\;\.\:\'\"\[\]\{\}]
CADENA          ({CARACTERES_ESPECIALES}|{CARACTERES_NO_PERMITIDOS}|{DIGITO}|{LETRA}|" "|"\t")

CONST_INT       {DIGITO}+
CONST_FLOAT     {DIGITO}+"."{DIGITO}*
CONST_STR       "\""{CADENA}*"\""

ID			    {LETRA}({LETRA}|{DIGITO}|_)*
   
COMENTARIO      "***/"{CADENA}*"/***"

%%
"IF"	    {printf("Token IF\n"); return IF;}
"ELSE"	    {printf("Token ELSE\n"); return ELSE;}
"WHILE"	    {printf("Token WHILE\n"); return WHILE;}
"DEFVAR"    {printf("Token DEFVAR\n"); return DEFVAR;}
"ENDDEF"    {printf("Token ENDVAR\n"); return ENDDEF;}
"DISPLAY"   {printf("Token DISPLAY\n"); return DISPLAY;}
"GET"       {printf("Token GET\n"); return GET;}

"Integer"   {printf("Token Integer\n"); yylval.int_val = atoi( yytext ); return TIPO_INTEGER;}
"Float"     {printf("Token Float\n");yylval.double_val = atof( yytext );return TIPO_FLOAT;}
"String"    {printf("Token String\n");yylval.str_val = strdup( yytext );return TIPO_STRING;}

"="	        {printf("Token =\n"); return OP_ASIG;}

"+"			{printf("Token +\n"); return OP_SUMA;}       
"-"			{printf("Token -\n"); return OP_RESTA;}
"*"			{printf("Token *\n"); return OP_MUL;}       
"/"			{printf("Token /\n"); return OP_DIV;}

"<"         {printf("Token <\n"); return OP_MENOR;}
"<="        {printf("Token <=\n"); return OP_MENOR_IGUAL;}
">"         {printf("Token >\n"); return OP_MAYOR;}
">="        {printf("Token >=\n"); return OP_MAYOR_IGUAL;}
"=="        {printf("Token ==\n"); return OP_IGUAL;}
"!="        {printf("Token !=\n"); return OP_DISTINTO;}

"("			{printf("Token (\n"); return P_A;}   
")"			{printf("Token )\n"); return P_C;}
"{"	        {printf("Token {\n"); return LL_A;}     
"}"	        {printf("Token }\n"); return LL_C;}
","         {printf("Token ,\n"); return COMA;}
";"			{printf("Token ;\n"); return PUNTO_COMA;}     
":"         {printf("Token :\n"); return DOS_PUNTOS;}

"AND"       {printf("Token AND\n");return OP_AND; }
"OR"        {printf("Token OR\n");return OP_OR; }
"NOT"       {printf("Token NOT\n");return OP_NOT; }

<<EOF>>     {printf("Token EOF\n");recorrerLista(&lista); yyterminate();}

{ID}			{ 
    yylval.str_val = strdup(yytext);
    printf("Token ID(%s)\n", yylval.str_val);
    return ID;
}

{CONST_INT}		{
    if(atoi(yytext) >= 0 && atoi(yytext) <= 65535){
        yylval.int_val = atoi(yytext);
        printf("Token ENTERO(%d)\n",yylval.int_val) ; 
        //insertarEnTablaDeSimbolos(&lista, NULL, yytext, NULL, NULL);
        return CONST_INT;
    }
    logError("La constante Integer esta fuera del limite. \n");
}

{CONST_FLOAT} {
    if(atof(yytext) >= 0 && atof(yytext) <= 2147483647){
        yylval.double_val = atof(yytext);
        printf("Token FLOAT(%f)\n", yylval.double_val) ; 
        //insertarEnTablaDeSimbolos(&lista, NULL, yytext, NULL,  NULL);
        return CONST_FLOAT;
    }
    logError("La constante Float esta fuera del limite. \n");
}

{CONST_STR}		{
    if(yyleng <= 32){
        yylval.str_val = strdup(yytext);
        printf("Token CADENA(%s)\n", yylval.str_val) ;
        //sprintf(longitudCadena,"%d", yyleng);
        //insertarEnTablaDeSimbolos(&lista, NULL, yytext, NULL, longitudCadena);
        return CONST_STR;
    }
        logError("La constante String esta fuera del limite. \n");
}

{COMENTARIO}


"\n"      		
"\t"		

%%

void logError(char* msg){
    printf("Lexical Error %s\n", msg);
    system("Pause");
    exit(1);
}