package src.p4;

public class C implements B {

    @Override
    public double sum(double num1, double num2) {
        return num1 + num2;
    }

}

// no, pues si ámbos metodos se llaman sum(), son métodos diferentes, pues devuelven int y double