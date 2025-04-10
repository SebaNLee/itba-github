
#include "biblioteca.h"


#define READ_SIZE 50
#define FLAGS 577 // read, create y truncar

int main() {

    int filedescriptor = my_open("temp.txt", FLAGS, 438);

    char read[READ_SIZE + 1];
    int r = my_read(filedescriptor, read, READ_SIZE);

    char * write = "Hola Mundo!\n";
    int w = my_write(filedescriptor, write, 12);

    my_close(filedescriptor);

    my_exit(42);
}


// ./build.sh