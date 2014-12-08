/**
 * @author Henrique Vicente
 * @author Rodrigo Alves
 */

import java.util.HashMap;

class Table {
    int arity;
    int[][] matrix;

    public Table(int arity) {
        this.arity = arity;
        this.matrix = new int[(int) Math.pow(2, arity)][arity+1];
    }
}

public class Evaluator {

    /**
     * Returns true if the expression is well-formed. Returns false otherwise.
     */
    public static boolean isWFF(String expr) {
        return true;
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

            exprDB[i] = expr;
        }
    }

}
