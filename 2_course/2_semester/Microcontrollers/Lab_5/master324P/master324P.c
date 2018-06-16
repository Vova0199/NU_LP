#define F_CPU 9216000L 
#define BAUD 9600 
#define UBRRcalc (F_CPU/(BAUD*16L)-1)
#define BUF_SIZE 16 
#define BUF_MASK (BUF_SIZE-1)
#define BUF1_SIZE 16 
#define BUF1_MASK (BUF1_SIZE-1)
//--------------------------------------------------------------------------------------------------------------------------------
#include <avr/io.h>
#include <util/delay.h>
#include <avr/pgmspace.h>
#include <avr/interrupt.h>
#include <math.h> 
#include <stdlib.h> 
//--------------------------------------------------------------------------------------------------------------------------------
unsigned char BufOut[BUF_SIZE], StartBufOut = 0, EndBufOut = 0; 
unsigned char Buf1Out[BUF_SIZE], StartBuf1Out = 0, EndBuf1Out = 0; 
volatile unsigned char  WaitRead = 0, WaitWrite = 0; 
unsigned char Number = 0;
//--------------------------------------------------------------------------------------------------------------------------------
ISR(USART0_RX_vect) 
{ 
	unsigned char Temporary=UDR0;
	WriteBufOut(Temporary); 
}
//--------------------------------------------------------------------------------------------------------------------------------
void WriteBufOut(unsigned char value) 
{
BufOut[EndBufOut++] = value;
    EndBufOut &= BUF_MASK;
    cli();
    if(WaitRead == 0) UCSR1B |= 1<<UDRIE1;    sei();
}
//--------------------------------------------------------------------------------------------------------------------------------
ISR(USART1_UDRE_vect) 
{
	PORTD |= 1<<PD2; 
    UCSR1B |= 1<<TXB81; 
    asm("nop");
    UDR1 = BufOut[StartBufOut++]; 
    StartBufOut &= BUF_MASK;
    if(StartBufOut == EndBufOut || WaitRead == 1) UCSR1B &= ~(1<<UDRIE1); 
}
//--------------------------------------------------------------------------------------------------------------------------------
ISR(USART1_TX_vect) 
{ PORTD &= ~(1<<PD2); } 
//--------------------------------------------------------------------------------------------------------------------------------
ISR(USART1_RX_vect)                
{ 
	unsigned char One;
	One = UDR1;
	WriteBuf1Out(One); 
	Number++;
	if (Number == 4) 
	{
		Number = 0;
	    WaitRead = 0;
	    if(StartBufOut != EndBufOut) UCSR1B |= 1<<UDRIE1; 
	}	
}
//--------------------------------------------------------------------------------------------------------------------------------
void WriteBuf1Out(unsigned char value) 
{
	Buf1Out[EndBuf1Out++] = value;
    EndBuf1Out &= BUF1_MASK;
    cli();
    if(WaitWrite == 0) UCSR0B |= 1<<UDRIE0; 
    sei();
}
//--------------------------------------------------------------------------------------------------------------------------------
ISR(USART0_UDRE_vect) 
{                                                          
    UDR0 = Buf1Out[StartBuf1Out++];
	WaitWrite = 1;
    StartBuf1Out &= BUF1_MASK;
    if(StartBuf1Out == EndBuf1Out  ||  WaitWrite == 1) UCSR0B &= ~(1<<UDRIE0); 
}
//--------------------------------------------------------------------------------------------------------------------------------
ISR(USART0_TX_vect) 
{ WaitWrite = 0; } 
//--------------------------------------------------------------------------------------------------------------------------------
int main()
{
	cli();
	Init(); 
	_delay_ms(3000);
    sei();  
	while(1)
	{	}
}
//--------------------------------------------------------------------------------------------------------------------------------
void Init()      
{
	DDRA  = 0xFF; 
	PORTA = 0x00;
	DDRB  = 0xFF;
	PORTB = 0x00;
	DDRC  = 0xFF;
	PORTC = 0x00;
    DDRD=0b11111010; 
	PORTD=0b00000101;
    UBRR1L = (unsigned char)(UBRRcalc); 
    UBRR1H = (unsigned char)(UBRRcalc>>8);
    UCSR1A = 0; 
    UCSR1C = (1<<UCSZ11) | (1<<UCSZ10) | (1<<USBS1);    
UCSR1B = (1<<UCSZ12) | (1<<RXEN1) | (1<<TXEN1) | (1<<RXCIE1) | (1<<TXCIE1); 
	UBRR0L = (unsigned char)(UBRRcalc); 
    UBRR0H = (unsigned char)(UBRRcalc>>8); 
	UCSR0A=0;
	UCSR0B=(1<<RXCIE0) | (1<<TXCIE0) | (1<<RXEN0) | (1<<TXEN0);
 	UCSR0C=(1<<UCSZ01) | (1<<UCSZ00); 
}
