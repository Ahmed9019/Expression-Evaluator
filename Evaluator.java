import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

interface IExpressionEvaluator {

    /**
     * Takes a symbolic/numeric infix expression as input and converts it to
     * postfix notation. There is no assumption on spaces between terms or the
     * length of the term (e.g., two digits symbolic or numeric term)
     *
     * @param expression infix expression
     * @return postfix expression
     */

    public String infixToPostfix(String expression);

    /**
     * Evaluate a postfix numeric expression, with a single space separator
     * @param expression postfix expression
     * @return the expression evaluated value
     */

    public int evaluate(String exp);

}

public class Evaluator implements IExpressionEvaluator {

    public class MyStack {
        public class Node {
            private Object element;
            private Node next;
            public Node(Object element, Node next) {
                this.element = element;
                this.next = next;
            }
            /* Getter */
            public Object getElement() {
                return element;
            }
            public Node getNext() {
                return next;
            }
            /* Setter */
            public void setElement(Object element) {
                this.element = element;
            }
            public void setNext(Node next) {
                this.next = next;
            }
        }
        private int size;
        private Node top;
        public MyStack() {
            top = new Node(null, null);
            size = 0;
        }
        public Object pop() {
            if (size == 0) {
                return 'r';
            }
            Object temp = top.getElement();
            top = top.getNext();
            size -= 1;
            return temp;
        }
        public Object peek() {
            if (size == 0) {
                return 'r';
            }
            return top.getElement();
        }
        public void push(Object element) {
            Node e = new Node(element, top);
            top = e;
            size += 1;
        }
        public boolean isEmpty() {
            return (size == 0);
        }
        public int size() {
            return size;
        }
        public void display(MyStack st) {
            Node n = top;
            System.out.print("[");
            if (st.size() != 0) {
                System.out.print(st.pop());
            }
            while (st.size() != 0) {
                System.out.print(", ");
                System.out.print(st.pop());
            }
            System.out.print("]");
        }
    }

    public static boolean isOp(char opr) {
        if (opr == '+' || opr == '-' || opr == '/' || opr == '*' || opr == '^') {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isCh(char c) {
        if (c == 'a' || c == 'b' || c == 'c') {
            return true;
        } else {
            return false;
        }
    }

    public static int degree(char op) {
        if (op == '+' || op == '-') {
            return 1;
        } else if (op == '*' || op == '/') {
            return 2;
        } else if (op == '^') {
            return 3;
        }
        return 0;
    }

    public static int a, b, c;

    public String infixToPostfix(String expression) {
        MyStack infix = new MyStack();
        int open = 0;

        MyStack op = new MyStack();

        String post = "";
        char l;

        for (int i = expression.length() - 1; i >= 0; i--) {
            infix.push(expression.charAt(i));
        }
        for (int i = 0; i < expression.length(); i++) {
            l = expression.charAt(i);

            if (isCh(l)) {
                post += l;
            } else if (l == '(') {
                op.push(l);
                open += 1;
            } else if (l == ')') {
                if (open > 0) {
                    while (!op.isEmpty() && (char) op.peek() != '(') {
                        post += op.pop();
                    }
                    op.pop();
                    open -= 1;
                } else {
                    return "Error";
                }
            } else if (isOp(l)) {
                while (!op.isEmpty() && degree((char) op.peek()) >= degree(l)) {
                    post += op.pop();
                }
                op.push(l);
            } else {
                return "Error";
            }
        }
        while (!op.isEmpty()) {
            if ((char) op.peek() == '(') {
                return "Error";
            }
            post += op.pop();
        }
        if (post.length() == 0 || post == "+" || post == "-" || post == "/" || post == "*" || post == "^") {
            return "Error";
        }

        return post;
    }
    public int evaluate(String exp) {
        MyStack values = new MyStack();

        int num = 0, val = 0;
        for (int i = 0; i < exp.length(); i++) {
            char l = exp.charAt(i);
            if (isCh(l)) {
                if (l == 'a') {
                    values.push(a);
                } else if (l == 'b') {
                    values.push(b);
                } else if (l == 'c') {
                    values.push(c);
                }
                num++;
            } else if (isOp(l) && num >= 2) {
                int val1 = (int) values.pop();
                int val2 = (int) values.pop();
                num -= 2;
                switch (l) {
                    case '+':
                        values.push(val2 + val1);
                        num++;
                        break;

                    case '-':
                        values.push(val2 - val1);
                        num++;
                        break;

                    case '/':
                        values.push((int) val2 / val1);
                        num++;
                        break;

                    case '*':
                        values.push(val2 * val1);
                        num++;
                        break;

                    case '^':
                        values.push((int) Math.pow(val2, val1));
                        num++;
                        break;

                }
            } else if (isOp(l) && num < 2) {
                val = (int) values.pop();
                if (l == '-') {
                    val = val * (-1);
                }
                values.push(val);
            }
        }
        return (int) values.pop();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String exp = sc.nextLine();

        String aline = sc.nextLine().replace("a=", "");
        String bline = sc.nextLine().replace("b=", "");
        String cline = sc.nextLine().replace("c=", "");
        a = Integer.parseInt(aline);
        b = Integer.parseInt(bline);
        c = Integer.parseInt(cline);
        /**/
        boolean er = false;
        int numC = 0;

        if (exp.length() == 2) {
            if (exp.charAt(1) == '*' || exp.charAt(1) == '^' || exp.charAt(1) == '/' ||
                exp.charAt(0) == '*' || exp.charAt(0) == '^' || exp.charAt(0) == '/') {
                er = true;
            } else if (exp.charAt(1) == '-' || exp.charAt(1) == '+') {
                er = true;
            }
        }
        String exp1 = exp.replaceAll("--", "+").replace("++", "+");
        String expn = exp1.replace("/+", "/").replace("*+", "*").replace("^+", "^").replace("(+", "(").replace("++", "+").replace("-+", "-");
        for (int i = 0; i < expn.length() && !er; i++) {
            if (isCh(expn.charAt(i))) {
                numC++;
            }
        }
        if (numC < 1) {
            er = true;
        }

        for (int i = 0; i < expn.length() - 1 && !er; i++) {
            char si = expn.charAt(i);
            char sy = expn.charAt(i + 1);
            if ((si == '/' && sy == '*') ||
                (si == '*' && sy == '/') ||
                (si == '^' && sy == '*') ||
                (si == '*' && sy == '^') ||
                (si == '^' && sy == '/') ||
                (si == '/' && sy == '^') ||
                (si == '/' && sy == '/') ||
                (si == '*' && sy == '*') ||
                (si == '^' && sy == '^')) {
                er = true;
            }
        }
        if (expn.charAt(0) == '+') {
            expn = expn.substring(1, expn.length());
        }
        Evaluator ev = new Evaluator();
        if (!er) {
            String postfix = ev.infixToPostfix(expn);
            if (postfix == "Error") {
                System.out.println("Error");
            } else {
                int eval = ev.evaluate(postfix);
                System.out.println(postfix);
                System.out.println(eval);
            }
        } else {
            System.out.println("Error");
        }

    }
}