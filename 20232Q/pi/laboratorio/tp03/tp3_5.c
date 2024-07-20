#include <stdio.h>

int main(void){




int a, b, c;
a = getchar();
b = getchar();
c = getchar();
if (a <= 'Z' && a >= 'B'  && a != 'E' && a != 'I' && a != 'O' && a != 'U')
printf("El caracter %c es una consonante mayúscula \n", a);
else
if (a <= 'z' && a >= 'b'  && a != 'e' && a != 'i' && a != 'o' && a != 'u')
printf("El caracter %c es una consonante minúscula \n", a);
else
if (a = 'A' && a == 'E' && a == 'I' && a == 'O' && a == 'U' && a == 'a' && a == 'e' && a == 'i' && a == 'o' && a == 'u')
printf("El caracter %c es una vocal \n", a);
else
printf("El caracter %c no es una letra \n", a);

if (b % 2 == 0)
printf("El valor ASCII de %c es par \n", b);

if (b <= '0' && b >= '9' && b % 2 == 0) // justo el 0, 2, etc son de orden par en la tabla ascii
printf("El caracter %c representa un dígito par \n", b);

if (c <= 'Z' && c >= 'A' || c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
printf("El caracter %c es una vocal o es mayúscula \n", c);

if ((c <= 'Z' && c >= 'B' || c <= 'z' && c >= 'b') && c != 'e' && c != 'i' && c != 'o' && c != 'u' && c != 'E' && c != 'I' && c != 'O' && c != 'U')
printf("El caracter %c no es una vocal pero es letra \n", c);


    return 0;
}

// algo está mal, pero no lo voy a testear lmao