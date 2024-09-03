
public class HTMLTester {

    public static void main(String[] args) {
        PlainText text = new PlainText("Hola");
        HTMLText boldText = new BoldText(text);
        HTMLText italicText = new ItalicText(text);
        System.out.println(boldText);
        System.out.println(italicText);
        HTMLText boldItalicText = new BoldText(italicText);
        System.out.println(boldItalicText);
        text.setText("ITBA");
        System.out.println(boldText);
        System.out.println(italicText);
        System.out.println(boldItalicText);
        HTMLText linkText = new LinkText(text, "itba.edu.ar");
        HTMLText linkBoldText1 = new LinkText(boldItalicText, "itba.edu.ar");
        HTMLText linkBoldText2 = new BoldText(linkText);
        System.out.println(linkText);
        System.out.println(linkBoldText1);
        System.out.println(linkBoldText2);
        text.setText("Ejemplo");
        System.out.println(linkBoldText1);
        System.out.println(linkBoldText2);
    }

}


// !!! buen ej
// hacer seguimiento de cómo se utiliza la interfaz HTMLText y cómo se guarda HTMLText como elemento en común

// !!
// es como una relación de recurrencia
// en donde el caso base es que se guarde PlainText en HTMLText element

// obs: para LinkText tengo que hacer un source() aparte, pues es exclusivo