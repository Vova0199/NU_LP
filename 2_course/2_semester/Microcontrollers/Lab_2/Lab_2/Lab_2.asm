.include "m2560def.inc"
.def	_flag2  = r1 ; 0б≥т - пауза =0 (пораховано), =1(ще рахуЇ)
.def    temp =  r16
.def	_count2= r22
.def	_countA= r23
.def	_countBuz= r24
.def    count = r17 




.dseg 						//сегмент даних

.cseg
		.org	$000 
		jmp	RESET 		;Reset Handler
		.org $001C 
		jmp TIM1_COMP ; Timer2 CompareB Handler
		.org $0022  ; SPM Ready Handler
		reti


TIM1_COMP:	
		sbrs	_flag2, 0	
		rjmp	T2end		
		inc		_count2;
		cpi		_count2, 25  ;(t=0.8c)
		breq    ready	
		cpi		_count2, 3
		brne	T2end
		cbi PORTC, 2
		rjmp T2end

ready:	
		clt					;T=0
		bld	_flag2, 0
		out PORTC, _countBuz
		out PORTA, _countA
		clr _count2
		set					;“=1
		bld	_flag2,0		;0б≥т=1 
		lsl	 _countA
		inc count
		cpi count, 9
		breq Obnal
		 

		

T2end:	
		reti
Obnal:
		ldi _countA,  0b00000001
		clr count
		rjmp ready
Reset:
		ldi temp, high(RAMEND)
		out sph, temp
		ldi temp, low(RAMEND)
		out spl, temp
		 
		ldi	_count2, 0xff 		 //подаЇмо 1 на вих≥д PORTB
		out	DDRA, _count2 

		ldi _count2, 0x40       //п≥дт€гуванн€ резистора 10кќм
		out PORTB, _count2

		ldi _count2, 0x00 		 //подаЇмо 0 на вх≥д порт C
		out DDRD, _count2

		ldi _count2, 0xff
		out DDRC, _count2

		;_______________________________________________________________________________
; 25msec, Prescaler=1024, OCR2=0xFD
		ldi	temp, (1<<WGM22)|(1<<CS22)|(1<<CS21)|(1<<CS20)
		sts	TCCR2B, temp	;OCR2=0xFD
		ldi	temp, 0xFD
		sts	OCR2B, temp	; OCR2=0xFD
		ldi	temp, (1<<OCIE2B)
		sts	TIMSK2, temp	;
		clr	_count2
		clr	_flag2
		clr count
		ldi _countA,  0b00000001 
		ldi _countBuz,0b00000100
		sei	
;______________________________________________________________________________

main:	
		sbic	PINB, 6		; €кщо натиснута кнопка 
		rjmp	PC3end
		set					;“=1
		bld	_flag2, 0		;0б≥т=1 (оч≥куЇмо 0.8 c)
blk:	
		cpi _countA,  0b00000000
		brne blk
		
		rjmp blk
		

PC3end:	
	
		rjmp	main
