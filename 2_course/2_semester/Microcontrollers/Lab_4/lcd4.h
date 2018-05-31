#define e1 PORTD|=0b00000100;
#define e0 PORTD&=0b11111011;

#define rs1 PORTD|=0b00000001;
#define rs0 PORTD&=0b11111110;

#define rw1 PORTD|=0b00000010;
#define rw0 PORTD&=0b11111101;

void port_ini(void)
{
	PORTA=0x00;
	DDRA=0xFF;
	PORTD=0x00;
	DDRD=0xFF;
}

void sendCommand(unsigned char c){
	e0;
	PORTA=c;
}

void sendChar(unsigned char c){
	rs1;
	PORTA=c;
	e1;
	_delay_ms(0.15);
	e0;
}

void setpos(unsigned char x, unsigned char y){
	char address;
	address = (0x40*x+y);
	sendChar(address);
}

void LCD_WriteLetter(unsigned char l){
	LCD_BF();
	rs1;
	rw0;
	DDRA=0xff;
	PORTA = l;
	e1;
	_delay_us(0.15);
	e0;
}

void LCD_WriteCommand(unsigned char c){
	LCD_BF();
	rs0;
	rw0;
	DDRA = 0xff;
	PORTA = c;
	e1;
	_delay_us(0.15);
	e0;
}

void LCD_GoToYX(unsigned char Y, unsigned char X)
{
	if(Y==1) LCD_WriteCommand(((1<<7)|(X-1)));
	else LCD_WriteCommand(((1<<7)|(0x40+X-1)));
}

void LCD_BF()
{
	DDRA = 0x00;
	PORTA = 0x00;
	rs0;
	rw1;
	do
	{
		e0;
		e1;
		_delay_us(0.15);
	}
	while(PINA&(1<<PA7));
	e0;
}

void lcd_ini(void){
	_delay_ms(40);
	rs0;
	sendCommand(0b00111000); // шина 8 біт, 2 стрічки, 5х8 точок
	e1;
	_delay_us(0.15);
	e0;
	_delay_us(50);
	e1;
	_delay_us(0.15);
	e0;
	_delay_us(50);
	e1;
	_delay_us(0.15);
	e0;
	_delay_us(50);
	sendCommand(0b00001100); // включити дисплей (курсор)
	e1;
	_delay_us(15);
	e0;
	_delay_us(50);
	sendCommand(0b00000100); // зсуви екрану та курсора
	e1;
	_delay_us(0.15);
	e0;
	_delay_us(50);
	sendCommand(0b00000001); // очистити дисплей
	e1;
	_delay_us(15);
	e0;
}