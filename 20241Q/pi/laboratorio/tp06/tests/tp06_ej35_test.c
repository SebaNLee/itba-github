#include <stdio.h>
#include <assert.h>

int tildarPalabras(const char frase[]);

int main(void) {
    char s[] = "|El ca-|fe |del me-|nu |tie-ne |mal |gus-to";
    assert(2 == tildarPalabras(s));
    
    char s2[] = "Da-|mian com-|pro |es-te |ar-bol";
    assert(3 == tildarPalabras(s2));
    
    char s3[] = "";
    assert(0 == tildarPalabras(s3));
    
    char s4[] = " ";
    assert(0 == tildarPalabras(s4));
    
    assert(0 == tildarPalabras(""));
    assert(0 == tildarPalabras("A-pro-|ba-mos |el e-|xa-men"));
    assert(0 == tildarPalabras("A-pro-|ba-mos |el e-|xa-men   "));
    
    puts("OK!");
    return 0;
    
}