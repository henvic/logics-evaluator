/**
 * @author Henrique Vicente
 * @author Rodrigo Alves
 */

import java.util.HashMap;
import java.util.Stack;

class MalformedExpression extends Exception {
}

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
    private HashMap<Character, Table> tableSet;

    private Arquivo io;

    public static char getTopOperator(String str, HashMap<Character, Table> tableSet) {
        char answer = str.charAt(0);

        for (int i = 0, j = str.length() - 1; i < str.length() && i < j; i++, j--) {
            if (tableSet.get(str.charAt(i)) != null) {
                answer = str.charAt(i);
                break;
            } else if (tableSet.get(str.charAt(j)) != null) {
                answer = str.charAt(i);
                break;
            }
        }

        return answer;
    }

    public static void getTruthValuesFor(String expr, Arquivo io, HashMap<Character, Table> tableSet) {
        int arity;
        char atom1, atom2;
        Table currentTable;

        for (int k = 0; k < expr.length(); k++) {
            currentTable = tableSet.get(expr.charAt(k));

            if (currentTable != null) {
                arity = currentTable.arity;

                atom1 = expr.charAt(k+1);

                if (arity == 1) {
                    for (int i = 0; i < currentTable.matrix.length; i++) {
                        if (currentTable.matrix[i][arity + 1] == 1) {
                        }

                    }
                } else { // Arity is 2
                    atom2 = expr.charAt(k-1);

                    for (int i = 0; i < currentTable.matrix.length; i++) {
                        if (currentTable.matrix[i][arity + 1] == 1) {

                        }
                    }

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
        Stack<String> brackets = new Stack<String>();
        Stack<String> operators = new Stack<String>();
        char current, top;

        for (int i = 0; i < expr.length(); i++) {
            current = expr.charAt(i);

            if (current == '(') {
                brackets.push(current + "");
            } else if (current == ')') {
                if (operators.size() > 0) {
                    // This guy must always be removed because it will always be a
                    // parameter whether the operator is unary or binary
                    if (operators.size() > 0) operators.pop();

                    if (operators.size() > 0) {
                        // If the top element is not an operator, then after removing
                        // it we will have the next operator
                        top = operators.peek().charAt(0);

                        // Check whether the element at the top is an operator or an atom
                        if (tableSet.get(top) != null) {

                            if (tableSet.get(top).arity == 2) {
                                operators.pop();
                            }

                            if (operators.size() > 0) {
                                operators.pop();
                            } else {
                                return false;
                            }

                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }

                    if (brackets.size() > 0) brackets.pop();

                } else {
                    return false;
                }

            } else {
                operators.push(current + "");
            }
        }

        return true;
    }

    public Table getOperatorTruthTable(int arity) {
        Table table = new Table(arity);

        for (int i = 0; i < (int) Math.pow(2, arity); i++) {
            for (int j = 0; j < (arity + 1); j++) {
                table.matrix[i][j] = io.readInt();
            }
        }

        return table;
    }

    private void getOperators() {
        int arity;
        int operators;
        char operator;

        operators = io.readInt();

        for (int current = 0; current < operators; current += 1) {
            operator = io.readString().charAt(0);
            arity = io.readInt();

            tableSet.put(operator, getOperatorTruthTable(arity));
        }
    }

    private void getExpression(int counter) {
        String expr = io.readString();

        io.println("Expressao " + (counter + 1));

        if (!isWFF(expr, tableSet)) {
            io.println("Expressao mal-formada");

            //verifica se está no alfabeto todos os elementos
            //
            //verificar parênteses
            //
        }

        if (isWFF(expr, tableSet)) {
            io.println("Expressao bem-formada");
            io.println("Altura=" + getHeight(expr));
            io.println("Sub-expressoes=" + getSubExpressionsCount(expr));

//                getTruthValuesFor(expr, io, tableSet);
        } else {
            io.println("Expressao mal-formada");
        }

        io.println();
    }

    private void getExpressions() {
        int expressions = io.readInt();

        for (int current = 0; current < expressions; current += 1) {
            getExpression(current);
        }
    }

    public void run() {
        tableSet = new HashMap<>();
        io = new Arquivo("input.txt", "output.txt");

        getOperators();
        getExpressions();
    }

    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();

        evaluator.run();
    }

}
