import java.io.File;

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,5};
        int[] e1 = {0,3};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {-2,-9};
        int[] e2 = {1,4};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        Polynomial x = p1.multiply(p2);
    	System.out.println("x(1) = " + x.evaluate(1));
    	File txtfile = new File("./polynomial.txt");
    	Polynomial p3 = new Polynomial(txtfile);
    	System.out.println("p3(1) = " + p3.evaluate(1));
    	p2.saveToFile("new_polynomial");
    }
}