
/*

ej1, d

#include <stdio.h>

int main(void){
    int x;
    float y;
    y = 5.5;
    x = y / 3;
    printf("x: %d | y: %f\n", x, y);

    int i;
    float j;
    float k;
    j = 5.5;
    i = (int) j / 3.0;
    k = (int) j / 3.0;
    printf("i: %d | j: %f | k: %f\n", i, j, k);

    return 0;
}
*/

/*

ej1,e

#include <stdio.h>

int main(void){
    double x;
    int y;
    x = 25;
    y = x / 5;
    printf("x: %lf | y: %d\n", x, y);

    double i;
    float j;
    i = 25.0;
    j = x / 5;
    printf("i: %lf | j: %f\n", i, j);

    return 0;
}


*/

/*

ej1, f

#include <stdio.h>

int main(void){

     int x, y;
     x = 5.0;
     y = x++;
     printf("x: %d | y: %d\n", x, y);

    return 0;
}

*/


/*
ej1, h



#include <stdio.h>

int main(void){

     char x, y;
     unsigned char z;
     x = y = 60;
     z = x + y;
     printf("x: %c | y: %c | z: %c\n", x, y, z);

    return 0;
}
*/


/*
ej1, j



#include <stdio.h>

int main(void){

    int x, y;
    x = (char) 300;
    y = x++;
    printf("x: %c | y: %c\n", x, y);

    return 0;
}


*/

/*
ej1, k
*/


#include <stdio.h>

int main(void){

    int x, y, z;
    z = (x = 2) + (y = x);
    printf("x: %cd | y: %d | z: %d\n", x, y, z);

    return 0;
}








