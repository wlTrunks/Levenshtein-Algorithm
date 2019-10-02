import org.junit.jupiter.api.Test;

/**
 * I'm taking time performance test
 */
class LevenshteinAlgorithmTest {

    private final static String HAUS = "Haus";
    private final static String MAUS = "Maus";
    private final static String MAUSI = "Mausi";
    private final static String HAUSER = "Häuser";
    private final static String TOKEN_LONG_1 = "Kartoffelsalat";
    private final static String TOKEN_LONG_2 = "Runkelrüben";
    private final static String TOKEN_LONG_3= "Buskdoooled";
    private final static String ORIGINAL = "original";
    private final static String OPTIMIZED = "opt";
    private final static String MAX_DESTINATION = "max destination";

    @Test
    public void Haus_Maus_Test() {
        long original = runTest(ORIGINAL, () -> LevenshteinAlgorithm.levenstein(HAUS, MAUS));
        long opt = runTest(OPTIMIZED, () -> LevenshteinAlgorithm.levensteinOptimazed(HAUS, MAUS));
        long max = runTest(MAX_DESTINATION, () -> LevenshteinAlgorithm.levensteinOptimazedMax(HAUS, MAUS, 2));
        printTimeResult(original, opt, max);
    }


    @Test
    public void Haus_Mausi_Test() {
        long original = runTest(ORIGINAL, () -> LevenshteinAlgorithm.levenstein(HAUS, MAUSI));
        long opt = runTest(OPTIMIZED, () -> LevenshteinAlgorithm.levensteinOptimazed(HAUS, MAUSI));
        long max = runTest(MAX_DESTINATION, () -> LevenshteinAlgorithm.levensteinOptimazedMax(HAUS, MAUSI, 2));
        printTimeResult(original, opt, max);
    }

    @Test
    public void Haus_Hauser_Test() {
        long original = runTest(ORIGINAL, () -> LevenshteinAlgorithm.levenstein(HAUS, HAUSER));
        long opt = runTest(OPTIMIZED, () -> LevenshteinAlgorithm.levensteinOptimazed(HAUS, HAUSER));
        long max = runTest(MAX_DESTINATION, () -> LevenshteinAlgorithm.levensteinOptimazedMax(HAUS, HAUSER, 2));
        printTimeResult(original, opt, max);
    }

    @Test
    public void Kartoffelsalat_Runkelruben_Test() {
        long original = runTest(ORIGINAL, () -> LevenshteinAlgorithm.levenstein(TOKEN_LONG_1, TOKEN_LONG_2));
        long opt = runTest(OPTIMIZED, () -> LevenshteinAlgorithm.levensteinOptimazed(TOKEN_LONG_1, TOKEN_LONG_2));
        long max = runTest(MAX_DESTINATION, () -> LevenshteinAlgorithm.levensteinOptimazedMax(TOKEN_LONG_1, TOKEN_LONG_2, 2));
        printTimeResult(original, opt, max);
    }
    @Test
    public void Basddooolsd_Runkelruben_Test() {
        long original = runTest(ORIGINAL, () -> LevenshteinAlgorithm.levenstein(TOKEN_LONG_3, TOKEN_LONG_2));
        long opt = runTest(OPTIMIZED, () -> LevenshteinAlgorithm.levensteinOptimazed(TOKEN_LONG_3, TOKEN_LONG_2));
        long max = runTest(MAX_DESTINATION, () -> LevenshteinAlgorithm.levensteinOptimazedMax(TOKEN_LONG_3, TOKEN_LONG_2, 4));
        printTimeResult(original, opt, max);
    }

    private void printTimeResult(long original, long opt, long max) {
        System.out.println(String.format("%s = %d", ORIGINAL, original));
        System.out.println(String.format("%s = %d", OPTIMIZED, opt));
        System.out.println(String.format("%s = %d", MAX_DESTINATION, max));
    }

    private long runTest(String name, Performance performance) {
        long start = System.nanoTime();
        int destination = performance.runTest();
        System.out.println(String.format("%s result = %d", name, destination));
        return System.nanoTime() - start;
    }

    interface Performance {
        int runTest();
    }
}
