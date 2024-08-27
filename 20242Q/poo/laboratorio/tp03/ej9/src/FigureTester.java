public class FigureTester {

    public static void main(String[] args) {
        Rectangle myRectangle = new Rectangle(new Point(0, 0), new Point(2, 4));
        System.out.println(myRectangle); // Rectángulo [ {0,00 , 0,00} , {2,00 , 4,00} ]
        System.out.println("%.2f".formatted(myRectangle.perimeter())); // 12,00
        System.out.println("%.2f".formatted(myRectangle.area())); // 8,00
        System.out.println("%.2f".formatted(myRectangle.base())); // 2,00
        System.out.println("%.2f".formatted(myRectangle.height())); // 4,00

        Triangle myTriangle = new Triangle(new Point(0, 0), new Point(1, 0), new Point(0, 1));
        System.out.println(myTriangle); // Triángulo [ {0,00 , 0,00} , {1,00 , 0,00} , {0,00 , 1,00} ]
        System.out.println("%.2f".formatted(myTriangle.perimeter())); // 3,41
        System.out.println("%.2f".formatted(myTriangle.area())); //0,50

        Ellipse myEllipse = new Ellipse(new Point(0, 0), 2, 1);
        System.out.println(myEllipse); // Elipse [Centro: {0,00 , 0,00}, DMayor: 2,00, DMenor: 1,00]
        System.out.println("%.2f".formatted(myEllipse.perimeter())); // 4,71
        System.out.println("%.2f".formatted(myEllipse.area())); //1,57

        Circle myCircle = new Circle(new Point(0, 0), 3);
        System.out.println(myCircle); // Círculo [Centro: {0,00 , 0,00} , Radio: 3,00}]
        System.out.println("%.2f".formatted(myCircle.perimeter())); // 18,85
        System.out.println("%.2f".formatted(myCircle.area())); // 28,27
    }

}
