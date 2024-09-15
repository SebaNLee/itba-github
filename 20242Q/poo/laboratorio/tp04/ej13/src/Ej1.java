

public class Ej1 {

    public static void main(String[] args) {
        Ej1 ej1 = new Ej1();
        try {
            ej1.method();
            System.out.println("Método ejecutado");
        } catch (Exception2 e) {
            System.out.println("Excepción 2 capturada");
        } finally {
            System.out.println("Finalizando");
        }
    }

    public void method() throws Exception3 {
        throw new Exception3();
    }

}

// bien, pues Exception3 es sub de Exception2