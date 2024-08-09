public class MultipleCounter extends Counter {

    private int times;

    MultipleCounter(int times) {
        super(); // esto llama al constructor padre
        this.times = times;
    }

    public void increment() {
        // podría acceder a la instancia padre con super.count si no está definida con private
        // pero sería inseguro
        // quedaría: super.count = super.count + times;

        int aux = times;
        while (aux > 0){
            super.increment();
            aux--;
        }
    }

    public void decrement() {
        int aux = times;
        while (aux > 0){
            super.decrement();
            aux--;
        }
    }
}
