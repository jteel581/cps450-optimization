#include <syscall.h>


void writeint(int num) {
  char buf[20];
  char result[20] = "0\n";
  char *pos = buf;
  char *writeptr = result;
  int numWritten;
 
  // Handle negative numbers
  if (num < 0) {
    *writeptr++ = '-';
    num = -num;
  }
  
  if (num > 0) {
      
    // Build the number in reverse order
    while (num > 0) {
      *pos++ = (num % 10) + '0';
      num /= 10;
    }
    pos--;
    
    // Now we need to copy the results into the output buffer, reversed
    while (pos > buf) {
      *writeptr++ = *pos--;
    }
    *writeptr++ = *pos;
    *writeptr++ = 10;
    *writeptr++ = 0;
  } else {
    // number is 0; use default result
    writeptr = result + 3;
  }
  
  write(1, result, (writeptr - result) - 1);
  

}

int readint()
{
	  char buf[20];
	  int size = 20;
	  int readCount = 0;
	  int keepOn = 1;
	  char c[1];
	  int cSize = 1;
	  int count = 0;
	  while (keepOn != 0)
	  {
		count = read(0, c, cSize);
		if (readCount == 0 && c[0] == '-')
		{
			buf[readCount] = c[0];
			readCount = readCount + 1;

		}
		else if (c[0] == '0' || c[0] == '1' ||c[0] == '2' ||c[0] == '3' ||c[0] == '4' ||c[0] == '5' ||c[0] == '6' ||c[0] == '7' ||c[0] == '8' ||c[0] == '9')
		{
			buf[readCount] = c[0];
			readCount = readCount + 1;

		}
		else
		{
			
			keepOn = 0;
		}
	  }
	  //readCount = read(0, buf, size);
	  int numRead = 0;
	  int place = 1;
	  int i;
	  char firstChar;
	  firstChar = buf[0];
	  int isNegative = 1;
	  if (firstChar == '-')
	  {
	      isNegative = 0;
	  }
	  for (i = readCount - 1; i > -1; i--, place *= 10)
	  {
		  int numAtI = buf[i] - '0';
		  numRead = numRead + (numAtI * place);
		  if (isNegative == 0 && i == 1)
		  {
		  	  numRead = -numRead;
		  	  break;
		  }

	  }
	  return numRead;

}

double multiply(int a, int b)
{
	double c = a * b;
	return c;
}

double divide(int a, int b)
{
	double c = a / b;
	return c;
}


