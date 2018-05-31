#define F_CPU  11059200
#include <avr/io.h>
#include <util/delay.h>
#include <avr/pgmspace.h>
#include <avr/interrupt.h>
#include <avr/sleep.h>
#include "lcd4.h"

#define XTAL 8000000L
#define HI(x) ((x) >> 8) 
#define LO(x) ((x) & 0xFF)

#define Key_1 0b11101110
#define Key_2 0b11011110
#define Key_3 0b10111110
#define Key_A 0b01111110

#define Key_4 0b11101101
#define Key_5 0b11011101
#define Key_6 0b10111101
#define Key_B 0b01111101

#define Key_7 0b11101011
#define Key_8 0b11011011
#define Key_9 0b10111011
#define Key_C 0b01111011

#define Key_F 0b11100111
#define Key_0 0b11010111
#define Key_E 0b10110111
#define Key_D 0b01110111

#define BUZ_1 PORTB|=0b00001000;
#define BUZ_0 PORTB&=0b00000000;

volatile uint8_t tot_overflow;

unsigned char number[10] = {'0x30','0x31','0x32','0x33','0x34','0x35','0x36','0x37','0x38','0x39'};
	
unsigned int memory[60][8] = {};
	


unsigned int second = 0;
unsigned int second2 = 0;

unsigned int minute = 0;
unsigned int minute2 = 0;

unsigned int hour = 0;
unsigned int hour2 = 0;


unsigned int alarm_second = 0;
unsigned int alarm_second2 = 0;

unsigned int alarm_minute = 0;
unsigned int alarm_minute2 = 0;

unsigned int alarm_hour = 0;
unsigned int alarm_hour2 = 0;

unsigned int mem_alarm_second = 0;
unsigned int mem_alarm_second2 = 0;

unsigned int mem_alarm_minute = 0;
unsigned int mem_alarm_minute2 = 0;

unsigned int mem_alarm_hour = 0;
unsigned int mem_alarm_hour2 = 0;



unsigned int counter = 0;
unsigned int counterD = 0;

unsigned int counterE = 0;
unsigned int counterB = 0;
unsigned int counterC = 0;

unsigned int counter_memory = 0;
unsigned int counter_memory1 = 0;

unsigned int search = 0;
unsigned int search2 = 0;

unsigned int alarm = 0;
unsigned int alarm_status = 0;
unsigned int clock = 0;

unsigned int pause = 0;
unsigned int counterSearch = 0;
unsigned int used = 0;
unsigned int counterA = 0;

