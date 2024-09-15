
public class Ej3 {

    public static void main(String[] args) {
        Ej3 ej3 = new Ej3();
        try {
            ej3.method();
        } catch (Exception2 e) {
            System.out.println("Excepción 2 capturada");
        } catch (Exception3 e) {
            System.out.println("Excepción 3 capturada");
        }
    }

    public void method() throws Exception3 {
        throw new Exception3();
    }

}

// no, pues Exception3 es sub de Exception2, y esta última ya está en un catch antes