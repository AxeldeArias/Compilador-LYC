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


ID = {LETRA}({LETRA}|{DIGITO}|_)*
   
COMENTARIO = "***/"{CADENA}*"/***"


%%


/* 3er Area: Reglas Léxicas */
//--------> Simbolos  


<YYINITIAL> "="	        {/*System.out.println("Token.*");*/ return new Symbol(Simbolos.OP_ASIG,yycolumn,yyline,yytext());}
<YYINITIAL> "+"			{/*System.out.println("Token.*");*/ return new Symbol(Simbolos.OP_SUMA,yycolumn,yyline,yytext());}
<YYINITIAL> "*"			{/*System.out.println("Token.*");*/ return new Symbol(Simbolos.OP_MUL,yycolumn,yyline,yytext());}



<YYINITIAL> {ID} {
    /*System.out.println("Token.*");*/
    return new Symbol(Simbolos.ID,yycolumn,yyline,yytext());
}

<YYINITIAL> {CONST_INT} {
    Integer constInt = Integer.parseInt(yytext());
    if(constInt >= 0 && constInt <= 65535){
        /*System.out.println("Token.*");*/
        return new Symbol(Simbolos.CONST_INT,yycolumn,yyline,yytext());
    }
    new TError(yytext(),yyline,yycolumn,"Error Lexico","La constante Integer esta fuera del limite.");
}

<YYINITIAL> {CONST_FLOAT} {
    Double constFloat = Double.parseDouble(yytext());
    if( constFloat >= 0 && constFloat <= 2147483647){
        /*System.out.println("Token.*");*/
        return new Symbol(Simbolos.CONST_FLOAT,yycolumn,yyline,yytext());
    }
    new TError(yytext(),yyline,yycolumn,"Error Lexico","La constante Float esta fuera del limite.");
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