int main(void){
	
	DDRC = 0x0F;
	PORTC = 0xf0;
	
	DDRB = 0x0F;
	PORTB = 0xf0;
	port_ini();
	lcd_ini();
	display_ini();
	timer_ini();
	unsigned char freePin = 1, key =1;
	


	while(1){
		if(freePin == 1)
		{
			if(PINC!=0xf0)
			{
				_delay_ms(50);
				freePin = 0;
				PORTC = 0b11111110;
				asm("nop");
				if(PORTC==PINC)
				{
					PORTC = 0b11111101;
					asm("nop");
					if(PORTC == PINC)
					{
						PORTC = 0b11111011;
						asm("nop");
						if(PORTC == PINC)
						{
							PORTC = 0b11110111;
							asm("nop");
							if(PORTC == PINC)
							{
								key = 0;
							}
						}
					}
				}

				if(key==1)
				{
					buz_very_short();
					if(PINC == Key_D)
					{
						counterD++;
						if(counterD == 1)
						{
							alarm_status =1;
							counter = 1;
							counterD = 0;
							pause = 0;
						}
						else
						if(counterD==2)
						{
							alarm_status =0;
							counter = 0;
							counterD = 1;

							pause = 1;
						}
					}

					if(PINC == Key_C)
					{
						counterSearch++;
						if(counterSearch == 1)
						{
							counter = 0;
							display_alarm_mem();
							//readForMemory();
							counterC = 1;
							alarm=1;
						}
						if(counterSearch == 2)
						{
							counterSearch = 0;
							counter = 1;
							display_search_none();
							counterC = 0;
							alarm=0;
						}
						
					}

					if(PINC == Key_E)
					{
								saveToTheMemory();
						
						
					}

					if(PINC == Key_B)
					{
						counterSearch++;
						if(counterSearch == 1)
						{
							counter = 0;
							display_alarm();
							counterA = 1;
							alarm=1;
						}
						if(counterSearch == 2)
						{
							counterSearch = 0;
							counter = 1;
							display_search_none();
							counterA = 0;
							alarm=0;
						}
						
					}

					if(PINC == Key_F)
					{

						
						
					}

					if(PINC == Key_A)
					{
						counterSearch++;
						if(counterSearch == 1)
						{
							counter = 0;

							counterA = 1;
							clock  = 1;
						}
						if(counterSearch == 2)
						{
							counterSearch = 0;
							counter = 1;
							display_search_none();
							counterA = 0;
						}
					}
					if(PINC == Key_1)
					{
						if(counterSearch == 1){
							write_number(1);
						}
					}
					if(PINC == Key_2)
					{
						if(counterSearch == 1){
							write_number(2);
						}
					}
					if(PINC == Key_3)
					{
						if(counterSearch == 1){
							write_number(3);
						}
					}
					if(PINC == Key_4)
					{
						if(counterSearch == 1){
							write_number(4);
						}
					}
					if(PINC == Key_5)
					{
						if(counterSearch == 1){
							write_number(5);
						}
					}
					if(PINC == Key_6)
					{
						if(counterSearch == 1){
							write_number(6);
						}
					}
					if(PINC == Key_7)
					{
						if(counterSearch == 1){
							write_number(7);
						}
					}
					if(PINC == Key_8)
					{
						if(counterSearch == 1){
							write_number(8);
						}
					}
					if(PINC == Key_9)
					{
						if(counterSearch == 1){
							write_number(9);
						}
					}
					if(PINC == Key_0)
					{
						if(counterSearch == 1){
							write_number(0);
						}
					}
				}
				key=1;
				PORTC = 0xf0;
			}
		}
		else
		if(PINC == 0xf0)
		{
			_delay_ms(200);
			freePin=1;
		}
		if(second == mem_alarm_second && second2 == mem_alarm_second2 && minute == mem_alarm_minute
			&& minute2 == mem_alarm_minute2 && hour == mem_alarm_hour && hour2 == mem_alarm_hour2 && alarm_status == 1){
			buz_short();
		}
	if(alarm_status == 1){
		LCD_GoToYX(1,1);
		LCD_WriteLetter(0x41);
	}
		
	}
}

int display_ini(void){
	LCD_GoToYX(1,9);
	LCD_WriteLetter(0x30);
	LCD_WriteLetter(0x30);
	LCD_WriteLetter(0x3a);
	LCD_WriteLetter(0x30);
	LCD_WriteLetter(0x30);
	LCD_WriteLetter(0x3a);
	LCD_WriteLetter(0x30);
	LCD_WriteLetter(0x30);
}

int timer_ini(void){
	TIMSK0 |= (1 << OCIE0A);
    OCR0A = 0x48;
    TCCR0B |= (1 << WGM02) | (1 << CS00) | (1 << CS02);

	sei();
	tot_overflow = 0;
}

ISR(TIMER0_COMPA_vect)
{
		// keep a track of number of overflows
		tot_overflow++;
		if (tot_overflow >= 4 ) // NOTE: '>=' used instead of '=='
		{	
			if(alarm == 0 && clock == 0)
			{	
				second++;
				if(second > 9)
				{
					second2++;
					second = 0;
					if(second2 > 5)
					{
						minute++;
						second2 = 0;
						display_m1(minute);
						if(minute > 9)
						{
							minute2++;
							minute=0;
							display_m2(minute2);
							if(minute2 > 5)
							{
								hour++;
								minute2=0;
								display_h1(hour);
								if(hour>9)
								{
									hour2++;
									hour = 0;
									display_h2(hour2);
								}
							}
						}
					}
					display_s2(second2);
				}
				display_s1(second);
				tot_overflow = 0;   // reset overflow counter
			}
		}
}

int display_s1(unsigned int second1)
{
	char second1_review = number[second1];
	LCD_GoToYX(1,16);
	LCD_WriteLetter(second1_review);
}

int display_s2(unsigned int second2)
{
	char second2_review = number[second2];
	LCD_GoToYX(1,15);
	LCD_WriteLetter(second2_review);
}

int display_m1(unsigned int minute1)
{
	char minute1_review = number[minute1];
	LCD_GoToYX(1,13);
	LCD_WriteLetter(minute1_review);
}

