.include "m2560def.inc"
.def	_flag2  = r1 
.def    temp =  r16
.def	_count2= r22
.def	_countA= r23
.def	_countBuz= r24
.def    count = r17 




.dseg 					

.cseg
		.org	$000 
		jmp	RESET 	
		.org $001C 
		jmp TIM1_COMP
		.org $0022 
		reti


TIM1_COMP:	
		sbrs	_flag2, 0	
		rjmp	T2end		
		inc		_count2;
		cpi		_count2, 25
		breq    ready	
		cpi		_count2, 3
		brne	T2end
		cbi PORTC, 2
		rjmp T2end

ready:	
		clt				
		bld	_flag2, 0
		out PORTC, _countBuz
		out PORTA, _countA
		clr _count2
		set				
		bld	_flag2,0		
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
		 
		ldi	_count2, 0xff 		 
		out	DDRA, _count2 

		ldi _count2, 0x40       
		out PORTB, _count2

		ldi _count2, 0x00 		 
		out DDRD, _count2

		ldi _count2, 0xff
		out DDRC, _count2

		;_______________________________________________________________________________

		ldi	temp, (1<<WGM13)| (1<<WGM12)| (1<<CS12)|(1<<CS11)|(1<<CS10)
		sts	TCCR1B, temp	
		ldi	temp, 0xFD
		sts	OCR1BH, temp	
		ldi	temp, (1<<OCIE1B)
		sts	TIMSK1, temp	
		clr	_count2
		clr	_flag2
		clr count
		ldi _countA,  0b00000001 
		ldi _countBuz,0b00000100
		sei	
;______________________________________________________________________________

main:	
		sbic	PINB, 6	
		rjmp	PC3end
		set					
		bld	_flag2, 0		
blk:	
		cpi _countA,  0b00000000
		brne blk
		
		rjmp blk
		

PC3end:	
	
		rjmp	main
