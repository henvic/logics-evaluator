/**
 * @author Henrique Vicente
 * @author Rodrigo Alves
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

class Answer {
    boolean verdict;
    int subExpressions;
    int height;

    public Answer(boolean verdict) {
        this.verdict = verdict;
        this.subExpressions = 0;
        this.height = 0;
    }

    public boolean isTrue() {
        return this.verdict == true;
    }
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

    public static Answer isWFF(String expr, HashMap<Character, Table> tableSet) {
        Answer answer = new Answer(true);

        Stack<String> brackets = new Stack<String>();
        Stack<String> operators = new Stack<String>();

        String subExpr;
        ArrayList<String> subExpressions = new ArrayList<String>();

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
                        subExpr = top + "";

                        // Check whether the element at the top is an operator or an atom
                        if (tableSet.get(top) != null) {

                            if (tableSet.get(top).arity == 2) {
                                operators.pop();
                                subExpr += top;
                            }

                            if (operators.size() > 0) {
                                operators.pop();
                            } else {
                                answer.verdict = false;
                                break;
                            }

                        } else {
                            answer.verdict = false;
                            break;
                        }

                        subExpressions.add(subExpr);
                    } else {
                        answer.verdict = false;
                        break;
                    }

                    if (brackets.size() > 0) brackets.pop();

                } else {
                    answer.verdict = false;
                    break;
                }

            } else {
                operators.push(current + "");
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Arquivo io = new Arquivo("input.txt", "output.txt");

        int N, arity, expressions;
        char operator;
        HashMap<Character, Table> tableSet = new HashMap<Character, Table>();
        Table table;
        Answer answer;

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

            answer = isWFF(expr, tableSet);

            if (answer.isTrue()) {
                io.println("Expressao bem-formada");
                io.println("Altura=" + answer.height);
                io.println("Sub-expressoes=" + answer.subExpressions);

                // getTruthValuesFor(expr, io, tableSet);
            } else {
                io.println("Expressao mal-formada");
            }

            io.println();
        }
    }

}
