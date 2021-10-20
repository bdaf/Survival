package pl.bdaf.person;

public class DiaryWriter {
    static String describe(Person aPerson){
        String diary = "";
        if (aPerson.getStrength() < 10) diary += aPerson.getName() + " is exhausted!\n";
        if (aPerson.getHydrationPoints() < 3) diary += aPerson.getName() + " is really thirsty!\n";
        else if(aPerson.getHydrationPoints() < 2) diary += aPerson.getName() + " won't survive next day without water!\n";
        if (aPerson.getSatietyPoints() < 5) diary += aPerson.getName() + " would gladly eat anything...\n";
        else if (aPerson.getSatietyPoints() < 3) diary += aPerson.getName() + " is starving! Next lived day would be a miracle!\n";
        if (aPerson.getCheerfulness() < 10) diary += aPerson.getName() + " is bored..!\n";
        else if (aPerson.getCheerfulness() < 5) diary += aPerson.getName() + " will soon die of boredom..\n";
        diary += aPerson.getName() + aPerson.getState().getDescribe();
        return diary;
    }

    static String describeDeadPerson(Person aDeadPerson) {
        if(aDeadPerson.isAlive())
            return null;
        return aDeadPerson.getName() + aDeadPerson.getState().getDescribe()+aDeadPerson.getDeadDay()+"...";
    }
}
