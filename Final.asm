include number.asm
include macros2.asm

.MODEL LARGE ;Modelo de Memoria
.386 ;Tipo de Procesador
.STACK 200h ;Bytes en el Stack

.DATA
	"Ingrese un digito del uno al veinte:"	dd	?
	n	dd	?
	@cantOp_n0	dd	?
	_1	dd	?
	_2	dd	2.0
	_3	dd	3.0
	resu	dd	?
	"El resultado es: "	dd	?
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

WRITE
READ
	FLD @cantOp_n0
	FSTP n

	FLD _1
	FSTP @aux3

	FLD @cantOp_n0
	FLD _1
	FSUB
	FSTP @aux4
	FFREE

	FLD @cantOp_n0
	FSTP @aux4

CMP
JB
	FLD _2
	FLD @aux3
	FADD
	FSTP @aux8
	FFREE

	FLD @cantOp_n0
	FLD _1
	FSUB
	FSTP @aux9
	FFREE

	FLD @cantOp_n0
	FSTP @aux9

CMP
JB
	FLD _3
	FLD @aux8
	FADD
	FSTP @aux13
	FFREE

ETIQ
	FLD resu
	FSTP @auxnull

ETIQ
	FLD resu
	FSTP @aux13

WRITE
WRITE

	MOV EAX, 4C00h
	INT 21h

	END start