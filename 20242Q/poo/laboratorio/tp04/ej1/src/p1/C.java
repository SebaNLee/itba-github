package src.p1;

public class C implements A, B {

    @Override
    public boolean even(int value) {
        return value % 2 == 0;
    }

}


// anda perfectamente, hay conflicto, pero hace Override