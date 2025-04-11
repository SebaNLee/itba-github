


# para chequear que esté bien
nasm -f elf32 fiboASM.asm && gcc -c -m32 main.c && gcc -c -m32 fiboC.c && gcc -m32 main.o fiboASM.o fiboC.o -o main && ./main

# para salida de .s del código en C
