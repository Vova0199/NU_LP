#define F_CPU 12000000
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

//                              0    1    2    3    4    5    6    7    8    9

const unsigned char codes[10] = { 0x3f, 0x06, 0x5b, 0x4f, 0x66,
    0x6d, 0x7d, 0x07, 0x7f, 0x6f };
const unsigned char codespoint[10] = { 0xbf, 0x86, 0xdb, 0xcf, 0xe6,
    0xed, 0xfd, 0x87, 0xff, 0xef };
volatile long unsigned int clock = 0;
volatile long unsigned int pause = 0;

unsigned char data[8] = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
unsigned char counter = 0;
unsigned long buzzer = 0;
unsigned long indicator = 0;
unsigned char buzflag = 0;
unsigned char OffInd[2] = { 0x3f, 0x3f };
unsigned char OnInd[2] = { 0x77, 0x3f };
unsigned char ringstatus = 0;

unsigned int seconds = 0;
unsigned int minutes = 0;
unsigned int hours = 0;

unsigned char pc0status = 0;
unsigned char ringer = 0;

// ringer
unsigned int ringminutes = 0;
unsigned int ringhours = 0;

void init_timercl(void)
{
    TIMSK0 |= (1 << OCIE0A);
    OCR0A = 0x48;
    TCCR0B |= (1 << WGM02) | (1 << CS00) | (1 << CS02);
}

void init_timer(void)

{
    TIMSK2 = (1 << TOIE2);
    TCCR2B = (0 << CS20) | (1 << CS21) | (0 << CS22);
}

void delayc(long unsigned int Pause_ms)
{
    pause = 0;
    while (pause < Pause_ms) {
    }
}

void convert_data(unsigned int seconds, unsigned int minutes,
    unsigned int hours)
{
    unsigned int temp, res;
    temp = hours;
    res = temp / 10; // Calculate 10-h
    if (ringstatus == 1) {
        data[5] = OnInd[0];
    }
    else if (ringstatus == 2) {
        data[5] = OffInd[0];
    }
    else {
        data[0] = codes[res];
    }
    temp = temp - res * 10;
    if (ringstatus == 1) {
        data[1] = OnInd[1];
    }
    else if (ringstatus == 2) {
        data[1] = OffInd[1];
    }
    else {
        data[1] = codespoint[temp]; // Calculate 1-h
    }
    temp = minutes;
    res = temp / 10; // Calculate 10-m
    if (ringstatus == 2) {
        data[2] = OffInd[1];
    }
    else {
        data[2] = codes[res];
    }
    temp = temp - res * 10;

    data[3] = codespoint[temp]; // Calculate 1-m

    temp = seconds;
    res = temp / 10; // Calculate 10-s

    if (pc0status == 1) {
        data[4] = 0x5C;
    }
    else {
        data[4] = codes[res];
    }

    temp = temp - res * 10;

    if (pc0status == 1) {
        data[5] = 0x63;
    }
    else {
	    if (ringstatus == 1) {
        data[5] = OnInd[0];
    }
    else if (ringstatus == 2) {
        data[5] = OffInd[0];
    }
    else {
        data[5] = codes[temp]; 
    }
        // Calculate 1-s
    }
}

ISR(TIMER0_COMPA_vect)
{
    clock++;
    pause++;
    if (clock == 100) {
        seconds = seconds + 1;
        if (seconds == 60) {
            minutes = minutes + 1;
            seconds = 0;
            if (minutes == 60) {
                hours = hours + 1;
                minutes = 0;

                if (hours == 24) {
                    hours = 0;
                }
            }
        }
        clock = 0;
    }
    TCNT0 = 0x00;
}

ISR(TIMER2_OVF_vect)
{
    PORTA = 0xff;
    PORTB = ~_BV(counter);
    PORTA = ~data[counter];
    counter = (counter + 1) % 6;
    buzzer = (buzzer + 1) % 1000;
    indicator = (indicator + 1) % 10000;

    if ((ringer == 1) && (ringhours == hours) && (ringminutes == minutes)) {
        if ((buzzer == 0) && (buzflag == 0)) {
            PORTC = 0x00;
            buzflag = 1;
        }
        else if ((buzzer == 0) && (buzflag == 1)) {
            buzflag = 0;
            PORTC = 0x8;
        }
    }
    else {
    }
    if ((ringstatus != 0) && (indicator == 0)) {
        ringstatus = 0;
    }

    TCNT2 = 0x00;
}

int main(void)
{
    DDRA = 0xff;
    PORTA = 0x00;
    DDRB = 0xFF;
    PORTB = 0xFF;
    DDRC = 0x8;
    PORTD = 0x3C;

    init_timer();
    init_timercl();
    sei();

    while (1) {
        if (pc0status == 1) {
            convert_data(0, ringminutes, ringhours);
        }
        else {
            convert_data(seconds, minutes, hours);
        }

        //???????????? ????
        if (!(PIND & (1 << PD4)) && (pc0status == 0)) {
            delayc(40);

            hours++; //??????? ?????? ??????????
            if (hours == 24)
                hours = 0;
        }
        if (!(PIND & (1 << PD5)) && (pc0status == 0)) {
            delayc(40);

            minutes++; //??????? ??????? ??????????
            if (minutes == 60)
                minutes = 0;
        }

        //???????????? ????????
        if (!(PIND & (1 << PD2)) && (pc0status == 0)) {
            delayc(40);

            pc0status = 1;
            // convert_data(0, ringminutes, ringhours);
        }
        // ??????? ?????? ??????????

        if (!(PIND & (1 << PD4)) && (pc0status == 1)) {
            delayc(40);

            ringhours++;
            if (ringhours == 24)
                ringhours = 0;
        }

        // ?????????? ??????? ??????????
        if (!(PIND & (1 << PD5)) && (pc0status == 1)) {
            delayc(40);

            ringminutes++;
            if (ringminutes == 60)
                ringminutes = 0;
        }

        if (!(PIND & (1 << PD3)) && (pc0status == 1)) {
            delayc(40);

            pc0status = 0; //???????????? ?? ?????? ?????????
        }

        if (!(PIND & (1 << PD3)) && (pc0status == 0) && (ringer == 0)) {
            delayc(40);

            ringer = 1; //????????? ???
            ringstatus = 1;
        }
        if (!(PIND & (1 << PD3)) && (pc0status == 0) && (ringer == 1)) {
            delayc(40);

            ringer = 0;
            ringstatus = 2;
            PORTD = 0x3C;
        }
    }

    return 1;
}
