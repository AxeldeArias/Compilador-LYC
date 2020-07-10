include number.asm
include macros2.asm

.MODEL LARGE ;Modelo de Memoria
.386 ;Tipo de Procesador
.STACK 200h ;Bytes en el Stack

.DATA
	@const_string_0	db	"Ingrese un digito del uno al veinte: ", "$"
	n	dd	?
	resu	dd	?
	@cantidadDeOperaciones_n0	dd	?
	_1	dd	1
	_0	dd	0
	_error_no_positivo	db	"ERROR- Solo puede ingresar una cantidad positiva en la sentencia take.", "$"
	_2	dd	2
	_3	dd	3
	_4	dd	4
	_5	dd	5
	_6	dd	6
	_7	dd	7
	_8	dd	8
	_9	dd	9
	_10	dd	10
	_11	dd	11
	_12	dd	12
	_13	dd	13
	_14	dd	14
	_15	dd	15
	_16	dd	16
	_17	dd	17
	_18	dd	18
	_19	dd	19
	_20	dd	20
	@const_string_1	db	"El resultado es: ", "$"
	@aux2	dd	?
	@aux10	dd	?
	@aux14	dd	?
	@aux16	dd	?
	@aux20	dd	?
	@aux22	dd	?
	@aux26	dd	?
	@aux28	dd	?
	@aux32	dd	?
	@aux34	dd	?
	@aux38	dd	?
	@aux40	dd	?
	@aux44	dd	?
	@aux46	dd	?
	@aux50	dd	?
	@aux52	dd	?
	@aux56	dd	?
	@aux58	dd	?
	@aux62	dd	?
	@aux64	dd	?
	@aux68	dd	?
	@aux70	dd	?
	@aux74	dd	?
	@aux76	dd	?
	@aux80	dd	?
	@aux82	dd	?
	@aux86	dd	?
	@aux88	dd	?
	@aux92	dd	?
	@aux94	dd	?
	@aux98	dd	?
	@aux100	dd	?
	@aux104	dd	?
	@aux106	dd	?
	@aux110	dd	?
	@aux112	dd	?
	@aux116	dd	?
	@aux118	dd	?
	@aux122	dd	?


.CODE

start:
	MOV EAX,@DATA
	MOV DS,EAX
	MOV ES,EAX

	DisplayString @const_string_0
	GetInteger n
	FLD n
	FLD _1
	FSUB
	FSTP @aux2
	FFREE

	FLD @aux2
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JAE inicio_take_n0
	DisplayString _error_no_positivo
	JMP fin_programa
inicio_take_n0:
	FLD _1
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux10
	FFREE

	FLD @aux10
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _2
	FADD
	FSTP @aux14
	FFREE

	FLD @aux14
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux16
	FFREE

	FLD @aux16
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _3
	FADD
	FSTP @aux20
	FFREE

	FLD @aux20
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux22
	FFREE

	FLD @aux22
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _4
	FADD
	FSTP @aux26
	FFREE

	FLD @aux26
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux28
	FFREE

	FLD @aux28
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _5
	FADD
	FSTP @aux32
	FFREE

	FLD @aux32
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux34
	FFREE

	FLD @aux34
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _6
	FADD
	FSTP @aux38
	FFREE

	FLD @aux38
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux40
	FFREE

	FLD @aux40
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _7
	FADD
	FSTP @aux44
	FFREE

	FLD @aux44
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux46
	FFREE

	FLD @aux46
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _8
	FADD
	FSTP @aux50
	FFREE

	FLD @aux50
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux52
	FFREE

	FLD @aux52
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _9
	FADD
	FSTP @aux56
	FFREE

	FLD @aux56
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux58
	FFREE

	FLD @aux58
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _10
	FADD
	FSTP @aux62
	FFREE

	FLD @aux62
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux64
	FFREE

	FLD @aux64
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _11
	FADD
	FSTP @aux68
	FFREE

	FLD @aux68
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux70
	FFREE

	FLD @aux70
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _12
	FADD
	FSTP @aux74
	FFREE

	FLD @aux74
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux76
	FFREE

	FLD @aux76
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _13
	FADD
	FSTP @aux80
	FFREE

	FLD @aux80
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux82
	FFREE

	FLD @aux82
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _14
	FADD
	FSTP @aux86
	FFREE

	FLD @aux86
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux88
	FFREE

	FLD @aux88
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _15
	FADD
	FSTP @aux92
	FFREE

	FLD @aux92
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux94
	FFREE

	FLD @aux94
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _16
	FADD
	FSTP @aux98
	FFREE

	FLD @aux98
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux100
	FFREE

	FLD @aux100
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _17
	FADD
	FSTP @aux104
	FFREE

	FLD @aux104
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux106
	FFREE

	FLD @aux106
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _18
	FADD
	FSTP @aux110
	FFREE

	FLD @aux110
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux112
	FFREE

	FLD @aux112
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _19
	FADD
	FSTP @aux116
	FFREE

	FLD @aux116
	FSTP resu

	FLD @cantidadDeOperaciones_n0
	FLD _1
	FSUB
	FSTP @aux118
	FFREE

	FLD @aux118
	FSTP @cantidadDeOperaciones_n0

	FLD @cantidadDeOperaciones_n0
	FLD _0
	FXCH
	FCOM
	FSTSW AX
	SAHF
	JB asignacion_take_n0
	FLD resu
	FLD _20
	FADD
	FSTP @aux122
	FFREE

	FLD @aux122
	FSTP resu

asignacion_take_n0:
	DisplayString @const_string_1
	DisplayInteger resu, 1
fin_programa:

	MOV EAX, 4C00h
	INT 21h

	END start