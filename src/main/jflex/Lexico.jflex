//-------> Paquetes e importaciones
package Analizadores;
import java_cup.runtime.*;
import java.util.LinkedList;
import java.util.List;
%%
%{
    //Código de usuario en sintaxis java
    public static List<TError> TablaEL = new LinkedList<TError>();
%}


//--------> Directivas
%public
%class AnalizadorLexico
%cupsym Simbolos
%cup
%column
%line  
%ignorecase
%unicode 


//--------> Expresiones Regulares
DIGITO = [0-9]
LETRA = [a-zA-Z]

CARACTERES_ESPECIALES = [\@\$\_\~\\\,\%]
CARACTERES_NO_PERMITIDOS = [#\^\*\(\)\-\+\=\|\/\?\>\<\!\;\.\:\'\"\[\]\{\}]
CADENA = ({CARACTERES_ESPECIALES}|{CARACTERES_NO_PERMITIDOS}|{DIGITO}|{LETRA}|" "|"\t")

CONST_INT = {DIGITO}+
CONST_FLOAT =  {DIGITO}+"."{DIGITO}*
CONST_STR = "\""{CADENA}*"\""

ID = {LETRA}({LETRA}|{DIGITO}|_)*
   
COMENTARIO = "***/"{CADENA}*"/***"


%%


/* 3er Area: Reglas Léxicas */
//--------> Simbolos  
<YYINITIAL> "IF"	    {System.out.println("Token IF"); return new Symbol(Simbolos.IF,yycolumn,yyline,yytext());}
<YYINITIAL> "ELSE"	    {System.out.println("Token ELSE"); return new Symbol(Simbolos.ELSE,yycolumn,yyline,yytext());}
<YYINITIAL> "WHILE"	    {System.out.println("Token WHILE"); return new Symbol(Simbolos.WHILE,yycolumn,yyline,yytext());}
<YYINITIAL> "DEFVAR"    {System.out.println("Token DEFVAR"); return new Symbol(Simbolos.DEFVAR,yycolumn,yyline,yytext());}
<YYINITIAL> "ENDDEF"    {System.out.println("Token ENDVAR"); return new Symbol(Simbolos.ENDDEF,yycolumn,yyline,yytext());}
<YYINITIAL> "DISPLAY"   {System.out.println("Token DISPLAY"); return new Symbol(Simbolos.DISPLAY,yycolumn,yyline,yytext());}
<YYINITIAL> "GET"       {System.out.println("Token GET"); return new Symbol(Simbolos.GET,yycolumn,yyline,yytext());}

<YYINITIAL> "Integer"   {
                            System.out.println("Token Integer");
                            return new Symbol(Simbolos.TIPO_INTEGER,yycolumn,yyline,yytext());
                        }
<YYINITIAL> "Float"     {
                            System.out.println("Token Float");
                            return new Symbol(Simbolos.TIPO_FLOAT,yycolumn,yyline,yytext());
                        }

<YYINITIAL> "="	        {System.out.println("Token ="); return new Symbol(Simbolos.OP_ASIG,yycolumn,yyline,yytext());}
<YYINITIAL> "+="        {System.out.println("Token +="); return new Symbol(Simbolos.OP_ASIG_SUM,yycolumn,yyline,yytext());}    
<YYINITIAL> "-="        {System.out.println("Token -="); return new Symbol(Simbolos.OP_ASIG_RES,yycolumn,yyline,yytext());}
<YYINITIAL> "*="        {System.out.println("Token *="); return new Symbol(Simbolos.OP_ASIG_MUL,yycolumn,yyline,yytext());}                                   
<YYINITIAL> "/="        {System.out.println("Token /="); return new Symbol(Simbolos.OP_ASIG_DIV,yycolumn,yyline,yytext());}
<YYINITIAL> "+"			{System.out.println("Token +"); return new Symbol(Simbolos.OP_SUMA,yycolumn,yyline,yytext());}       
<YYINITIAL> "-"			{System.out.println("Token -"); return new Symbol(Simbolos.OP_RESTA,yycolumn,yyline,yytext());}
<YYINITIAL> "*"			{System.out.println("Token *"); return new Symbol(Simbolos.OP_MUL,yycolumn,yyline,yytext());}       
<YYINITIAL> "/"			{System.out.println("Token /"); return new Symbol(Simbolos.OP_DIV,yycolumn,yyline,yytext());}

<YYINITIAL> "<"         {System.out.println("Token <"); return new Symbol(Simbolos.OP_MENOR,yycolumn,yyline,yytext());}
<YYINITIAL> "<="        {System.out.println("Token <="); return new Symbol(Simbolos.OP_MENOR_IGUAL,yycolumn,yyline,yytext());}
<YYINITIAL> ">"         {System.out.println("Token >"); return new Symbol(Simbolos.OP_MAYOR,yycolumn,yyline,yytext());}
<YYINITIAL> ">="        {System.out.println("Token >="); return new Symbol(Simbolos.OP_MAYOR_IGUAL,yycolumn,yyline,yytext());}
<YYINITIAL> "=="        {System.out.println("Token =="); return new Symbol(Simbolos.OP_IGUAL,yycolumn,yyline,yytext());}
<YYINITIAL> "!="        {System.out.println("Token !="); return new Symbol(Simbolos.OP_DISTINTO,yycolumn,yyline,yytext());}

<YYINITIAL> "("			{System.out.println("Token ("); return new Symbol(Simbolos.P_A,yycolumn,yyline,yytext());}   
<YYINITIAL> ")"			{System.out.println("Token )"); return new Symbol(Simbolos.P_C,yycolumn,yyline,yytext());}
<YYINITIAL> "{"	        {System.out.println("Token {"); return new Symbol(Simbolos.LL_A,yycolumn,yyline,yytext());}     
<YYINITIAL> "}"	        {System.out.println("Token }"); return new Symbol(Simbolos.LL_C,yycolumn,yyline,yytext());}
<YYINITIAL> ","         {System.out.println("Token ,"); return new Symbol(Simbolos.COMA,yycolumn,yyline,yytext());}
<YYINITIAL> ";"			{System.out.println("Token ;"); return new Symbol(Simbolos.PUNTO_COMA,yycolumn,yyline,yytext());}     
<YYINITIAL> ":"         {System.out.println("Token :"); return new Symbol(Simbolos.DOS_PUNTOS,yycolumn,yyline,yytext());}

<YYINITIAL> "AND"       {System.out.println("Token AND");return new Symbol(Simbolos.OP_AND,yycolumn,yyline,yytext()); }
<YYINITIAL> "OR"        {System.out.println("Token OR");return new Symbol(Simbolos.OP_OR,yycolumn,yyline,yytext()); }
<YYINITIAL> "NOT"       {System.out.println("Token NOT");return new Symbol(Simbolos.OP_NOT,yycolumn,yyline,yytext()); }


<YYINITIAL> {ID} {
    System.out.println("Token ID("+yytext()+")");
    return new Symbol(Simbolos.ID,yycolumn,yyline,yytext());
}

<YYINITIAL> {CONST_INT} {
    Integer constInt = Integer.parseInt(yytext());
    if(constInt >= 0 && constInt <= 65535){
        System.out.println("Token ENTERO("+constInt+")");
        return new Symbol(Simbolos.CONST_INT,yycolumn,yyline,yytext());
    }
    new TError(yytext(),yyline,yycolumn,"Error Lexico","La constante Integer esta fuera del limite.");
}

<YYINITIAL> {CONST_FLOAT} {
    Double constFloat = Double.parseDouble(yytext());
    if( constFloat >= 0 && constFloat <= 2147483647){
        System.out.println("Token CONST_FLOAT("+constFloat+")");
        return new Symbol(Simbolos.CONST_FLOAT,yycolumn,yyline,yytext());
    }
    new TError(yytext(),yyline,yycolumn,"Error Lexico","La constante Float esta fuera del limite.");
}

<YYINITIAL> {CONST_STR} {
    String constStr = yytext();
    if(constStr.length() <= 32){
        System.out.println("Token CADENA("+constStr+")");
        return new Symbol(Simbolos.CONST_STR,yycolumn,yyline,yytext());
    }
    new TError(yytext(),yyline,yycolumn,"Error Lexico","La constante String esta fuera del limite.");
}


<YYINITIAL> {COMENTARIO} {/*Comentarios, se ignoran*/}


//--------> Simbolos Exp Reg
[\ \t\r\n\f]          {/*Espacios en blanco, se ignoran*/}


//--------> Errores Lexicos
.                   {
                        System.out.println("Error Léxico"+ yytext()+" Linea"+yyline+" Columna"+yycolumn);
                        TError datos = new TError(yytext(),yyline,yycolumn,"Error Lexico","No existe el simbolo en el lenguaje");
                        TablaEL.add(datos);
                        System.exit(0);
                    }
