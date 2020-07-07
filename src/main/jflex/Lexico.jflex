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
CADENA = ({CARACTERES_ESPECIALES}|{CARACTERES_NO_PERMITIDOS}|{LETRA}|" "|"\t")

CONST_INT = {DIGITO}+
CONST_FLOAT =  {DIGITO}+"."{DIGITO}*
CONST_STR = "\""{CADENA}*"\""


ID = {LETRA}({LETRA}|{DIGITO})*

COMENTARIO = "***/"{CADENA}*"/***"


%%


/* 3er Area: Reglas Léxicas */
//--------> Simbolos


<YYINITIAL> "("			{ return new Symbol(Simbolos.pa,yycolumn,yyline,yytext()); }
<YYINITIAL> ")"			{ return new Symbol(Simbolos.pc,yycolumn,yyline,yytext()); }
<YYINITIAL> "["			{ return new Symbol(Simbolos.ca,yycolumn,yyline,yytext()); }
<YYINITIAL> "]"			{ return new Symbol(Simbolos.cc,yycolumn,yyline,yytext()); }
<YYINITIAL> ";"			{ return new Symbol(Simbolos.pyc,yycolumn,yyline,yytext()); }
<YYINITIAL> "+"		    { return new Symbol(Simbolos.mas,yycolumn,yyline,yytext()); }
<YYINITIAL> ","		    { return new Symbol(Simbolos.coma,yycolumn,yyline,yytext()); }
<YYINITIAL> "take"		{ return new Symbol(Simbolos.take,yycolumn,yyline,yytext()); }
<YYINITIAL> "read"		{ return new Symbol(Simbolos.read,yycolumn,yyline,yytext()); }
<YYINITIAL> "write"		{ return new Symbol(Simbolos.write,yycolumn,yyline,yytext()); }
<YYINITIAL> "="         { return new Symbol(Simbolos.asigna,yycolumn,yyline,yytext()); }



<YYINITIAL> {ID} {
System.out.println("ID" + yytext());
    return new Symbol(Simbolos.id,yycolumn,yyline,yytext());
}

<YYINITIAL> {CONST_INT} {
    Integer constInt = Integer.parseInt(yytext());
    if(constInt >= 0 && constInt <= 65535){
        return new Symbol(Simbolos.cte,yycolumn,yyline,yytext());
    }
    new TError(yytext(),yyline,yycolumn,"Error Lexico","La constante Integer esta fuera del limite.");
}

<YYINITIAL> {CONST_STR} {
    String constStr = yytext();
    return new Symbol(Simbolos.cte_s,yycolumn,yyline,yytext());
}




<YYINITIAL> {COMENTARIO} {/*Comentarios, se ignoran*/}


//--------> Simbolos Exp Reg
[\ \t\r\n\f]          {/*Espacios en blanco, se ignoran*/}


//--------> Errores Lexicos
.                   {
                        System.out.println("Error Léxico "+ yytext()+" Linea "+yyline+" Columna "+yycolumn);
                        TError datos = new TError(yytext(),yyline,yycolumn,"Error Lexico","No existe el simbolo en el lenguaje");
                        TablaEL.add(datos);
                        System.exit(0);
                    }
