

int dcm (int a, int b){

    int auxi;

    auxi = a; // agrego esto para que no se pierda el valor de a

    while (auxi>0)
    {
        a = b;
        b = auxi;
        auxi = a % b ;
    }

    return b; // queda guardado en b, y no a
}

// se pierde el valor de a