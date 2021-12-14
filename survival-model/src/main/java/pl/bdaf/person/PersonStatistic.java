package pl.bdaf.person;

enum PersonStatistic{
    TO_TEST(10,10,10,10,"TestName"),
    TO_TEST_MIN(1,1,1,1,"TestNameMin"),

    TED(30,5,7,20, Person.Constants.TED),
    DOLORES(22, 4,6, 15, Person.Constants.DOLORES),
    TIMMY(15, 4, 5, 10, Person.Constants.TIMMY),
    BERTA(24, 3, 6, 15, Person.Constants.BERTA);

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

}
