package pl.bdaf.person;

public enum PersonStatistic{
    TO_TEST(10,10,10,10),
    TED(30,5,7,20),
    MOMMY(22, 4,6, 15),
    TIMMY(15, 4, 5, 10),
    BERTA(24, 3, 6, 15);

    int strength;
    int hydrationPoints;
    int satietyPoints;
    int cheerfulness;

    PersonStatistic(int aStrength, int aHydrationPoints, int aSatietyPoints, int aCheerfulness) {
        strength = aStrength;
        hydrationPoints = aHydrationPoints;
        satietyPoints = aSatietyPoints;
        cheerfulness = aCheerfulness;
    }
}
