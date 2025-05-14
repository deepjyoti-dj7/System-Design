public class Main {
    public static void main(String[] args) {
        Context context = new Context();

        // Assign variables
        context.assign("a", 5);
        context.assign("b", 10);

        // a + b
        Expression a = new VariableExpression("a");
        Expression b = new VariableExpression("b");
        Expression add = new AddExpression(a, b);

        System.out.println("a + b = " + add.interpret(context)); // Output: 15
    }
}
