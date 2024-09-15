package ar.edu.itba.poo.soltp5.ej6;

public class ArrayAccessStack<E> extends ArrayStack<E> implements AccessStack<E> {

    private int popAccesses;

    @Override
    public E pop() {
        E toReturn = super.pop();
        popAccesses++;
        return toReturn;
    }

    @Override
    public int getPopAccesses() {
        return popAccesses;
    }

    @Override
    public int getPushAccesses() {
        return getDim() + popAccesses;
    }

}
