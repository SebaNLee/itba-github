

int fiboC(int n) {

    if(n == 0 || n == 1) {
        return 1;
    }

    return fiboC(n - 2) + fiboC(n - 1);
}