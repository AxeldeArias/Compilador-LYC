include number.asm
include macros2.asm

.MODEL LARGE ;Modelo de Memoria
.386 ;Tipo de Procesador
.STACK 200h ;Bytes en el Stack

.DATA
	a	dd	?
	b	dd	?
	z	dd	?
	aux	dd	?
	aux2	dd	?
	c	dd	?
	_3	dd	3.0
	_10	dd	10.0
	_5	dd	5.0
	_2	dd	2.0
	_0	dd	0.0
	_1	dd	1.0
	_4	dd	4.0
	_6	dd	6.0
	_8	dd	8.0
	_11	dd	11.0

	@aux1	dd	?
	@aux2	dd	?
	@aux3	dd	?
	@aux4	dd	?
	@aux5	dd	?
	@aux6	dd	?
	@aux7	dd	?
	@aux8	dd	?
	@aux9	dd	?
	@aux10	dd	?
	@aux11	dd	?


.CODE

start:
	MOV EAX,@DATA
	MOV DS,EAX
	MOV ES,EAX

	FLD _3
	FSTP a

	FLD _10
	FSTP b

	FLD _5
	FSTP c

	FLD _2
	FSTP z

	FLD _0
	FSTP aux

	FLD _0
	FSTP aux2

etiqueta0:
	FLD aux
	FLD _3
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JAE etiqueta1

	FLD _1
	FLD _1
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JNE etiqueta1

	FLD a
	FLD _3
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JNE etiqueta2

	FLD a
	FLD _4
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JAE etiqueta2

	FLD _1
	FLD _2
	FADD
	FSTP @aux1
	FFREE

	FLD @aux1
	FSTP a

	FLD b
	FLD _6
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JBE etiqueta3

	FLD _6
	FLD _2
	FSUB
	FSTP @aux2
	FFREE

	FLD @aux2
	FSTP b

	JMP etiqueta4
etiqueta3:
	FLD _8
	FSTP z

	FLD z
	FLD b
	FADD
	FSTP @aux3
	FFREE

	FLD @aux3
	FLD _8
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JAE etiqueta5

	FLD _2
	FLD _2
	FMUL
	FSTP @aux4
	FFREE

	FLD _3
	FLD @aux4
	FADD
	FSTP @aux5
	FFREE

	FLD @aux5
	FLD b
	FADD
	FSTP @aux6
	FFREE

	FLD @aux6
	FSTP a

etiqueta5:
etiqueta4:
etiqueta6:
	FLD aux2
	FLD _1
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JAE etiqueta7

	FLD aux2
	FLD _1
	FADD
	FSTP @aux7
	FFREE

	FLD @aux7
	FSTP aux2

	JMP etiqueta6
etiqueta7:
	FLD z
	FLD _1
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JNE etiqueta8

	FLD z
	FLD _2
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JA etiqueta9

etiqueta8:
	FLD z
	FLD _3
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JAE etiqueta10

	FLD _11
	FLD _5
	FMUL
	FSTP @aux8
	FFREE

	FLD @aux8
	FSTP z

	JMP etiqueta11
etiqueta10:
	FLD a
	FLD b
	FADD
	FSTP @aux9
	FFREE

	FLD @aux9
	FSTP z

etiqueta11:
etiqueta9:
	JMP etiqueta14
etiqueta2:
	FLD z
	FLD _1
	FXCH
	FDIV
	FSTP @aux10
	FFREE

	FLD @aux10
	FSTP a

	FLD a
	FLD z
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JBE etiqueta15

	FLD _1
	FSTP b

etiqueta15:
etiqueta14:
	FLD aux
	FLD _1
	FADD
	FSTP @aux11
	FFREE

	FLD @aux11
	FSTP aux

	JMP etiqueta0
etiqueta1:
	DisplayFloat a,1
	newline 1

	DisplayFloat b,1
	newline 1

	DisplayFloat c,1
	newline 1

	DisplayFloat z,1
	newline 1

	DisplayFloat aux,1
	newline 1

	DisplayFloat aux2,1
	newline 1


	MOV EAX, 4C00h
	INT 21h

	END start