#include <avr/io.h>
#include <util/delay.h>
#include <avr/pgmspace.h>
#include <avr/interrupt.h>
#include <math.h>
#include <stdlib.h>
#include "lcd8.h"
#include "DS18B20.h"
#define             F_CPU         8000000L
#define             BAUD           4800
#define             UBRRcalc    (F_CPU/(BAUD*16L)-1)
#define	BUF_SIZE         4
#define	BUF_MASK       (BUF_SIZE-1)

OneWire OW1={&DDRA, &PINA, &PORTA, PA1};
OneWire OW2={&DDRD, &PIND, &PORTD, PD7};
char time=0, address = 125, u=0, k=8;
unsigned char  BufferOUT[BUF_SIZE],  StartBufOUT = 0, EndBufOUT = 0, Memory[4];
volatile unsigned char  waitread = 0, write = 0;

ISR(TIMER1_COMPA_vect)
{
unsigned int tempHB, tempLB,temp;
unsigned char tempDigital,tempDecimal, minus = 0;
char Sbuf[4];
LCD_WriteCommand (0x01); // clear LCD memory
_delay_ms (10);


if(OneWireReset(OW1))
{ 
OneWireWriteByte(OW1, SKIP_ROM);
OneWireWriteByte(OW1, READ_SCRATCHPAD);

tempLB = (unsigned int)OneWireReadByte(OW1);
tempHB = (unsigned int)OneWireReadByte(OW1);
Memory[0]=(unsigned char) tempLB;
Memory[1]=(unsigned char) tempHB;

temp = (tempLB)|(tempHB<<8);
if(temp&0x8000)
{
temp = ~temp + 1;
minus = 1;
}
LCD_GoToYX(0, 1);
if(minus) LCD_WriteLetter('-');
else LCD_WriteLetter('+');
tempDigital = temp >> 4;
tempDecimal = temp & 0xF;
tempDecimal = (tempDecimal<<1) + (tempDecimal<<3);
tempDecimal = (tempDecimal>>4);
LCD_WriteStr( utoa(tempDigital,Sbuf,10) );
LCD_WriteLetter('.');
LCD_WriteStr( utoa(tempDecimal,Sbuf,10) );
LCD_WriteLetter('*');
LCD_WriteLetter('C');


}
if(OneWireReset(OW1) ) 
{

OneWireWriteByte(OW1, SKIP_ROM);

OneWireWriteByte(OW1, CONVERT_TEMP);
}
else
{
LCD_GoToYX(1,1);
LCD_WriteStr("1-Ware = Error");
}


minus = 0;

if(OneWireReset(OW2))
{ 
OneWireWriteByte(OW2, SKIP_ROM);
OneWireWriteByte(OW2, READ_SCRATCHPAD);
tempLB = (unsigned int)OneWireReadByte(OW2);
tempHB = (unsigned int)OneWireReadByte(OW2);
Memory[2]=(unsigned char) tempLB;
Memory[3]=(unsigned char) tempHB;
temp = (tempLB)|(tempHB<<8);
if(temp&0x8000)
{
temp = ~temp + 1;
minus = 1;
}
LCD_GoToYX(1, 1);
if(minus) LCD_WriteLetter('-');
else LCD_WriteLetter('+');
tempDigital = temp >> 4;
tempDecimal = temp & 0xF;
tempDecimal = (tempDecimal<<1) + (tempDecimal<<3);
tempDecimal = (tempDecimal>>4);
LCD_WriteStr( utoa(tempDigital,Sbuf,10) );
LCD_WriteLetter('.');
LCD_WriteStr( utoa(tempDecimal,Sbuf,10) );
LCD_WriteLetter('*');
LCD_WriteLetter('C');


}
if(OneWireReset(OW2) ) 
{

OneWireWriteByte(OW2, SKIP_ROM);

OneWireWriteByte(OW2, CONVERT_TEMP);
}
else
{
LCD_GoToYX(1,1);
LCD_WriteStr("1-Ware = Error");
}

}
void WriteBufOUT(unsigned char value)
{	
	if (write != 1)
	{
           	 	BufferOUT[EndBufOUT++] = value;
            		EndBufOUT &= BUF_MASK;
	}
}



ISR(USART_RXC_vect)                   
{ 
             if (UDR == address)
			 {
			 	WriteBufOUT(Memory[0]);
				WriteBufOUT(Memory[1]);
				WriteBufOUT(Memory[2]);
				WriteBufOUT(Memory[3]);
			 	UCSRA &= ~(1<<MPCM);
			 	UCSRB |= 1<<UDRIE;
				write = 1;
			 }                         
}

ISR(USART_TXC_vect )                  
{
if( StartBufOUT == EndBufOUT )
PORTD &= ~(1<<PD2);
 }             

ISR(USART_UDRE_vect )               
{
             PORTD |= 1<<PD2;       
             asm("nop");
             UDR = BufferOUT[StartBufOUT++];
             StartBufOUT &= BUF_MASK;
             if( StartBufOUT == EndBufOUT )
	{
              UCSRB &= ~(1<<UDRIE);
	          UCSRA |= (1<<MPCM);
	          write=0;
	 }
}

//---------------------------------------------------------------------------
int main()
{
Initializer();

if(OneWireReset(OW1) )
{
OneWireWriteByte(OW1, SKIP_ROM);
OneWireWriteByte(OW1, CONVERT_TEMP);
}
else
{
LCD_GoToYX(1,1);
LCD_WriteStr("1-Ware = Error");
}
_delay_ms(1000);
sei();
if(OneWireReset(OW2) )
{
OneWireWriteByte(OW2, SKIP_ROM);
OneWireWriteByte(OW2, CONVERT_TEMP);
}
else
{
LCD_GoToYX(1,1);
LCD_WriteStr("1-Ware = Error");
}
_delay_ms(1000);
sei();
while (1)
{ }
}
//---------------------------------------------------------------------------
void Initializer() 
{

DDRA = 0xFF;
PORTA = 0x00;

port_ini();
lcd_ini();
OCR1A = 0x7A11;
TCCR1A = 0x00;
TCCR1B = 1<<WGM12 | 1<<CS01 | 1<<CS00;

TIMSK |= 1<<OCIE1A;

OneWireInit(OW1);
OneWireInit(OW2);
}
