public class jSubclass extends jSuperclass {
    public void print() {
        System.out.println("in subclass");
    }
    public static void main(String[] argv) {
        jSuperclass sup = new jSubclass();
        ((jSubclass)sup).print();

    }
}
