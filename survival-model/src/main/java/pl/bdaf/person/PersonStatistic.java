package pl.bdaf.person;

public enum PersonStatistic{
    TO_TEST(10,10,10,10,"TestName"),
    TO_TEST_MIN(1,1,1,1,"TestNameMin"),

    TED(30,5,7,20, Constants.TED),
    DOLORES(22, 4,6, 15, Constants.DOLORES),
    TIMMY(15, 4, 5, 10, Constants.TIMMY),
    BERTA(24, 3, 6, 15, Constants.BERTA);

    int strength;
    int hydrationPoints;
    int satietyPoints;
    int cheerfulness;
    String name;

    PersonStatistic(int aStrength, int aHydrationPoints, int aSatietyPoints, int aCheerfulness, String aName) {
        strength = aStrength;
        hydrationPoints = aHydrationPoints;
        satietyPoints = aSatietyPoints;
        cheerfulness = aCheerfulness;
        name = aName;
    }

    public String getName() {
        return name;
    }

    public static class Constants {
        public static final String TED = "ted";
        public static final String DOLORES = "dolores";
        public static final String TIMMY = "timmy";
        public static final String BERTA = "berta";
    }
}
