package core;

/**
 * This class contains data of:
 *
 * 1-Unsuited 5-combination scores, 2-Pascal Triangle.
 *
 * The usefulness of this class is to compute all values they contain only once,
 * so there is no need to restart calculations of these values if asked
 * repeatedly.
 *
 * @author desharnc27
 */
public class DataForNumbers {

    private static int[][] pascalTriangle;
    private static UnsuitedHFCombo[] list5;

    /**
     * Fills scores of unsuited combination scores and Pascal Triangle
     *
     */
    public static void proceed() {
        createPascal();
        create5ScoreList();
    }

    /**
     * Fills Pascal triangle up to a value that will be enough for all
     * calculations of this project.
     *
     */
    public static void createPascal() {
        int max = 25;
        pascalTriangle = new int[max][max];
        pascalTriangle[0][0] = 1;
        for (int i = 1; i < max; i++) {
            pascalTriangle[i][i] = 1;
            pascalTriangle[i][0] = 1;
            for (int j = 1; j < i; j++) {
                pascalTriangle[i][j] = pascalTriangle[i - 1][j - 1] + pascalTriangle[i - 1][j];
            }
        }
    }

    /**
     * Fills scores of unsuited combination scores
     *
     */
    public static void create5ScoreList() {
        list5 = UnsuitedMeths.fill5();
    }

    /**
     * Returns C(i,j)
     *
     * @param i
     * @param j
     * @return C(i,j)
     */
    public static int getPascal(int i, int j) {
        return pascalTriangle[i][j];
    }

    /**
     * Returns the score of unsuited 5-combo with identifier idx
     *
     * @param idx
     * @return the score of unsuited 5-combo with identifier idx
     */
    public static float getScore5(int idx) {
        return list5[idx].score;
    }

    /**
     * Returns the score (without counting flush points) of unsuited 5-combo
     * with identifier idx
     *
     * @param idx
     * @return the score (without counting flush points) of unsuited 5-combo
     * with identifier idx
     */
    public static float getScore5WithoutFlush(int idx) {
        return list5[idx].scoreWithoutFlush;
    }
}
