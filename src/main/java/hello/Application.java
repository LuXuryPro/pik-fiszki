package hello;

public class Application {
    public int sum(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        Application app = new Application();
        System.out.println("Suma 2 + 5 = " + app.sum(2, 5));
    }

}
