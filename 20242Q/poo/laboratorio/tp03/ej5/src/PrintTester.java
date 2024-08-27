public class PrintTester {

    public static void main(String[] args) {
        A a = new A();
        a.print(3);
        a.print(3.14);
        a.print((Number)3);
        a.print((Object)3);

        System.out.println();

        A b1 = new B();
        b1.print(3.14);
        b1.print("Hola");
        b1.print((Number)3);
        b1.print((Object)3);

        System.out.println();

        B b2 = new B();
        b2.print(3.14);
        b2.print("Hola");
        b2.print((Number)3);
        b2.print((Object)3);
    }

}

// !! buen ej, el compilador siempre va eligiendo lo más específico

// Integer
// invoca Number que se resuelve a Double
// invoca Number que se resuelve a Integer
// invoca Object que se resuelve a Integer

// como no existe para Double, baja hasta clase B para ejecutar y resolver como Number
// Object y es String, clase A
// resuelve en clase B
// no hay en B, entonces resuelve en A

// todos ídem a A b1, pues es la misma instancia. Primero se manda a B y si no está manda a A
// 
//
//
