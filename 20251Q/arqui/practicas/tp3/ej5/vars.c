

// declaro una función que no hace nada como separador
// así es más fácil verlo en ASM
void separador();


int var3;
extern int var5;
static int var6 = 11;

int main() {

    // 1.
    int var1;
    separador();

    // 2.
    int var2 = 21;
    separador();

    // 3.
    var3 = 22;
    separador();

    // 4.
    static int var4 = 23;
    separador();

    // 5. 
    var5 = 10;
    separador();

    // 6.
    var6 = 24;
    separador();

    return 0;
}


void separador() {
    return;
}


// gcc -c vars.c -m32 -fno-dwarf2-cfi-asm -fno-exceptions -S -fno-asynchronous-unwind-tables -masm=intel