


# compilo, linkedito y ejecuto con biblioteca
nasm -f elf32 biblioteca.asm && gcc -c -m32 test.c && gcc -m32 biblioteca.o test.o -o test && ./test


# doy admin a output
chmod 777 temp.txt