.include "m2560def.inc" 
.def temp = r16 
.def razr1 = r17
.def razr2 = r18 
.def razr3 = r19

.dseg 
.cseg 
.org 0 
rjmp Reset 

Reset: 
ldi temp, high(RAMEND)
out sph, temp
ldi temp, low(RAMEND) 
out spl, temp 
ldi temp, 0xff
out DDRA, temp 
ldi temp, 0x00 
out DDRD, temp 
ldi temp, 0xff 
out PORTD, temp
Proga: 

sbic PIND, 1 
rjmp Proga 

ldi temp, 0b10000000 
out PORTA, temp 
rcall Delay

ldi temp, 0b01000000
out PORTA, temp 
rcall Delay

ldi temp, 0b00100000
out PORTA, temp 
rcall Delay

ldi temp, 0b00010000 
out PORTA, temp 
rcall Delay 

ldi temp, 0b00001000 
out PORTA, temp
rcall Delay

ldi temp, 0b00000100
out PORTA, temp
rcall Delay

ldi temp, 0b00000010 
out PORTA, temp 
rcall Delay

ldi temp, 0b00000001
out PORTA, temp 
rcall Delay

rjmp Proga 

Delay: 
ldi razr1, 0x80
ldi razr2, 0xC0
ldi razr3, 0x14 

PDelay: 
subi razr1, 1
sbci razr2, 0
sbci razr3, 0
brne PDelay
ret

