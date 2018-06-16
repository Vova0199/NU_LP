#define e1 PORTC|=0b000000100;
#define e0 PORTC&=0b111111011;

#define rs1 PORTC|=0b00000001;
#define rs0 PORTC&=0b11111110;

#define rw1 PORTC|=0b00000010;
#define rw0 PORTC&=0b11111101;
#define ClearDisplay 0x01;
void port_ini(void)
{
	PORTB=0x00;
	DDRB=0xFF;
	PORTC=0x00;
	DDRC= 0xFF;
}

void sendCommand(unsigned char c){
	e0;
	PORTB=c;
}

void sendChar(unsigned char c){
	rs1;
	PORTB=c;
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
	DDRB=0xff;
	PORTB = l;
	e1;
	_delay_us(0.15);
	e0;
}
void LCD_WriteStr (volatile char *str)
{
    for(int i=0; i<255; i++)
        if (str[i]=='\0')
            return;
        else
            LCD_WriteLetter(str[i]);
}

void LCD_WriteCommand(unsigned char c){
	LCD_BF();
	rs0;
	rw0;
	DDRB = 0xff;
	PORTB = c;
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
	DDRB = 0x00;
	PORTB = 0x00;
	rs0;
	rw1;
	do
	{
		e0;
		e1;
		_delay_us(0.15);
	}
	while(PINB&(1<<PB7));
	e0;
}

void lcd_ini(void){
	_delay_ms(40);
	rs0;
	sendCommand(0b00111000); // ���� 8 ��, 2 ������, 5�8 �����
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
	sendCommand(0b00001100); // �������� ������� (������)
	e1;
	_delay_us(15);
	e0;
	_delay_us(50);
	sendCommand(0b00000100); // ����� ������ �� �������
	e1;
	_delay_us(0.15);
	e0;
	_delay_us(50);
	sendCommand(0b00000001); // �������� �������
	e1;
	_delay_us(15);
	e0;
}
