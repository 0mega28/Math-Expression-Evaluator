import repl.Repl;

public class ExprEvaluator {
    public static void main(String[] args) {
        if (args.length > 0 && "-r".equals(args[0])) {
            Repl.repl();
        } else {
            test.Test.test();
        }
    }
}
