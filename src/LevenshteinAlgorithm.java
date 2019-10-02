public class LevenshteinAlgorithm {

    /**
     * Classical implementation of algorithm
     * https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
     */
    public static int levenstein(String token1, String token2) {
        int[][] dp = new int[token1.length() + 1][token2.length() + 1];

        for (int i = 0; i <= token1.length(); i++)
            dp[i][0] = i;
        for (int j = 1; j <= token2.length(); j++)
            dp[0][j] = j;

        for (int i = 1; i <= token1.length(); i++)
            for (int j = 1; j <= token2.length(); j++)
                dp[i][j] = minimum(
                        dp[i - 1][j] + 1,
                        dp[i][j - 1] + 1,
                        dp[i - 1][j - 1] + costOfSubstitution(((token1.charAt(i - 1))), token2.charAt(j - 1)));

        return dp[token1.length()][token2.length()];
    }

    /**
     * Optimized version of algorithm
     */
    public static int levensteinOptimazed(String token1, String token2) {
        //we creating only 1 array, fill first column and move from left to right filling it
        int[] vc = new int[token1.length() + 1];
        vc[0] = 1; // setting first column
        //init first column of matrix
        for (int i = 1; i <= token1.length(); i++) {
            int cost = costOfSubstitution(token1.charAt(i - 1), token2.charAt(0)); //compare with char 0 token2
            int min = Math.min(vc[i - 1], i - 1);//[i-1] - above left, i-1 left
//            System.out.println("char t1 = " + token1.charAt(i - 1) + " char t2 = " + token2.charAt(0) + " cost = " + cost + " min = " + min);
            vc[i] = min + cost;
        }

        int rowNumber = 0; //it's row number of matrix
        //proceed compare by setting row of matrix and fill it. where 'i' is a column number
        for (int i = 1; i < token2.length(); i++) {
            char ch2 = token2.charAt(i);
            rowNumber = i + 1; // setting row number
//            for (int b = 0; b < vc.length; b++) {
//                System.out.println("vc[" + b + "] = " + (vc[b]));
//            }
            for (int j = 1; j <= token1.length(); j++) {
                int cost = costOfSubstitution(token1.charAt(j - 1), ch2);
                int min = minimum(rowNumber, vc[j - 1], vc[j]);
                int value = min + cost;
//                System.out.println("char t1 = " + token1.charAt(j - 1) + " char t2 = " + ch2 + " cost = " + cost + " min = " + min);
                vc[j - 1] = rowNumber; // we writing for previous row value
//                System.out.println("rowNumber = " + rowNumber + " value = " + value);
                rowNumber = value; // setting next row
            }
//            System.out.println("distance " + rowNumber);
            vc[token1.length()] = rowNumber;
        }
        //so in the end we have last column of our matrix
        return vc[token1.length()];
    }

    /**
     * Optimized version of algorithm with early exit point
     */
    public static int levensteinOptimazedMax(String token1, String token2, int maxDistance) {
        int[] vc = new int[token1.length() + 1];

        vc[0] = 1;
        for (int i = 1; i <= token1.length(); i++) {
            int cost = costOfSubstitution(token1.charAt(i - 1), token2.charAt(0));
            int min = Math.min(vc[i - 1], i - 1);
            vc[i] = min + cost;
        }
        int rowNumber = 0;
        int smallest = 0; //here is the smallest value in column
        for (int i = 1; i < token2.length(); i++) {
            char ch2 = token2.charAt(i);
            rowNumber = i + 1;
            for (int j = 1; j <= token1.length(); j++) {
                int cost = costOfSubstitution(token1.charAt(j - 1), ch2);
                int min = minimum(rowNumber, vc[j - 1], vc[j]);
                int value = min + cost;
                vc[j - 1] = rowNumber;
                if (value < rowNumber) smallest = value; // writing smallest value in column
//                System.out.println("rowNumber = " + rowNumber + " value = " + value + " smallest = " + smallest);
                rowNumber = value;
            }
            if (smallest > maxDistance + 1) return maxDistance + 1; //checking smallest distance with maxDistance
            vc[token1.length()] = rowNumber;
        }
        return vc[token1.length()];
    }

    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private static int minimum(int n1, int n2, int n3) {
        return Math.min(Math.min(n1, n2), n3);
    }
}
