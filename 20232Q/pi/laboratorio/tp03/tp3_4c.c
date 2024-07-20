#include <stdio.h>
#include "getnum.h"

int main(void){

int x, z;
x = getint("x:");
z = getint("z:");
// agegué algunas líneas para testear

if (x < 3 ){
printf("x < 3");

if (z < 5 ){
printf("x < 3 y z < 5");
}

}
else
printf("x >= 3");



    return 0;
}