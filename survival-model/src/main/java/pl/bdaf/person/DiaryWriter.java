package pl.bdaf.person;

import java.util.Random;

public class DiaryWriter {
    private static Random rand = new Random();

    static String describe(Person aPerson){
        StringBuilder diary = new StringBuilder();
        diary.append(aPerson.getName() + aPerson.getState().getDescribe());
        if (aPerson.getStrength() < 10) diary.append(aPerson.getName() + randomThreeStrings(" is exhausted.\n"," is really tired.\n"," have almost no strength.\n"));
        if (aPerson.getHydrationPoints() < 2) diary.append(aPerson.getName() + randomThreeStrings(" won't survive next day without water!\n"," can't live longer without water!\n"," will die soon of dryness...\n"));
        else if(aPerson.getHydrationPoints() < 3) diary.append(aPerson.getName() + randomThreeStrings(" is really thirsty.\n"," would gladly drink glass of water.\n"," realised that she wants water.\n"));
        if (aPerson.getSatietyPoints() < 3) diary.append(aPerson.getName() + randomThreeStrings(" is starving! Next day lived would be a miracle!\n"," will die soon because of lack of food...\n"," can't make next day without food!\n"));
        else if (aPerson.getSatietyPoints() < 5) diary.append(aPerson.getName() + randomThreeStrings(" would gladly eat anything.\n"," dreams about tasty meal.\n"," realised that she wants even this soup.\n"));
        if (aPerson.getCheerfulness() < 5) diary.append(aPerson.getName() + randomThreeStrings(" will go crazy in this shelter!\n"," is going to go for expedition or die....\n", " will soon die of boredom!\n"));
        else if (aPerson.getCheerfulness() < 10) diary.append( aPerson.getName() +  randomThreeStrings(" is bored.\n"," wants to go for expedition.\n", " would gladly do anything.\n"));
        return diary.toString();
    }

    static String describeDeadPerson(Person aDeadPerson) {
        if(aDeadPerson.isAlive())
            return null;
        return aDeadPerson.getName() + aDeadPerson.getState().getDescribe()+aDeadPerson.getDeadDay()+"...\n";
    }

    static String describeExpeditionDay(Person aPerson) {
        return aPerson.getName() + " is on expedition.\n";
    }


    private static String randomThreeStrings(String aS1, String aS2, String aS3){
        int numberToRandom = rand.nextInt(3);
        if(numberToRandom == 0) return aS1;
        else if(numberToRandom == 1) return aS2;
        return aS3;
    }
}
