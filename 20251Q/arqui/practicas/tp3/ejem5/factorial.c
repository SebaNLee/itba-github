

int factorial(int n)
{
    if (n == 0)
        return 1;
    int factorial_n_1 = factorial(n - 1);
    return n * factorial_n_1;
}


// gcc -c factorial.c -m32 -fno-dwarf2-cfi-asm -fno-exceptions -S -fno-asynchronous-unwind-tables -masm=intel