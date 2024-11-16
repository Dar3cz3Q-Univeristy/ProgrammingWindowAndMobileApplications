package org.example;

public class Main {
    public static void main(String[] args) {
        ClassContainer classContainer = new ClassContainer();
        classContainer.addClass("Group 1", 8);
        classContainer.addClass("Group 2", 3);
        classContainer.addClass("Group 3", 5);
        classContainer.addClass("Group 4", 10);

        Teacher teacher1 = new Teacher("Dariusz", "Homa", TeacherConditionEnum.OBECNY, 2002, 1000);
        Teacher teacher2 = new Teacher("Adrian", "Jamrogiewicz", TeacherConditionEnum.DELEGACJA, 2002, 3600);
        Teacher teacher3 = new Teacher("Tomasz", "JaGacek", TeacherConditionEnum.CHORY, 2002, 5000);
        Teacher teacher4 = new Teacher("Anna", "Nowak", TeacherConditionEnum.OBECNY, 1998, 4500);
        Teacher teacher5 = new Teacher("Marcin", "Kowalski", TeacherConditionEnum.DELEGACJA, 2005, 5200);
        Teacher teacher6 = new Teacher("Joanna", "Krawczyk", TeacherConditionEnum.CHORY, 2010, 4800);
        Teacher teacher7 = new Teacher("Piotr", "Zielinski", TeacherConditionEnum.NIEOBECNY, 2015, 5100);

        classContainer.addTeacher("Group 1", teacher1);
        classContainer.addTeacher("Group 1", teacher2);
        classContainer.addTeacher("Group 1", teacher3);
        classContainer.addTeacher("Group 1", teacher4);

        classContainer.addTeacher("Group 2", teacher4);
        classContainer.addTeacher("Group 2", teacher5);
        classContainer.addTeacher("Group 2", teacher6);

        classContainer.addTeacher("Group 3", teacher7);

        System.out.println("\nProcentowe zapelenienie grup:");
        classContainer.summary();

        System.out.println("\nPuste grupy:");
        for (String group : classContainer.findEmpty()) System.out.println("* " + group);

        classContainer.addSalary("Group 1", teacher1, 1000);
        classContainer.changeCondition("Group 1", teacher4, TeacherConditionEnum.NIEOBECNY);

        System.out.println("\nLista po zaktualizowaniu wyplaty i stanu grupy 1\n");
        classContainer.summary("Group 1");

        Teacher foundTeacher = classContainer.search("Group 1", "Nowak");
        System.out.println("\nZnaleziony nauczyciel po nazwisku\n");
        if (foundTeacher != null) foundTeacher.printing();
        else System.out.println("Nauczyciel o takim nazwisku nie istnieje na liscie");

        var foundTeachersList = classContainer.searchPartial("Group 1", "Ja");
        System.out.println("\nLista nauczycieli po przeszukiwaniu czesciowym\n");
        for(Teacher teacher : foundTeachersList) teacher.printing();

        int presentTeachers = classContainer.countByCondition("Group 3", TeacherConditionEnum.OBECNY);
        System.out.println("\nIlosc obecnych nauczycieli: " + presentTeachers + "\n");

        var sortedListByName = classContainer.sortByName("Group 1");
        System.out.println("\nPosortowana lista nauczycieli ze wzgledu na imie\n");
        for (Teacher teacher : sortedListByName) teacher.printing();

        var sortedListBySalary = classContainer.sortBySalary("Group 1");
        System.out.println("\nPosortowana lista nauczycieli ze wzgledu na wyplate\n");
        for (Teacher teacher : sortedListBySalary) teacher.printing();

        System.out.println("\nNauczyciel ktory zarabia najwiecej\n");
        Teacher maxSalaryTeacher = classContainer.max("Group 2");
        maxSalaryTeacher.printing();
    }
}