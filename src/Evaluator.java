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
    public static void getTruthyValuesFor(String expr, Arquivo io, HashMap<Character, Table> tableSet) {
        int arity;
        Table currentTable;

        for (int k = 0; k < expr.length(); k++) {
            currentTable = tableSet.get(expr.charAt(k));

            if (currentTable != null) {
                arity = currentTable.arity;

                if (arity == 1) {
                    for (int i = 0; i < currentTable.matrix.length; i++) {
                        if (currentTable.matrix[i][arity + 1] == 1) {

                        }
                    }
                } else {
                }
            } else {
                if (expr.charAt(k) != '(' && expr.charAt(k) != ')') {
                    // Is a single atom!
                    io.println("(" + Character.toUpperCase(expr.charAt(k)) + "=1)");
                }
            }
        }
    }

    public static int getSubExpressionsCount(String expr) {
        int answer = 2;

        return answer;
    }

    public static int getHeight(String expr) {
        int answer = 2;

        return answer;
    }

    /**
     * Returns true if the expression is well-formed. Returns false otherwise.
     */
    public static boolean isWFF(String expr, HashMap<Character, Table> tableSet) {
        Stack<String> parents = new Stack<String>();
        Stack<String> operators = new Stack<String>();
        char current, top;

        for (int i = 0; i < expr.length(); i++) {
            current = expr.charAt(i);

            if (current == '(') {
                parents.push(current + "");
            } else if (current == ')') {
                if (operators.size() > 0) {
                    if (operators.size() > 0) operators.pop();

                    if (operators.size() > 0) {
                        top = operators.peek().charAt(0);

                        // Check whether the element at the top is an operator or an atom
                        if (tableSet.get(top) != null) {
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
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }

                    if (parents.size() > 0) parents.pop();

                } else {
                    return false;
                }

            } else {
                operators.push(current + "");
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Arquivo io = new Arquivo("input.txt", "output.txt");

        int N, arity, expressions;
        char operator;
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

        for (int i = 0; i < expressions; i++) {
            String expr = io.readString();

            io.println("Expressao " + (i + 1));

            if (isWFF(expr, tableSet)) {
                io.println("Expressao bem-formada");
                io.println("Altura=" + getHeight(expr));
                io.println("Sub-expressoes=" + getSubExpressionsCount(expr));

                getTruthyValuesFor(expr, io, tableSet);
            } else {
                io.println("Expressao mal-formada");
            }

            io.println();
        }
    }

}
