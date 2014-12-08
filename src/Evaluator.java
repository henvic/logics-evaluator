/**
 * @author Henrique Vicente
 * @author Rodrigo Alves
 */

import java.util.HashMap;
import java.util.Stack;

class Table {
    int arity;
    int[][] matrix;

    public Table(int arity) {
        this.arity = arity;
        this.matrix = new int[(int) Math.pow(2, arity)][arity+1];
    }

    public String toString() {
        return this.arity + "";
    }
}

public class Evaluator {

    /**
     * Returns true if the expression is well-formed. Returns false otherwise.
     */
    public static boolean isWFF(String expr, HashMap<Character, Table> tableSet) {
        Stack<String> parents = new Stack<String>();
        Stack<String> operators = new Stack<String>();

        int times;
        char current, top;

        for (int i = 0; i < expr.length(); i++) {
            current = expr.charAt(i);

            if (current == '(') {
                parents.push(current + "");
            } else if (current == ')') {
                if (operators.size() > 0) {
                    top = operators.peek().charAt(0);

                    // Check whether the element at the top is an operator or an atom
                    if (tableSet.get(top) == null) {
                        // If the top element is not an operator, then after removing
                        // it we will have the next operator
                        operators.pop();

                        if (operators.size() > 0) {
                            top = operators.peek().charAt(0);

                            if (tableSet.get(top) == null) return false;

                            if (tableSet.get(top).arity == 2) {
                                operators.pop();
                            }
                        }
                    }

                    if (parents.size() > 0) {
                        parents.pop();
                    }
                } else {
                    return false;
                }

            } else {
                operators.push(current + "");
            }
        }

        if (parents.size() == operators.size()) return true;
        return false;
    }

    public static void main(String[] args) {
        Arquivo io = new Arquivo("input.txt", "output.txt");

        int N, arity, expressions;
        char operator;
        String[] exprDB;
        HashMap<Character, Table> tableSet = new HashMap<Character, Table>();
        Table table;

        N = io.readInt();

        while (N > 0) {
            operator = io.readString().charAt(0);
            arity = io.readInt();

            table = new Table(arity);

            tableSet.put(operator, table);

            for (int i = 0; i < (int) Math.pow(2, arity); i++) {
                for (int j = 0; j < (arity + 1); j++) {
                    table.matrix[i][j] = io.readInt();
                }
            }

            N--;
        }

        expressions = io.readInt();

        exprDB = new String[expressions];

        for (int i = 0; i < exprDB.length; i++) {
            String expr = io.readString();

            System.out.println("Expressao " + (i + 1));

            if (isWFF(expr, tableSet)) {
                System.out.println("Expressao bem-formada");
                // @todo height
                // @todo amount of sub expressions
                // @todo variable truth-values
            } else {
                System.out.println("Expressao mal-formada");
            }

            System.out.println();

            exprDB[i] = expr;
        }
    }

}