int display_m2(unsigned int minute2)
{
	char minute2_review = number[minute2];
	LCD_GoToYX(1,12);
	LCD_WriteLetter(minute2_review);
}

int display_h1(unsigned int hour1)
{
	char hour1_review = number[hour1];
	LCD_GoToYX(1,10);
	LCD_WriteLetter(hour1_review);
}

int display_h2(unsigned int hour2)
{
	char hour2_review = number[hour2];
	LCD_GoToYX(1,9);
	LCD_WriteLetter(hour2_review);
}



int saveToTheMemory(void)
{
	display_s1(second);
	display_s2(second2);
	display_m1(minute);
	display_m2(minute2);
	display_h1(hour);
	display_h2(hour2);
	alarm=0;
	clock=0;
}




int ini_used_1(unsigned int c){
	LCD_GoToYX(1,1);
	LCD_WriteLetter(c);
}

int ini_used_2(unsigned int c){
	LCD_GoToYX(1,2);
	LCD_WriteLetter(c);
}

int write_number(unsigned int c){
	char d = number[c];
	used++;
	if(alarm == 1){
	if(used == 1){
		LCD_GoToYX(1,16);
		LCD_WriteLetter(d);
		mem_alarm_second = c;
		search2 = c;
	}
	if(used == 2)
	{
		LCD_GoToYX(1,15);
		LCD_WriteLetter(d);
		mem_alarm_second2 = c;
		search = c;
	}
	if(used == 3){
		LCD_GoToYX(1,13);
		LCD_WriteLetter(d);
		mem_alarm_minute = c;
	}
	if(used == 4){
		LCD_GoToYX(1,12);
		LCD_WriteLetter(d);
		mem_alarm_minute2 = c;
	}
	if(used == 5){
		LCD_GoToYX(1,10);
		LCD_WriteLetter(d);
		mem_alarm_hour = c;
	}
	if(used == 6){
		LCD_GoToYX(1,9);
		LCD_WriteLetter(d);
		mem_alarm_hour2 = c;
	}
	}




		if(clock == 1){
	if(used == 1){
		LCD_GoToYX(1,16);
		LCD_WriteLetter(d);
		second = c;
		search2 = c;
	}
	if(used == 2)
	{
		LCD_GoToYX(1,15);
		LCD_WriteLetter(d);
		second2 = c;
		search = c;
	}
	if(used == 3){
		LCD_GoToYX(1,13);
		LCD_WriteLetter(d);
		minute = c;
	}
	if(used == 4){
		LCD_GoToYX(1,12);
		LCD_WriteLetter(d);
		minute2 = c;
	}
	if(used == 5){
		LCD_GoToYX(1,10);
		LCD_WriteLetter(d);
		hour = c;
	}
	if(used == 6){
		LCD_GoToYX(1,9);
		LCD_WriteLetter(d);
		hour2 = c;
	}
	}
}


int display_search_none(void){
	display_s1(second);
	display_s2(second2);
	display_m1(minute);
	display_m2(minute2);
	display_h1(hour);
	display_h2(hour2);
}

int display_alarm_mem(void){
	LCD_GoToYX(1,1);
	LCD_WriteLetter(0x20);
	LCD_WriteLetter(0x20);
	display_s1(mem_alarm_second);
	display_s2(mem_alarm_second2);
	display_m1(mem_alarm_minute);
	display_m2(mem_alarm_minute2);
	display_h1(mem_alarm_hour);
	display_h2(mem_alarm_hour2);
}


int display_alarm(void){
	LCD_GoToYX(1,1);
	LCD_WriteLetter(0x20);
	LCD_WriteLetter(0x20);
	display_s1(alarm_second);
	display_s2(alarm_second2);
	display_m1(alarm_minute);
	display_m2(alarm_minute2);
	display_h1(alarm_hour);
	display_h2(alarm_hour2);
}

int buz_long(void){
	BUZ_1;
	_delay_ms(100);
	BUZ_0;
}

int buz_short(void){
	BUZ_1;
	_delay_ms(50);
	BUZ_0;
	_delay_ms(50);
	BUZ_1;
	_delay_ms(50);
	BUZ_0;
}

int buz_very_short(void) {
	BUZ_1;
	_delay_ms(50);
	BUZ_0;
}

int buz_minute(void)
{
	BUZ_1;
	_delay_ms(250);
	BUZ_0;
}

