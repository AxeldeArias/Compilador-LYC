include number.asm
include macros2.asm

.MODEL LARGE ;Modelo de Memoria
.386 ;Tipo de Procesador
.STACK 200h ;Bytes en el Stack

.DATA
	@const_string_0	db	"Ingrese un digito del uno al veinte:", "$"
	n	db	?, "$"
	@cantOp_n0	dd	?
	_1	dd	?
	_2	dd	2.0
	_3	dd	3.0
	resu	dd	?
	@const_string_1	db	"El resultado es: ", "$"
	@aux3	dd	?
	@aux4	dd	?
	@aux8	dd	?
	@aux9	dd	?
	@aux13	dd	?


.CODE

start:
	MOV EAX,@DATA
	MOV DS,EAX
	MOV ES,EAX

	DisplayString @const_string_0
	getString n
	FLD @cantOp_n0
	FSTP n

	FLD _1
	FSTP @aux3

	FLD @cantOp_n0
	FLD _1
	FXCH
	FSUB
	FSTP @aux4
	FFREE

	FLD @cantOp_n0
	FSTP @aux4

	FLD @cantOp_n0
	FLD _1
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB fintake_n0
	FLD _2
	FLD @aux3
	FADD
	FSTP @aux8
	FFREE

	FLD @cantOp_n0
	FLD _1
	FXCH
	FSUB
	FSTP @aux9
	FFREE

	FLD @cantOp_n0
	FSTP @aux9

	FLD @cantOp_n0
	FLD _1
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB fintake_n0
	FLD _3
	FLD @aux8
	FADD
	FSTP @aux13
	FFREE

fintake_n0:
	FLD resu
	FSTP @aux13

	DisplayString @const_string_1
	DisplayString resu

	MOV EAX, 4C00h
	INT 21h

	END start