include number.asm
include macros2.asm

.MODEL LARGE ;Modelo de Memoria
.386 ;Tipo de Procesador
.STACK 200h ;Bytes en el Stack

.DATA
	AUXa_anio	dd	?
	AUXa_mes	dd	?
	AUXa_dia	dd	?
	a_anio	dd	?
	a_mes	dd	?
	a_dia	dd	?
	cantASumar	dd	?
	AUXcantdiames	dd	?
	_10	dd	10.0
	_2020	dd	2020.0
	_5	dd	5.0
	_31	dd	31.0
	_0	dd	0.0
	_2	dd	2.0
	_4	dd	4.0
	_400	dd	400.0
	_29	dd	29.0
	_28	dd	28.0
	_1	dd	1.0
	_3	dd	3.0
	_7	dd	7.0
	_8	dd	8.0
	_9	dd	9.0
	_30	dd	30.0
	_6	dd	6.0
	_12	dd	12.0

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

	FLD _10
	FSTP cantASumar

	FLD _2020
	FSTP a_anio

	FLD _5
	FSTP a_mes

	FLD _31
	FSTP a_dia

	FLD _0
	FSTP AUXcantdiames

	FLD a_anio
	FSTP AUXa_anio

	FLD a_mes
	FSTP AUXa_mes

	FLD a_dia
	FSTP AUXa_dia

etiqueta0:
	FLD cantASumar
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JBE etiqueta1

	FLD AUXa_mes
	FLD _2
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JNE etiqueta2

	FLD _4
	FLD AUXa_anio
	FMUL
	FSTP @aux1
	FFREE

	FLD @aux1
	FLD _4
	FDIV
	FSTP @aux2
	FFREE

	FLD AUXa_anio
	FLD @aux2
	FSUB
	FSTP @aux3
	FFREE

	FLD @aux3
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JE etiqueta3

	FLD _400
	FLD AUXa_anio
	FMUL
	FSTP @aux4
	FFREE

	FLD @aux4
	FLD _400
	FDIV
	FSTP @aux5
	FFREE

	FLD AUXa_anio
	FLD @aux5
	FSUB
	FSTP @aux6
	FFREE

	FLD @aux6
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JNE etiqueta4

etiqueta3:
	FLD _29
	FSTP AUXcantdiames

	JMP etiqueta6
etiqueta4:
	FLD _28
	FSTP AUXcantdiames

etiqueta6:
	JMP etiqueta9
etiqueta2:
	FLD AUXa_mes
	FLD _1
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JE etiqueta10

	FLD AUXa_mes
	FLD _3
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JNE etiqueta11

etiqueta10:
	FLD _31
	FSTP AUXcantdiames

etiqueta11:
	FLD AUXa_mes
	FLD _5
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JE etiqueta12

	FLD AUXa_mes
	FLD _7
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JNE etiqueta13

etiqueta12:
	FLD _31
	FSTP AUXcantdiames

etiqueta13:
	FLD AUXa_mes
	FLD _5
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JE etiqueta14

	FLD AUXa_mes
	FLD _7
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JNE etiqueta15

etiqueta14:
	FLD _31
	FSTP AUXcantdiames

etiqueta15:
	FLD AUXa_mes
	FLD _8
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JE etiqueta16

	FLD AUXa_mes
	FLD _10
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JNE etiqueta17

etiqueta16:
	FLD _31
	FSTP AUXcantdiames

etiqueta17:
	FLD AUXa_mes
	FLD _4
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JE etiqueta18

	FLD AUXa_mes
	FLD _9
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JNE etiqueta19

etiqueta18:
	FLD _30
	FSTP AUXcantdiames

etiqueta19:
	FLD AUXa_mes
	FLD _10
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JE etiqueta20

	FLD AUXa_mes
	FLD _6
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JNE etiqueta21

etiqueta20:
	FLD _30
	FSTP AUXcantdiames

etiqueta21:
etiqueta9:
	FLD AUXa_dia
	FLD AUXcantdiames
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JAE etiqueta23

	FLD AUXa_dia
	FLD _1
	FADD
	FSTP @aux7
	FFREE

	FLD @aux7
	FSTP AUXa_dia

	JMP etiqueta25
etiqueta23:
	FLD AUXa_mes
	FLD _12
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JE etiqueta26

	FLD AUXa_mes
	FLD _1
	FADD
	FSTP @aux8
	FFREE

	FLD @aux8
	FSTP AUXa_mes

	FLD _1
	FSTP AUXa_dia

	JMP etiqueta28
etiqueta26:
	FLD _1
	FSTP AUXa_dia

	FLD _1
	FSTP AUXa_mes

	FLD AUXa_dia
	FLD _1
	FADD
	FSTP @aux9
	FFREE

	FLD @aux9
	FSTP AUXa_dia

	FLD AUXa_anio
	FLD _1
	FADD
	FSTP @aux10
	FFREE

	FLD @aux10
	FSTP AUXa_anio

etiqueta28:
etiqueta25:
	FLD cantASumar
	FLD _1
	FSUB
	FSTP @aux11
	FFREE

	FLD @aux11
	FSTP cantASumar

	JMP etiqueta0
etiqueta1:
	FLD AUXa_anio
	FSTP a_anio

	FLD AUXa_mes
	FSTP a_mes

	FLD AUXa_dia
	FSTP a_dia

	DisplayFloat a_anio,1
	newline 1

	DisplayFloat a_mes,1
	newline 1

	DisplayFloat a_dia,1
	newline 1


	MOV EAX, 4C00h
	INT 21h

	END start