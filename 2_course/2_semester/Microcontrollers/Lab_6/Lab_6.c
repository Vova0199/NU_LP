#define	LCDdataPORT	PORTA	// Data Port
#define	LCDdataPIN	PINA
#define	LCDdataDDR	DDRA

#define	LCDcontrolPORT	PORTC	// Control Port
#define	LCDcontrolPIN	PINC
#define	LCDcontrolDDR	DDRC

#define	RS		0
#define	RW		1
#define	E		2
#define	Rst		3
//=============================================================

#include <avr/io.h>
#include <util/delay.h>
#include <avr/pgmspace.h>
//#include <avr/interrupt.h>
#include <stdlib.h> 
#include "LCD12864_8bit.h"

//---------------------------------------------------------------------------
PROGMEM const unsigned char Hello[] ="Hello";
void Initializer();

#define nop() {asm("nop");}
 
void right (void);
void left (void);
 

int x, j, m=10;

void delay(int t)
{
for (x=0; x<t ;x++)
nop (); 
 
}
 
void long_delay(int p)
{
for (j=0; j<p; j++)
	{
	delay(300);
		}
}

void go (void)
{ if ((PINB&0x01)==0x00) {
	right();
	LCD_GotoYX(3,1);
	LCD_WriteStr("Вправо");
} 
  if ((PINB&0x01)==0x01) {
  left();
  LCD_GotoYX(3,1);
	LCD_WriteStr("Вліво");
	} 
}

void right (void)
{
if (((PINB&0x08)==0x00) && (m<350)) {m=m+10;}
if (((PINB&0x10)==0x00) && (m>2)) {m=m-10;}

PORTB |= _BV(PB1); 
PORTB &= ~_BV(PB2);

PORTD = 0b10001000;
long_delay(m);

PORTD = 0b00100010;
long_delay(m);

PORTD = 0b01000100;
long_delay(m);
 
PORTD = 0b00010001;
long_delay(m);

}
 
void Initializer()	//Ініціалізація заліза
{
	//Ініціалізація портів
	//Порт A на вхід з підт. резистр. 
	DDRA  = 0x00;
	PORTA = 0xFF;
	//Порт B на вхід з підт. резистр.
	DDRB  = 0x00;
	PORTB = 0xFF;
	//Порт С на вхід з підт. резистр.
	DDRC  = 0x00;
	PORTC = 0xFF;
	//Порт D на вхід з підт. резистр.
	DDRD  = 0x00;
	PORTD = 0xFF;

	//Ініціалізація LCD
	//InitLCD_12864(TextMode);
	InitLCD_12864(GraphMode);
}

void left(void)
{
if (((PINB&0x08)==0x00) && (m<350)) {m++;}
if (((PINB&0x10)==0x00) && (m>2)) {m--;}

PORTB |= _BV(PB2);
PORTB &= ~_BV(PB1);

PORTD = 0b00010001;
long_delay(m);
 
PORTD = 0b01000100;
long_delay(m);

PORTD = 0b00100010;
long_delay(m);
 
PORTD = 0b10001000;
long_delay(m);

}
 
int main(void)
{
DDRD = 0xFF;
PORTD = 0x00;

PORTB=0x39;
DDRB=0x46;
	Initializer();


while(1)
{
if ((PINB&0x20)==0x00) {go ();}
}
}
