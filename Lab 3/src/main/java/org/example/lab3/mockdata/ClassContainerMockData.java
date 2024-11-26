package org.example.lab3.mockdata;

import org.example.lab3.core.ClassContainer;
import org.example.lab3.core.Teacher;
import org.example.lab3.core.TeacherConditionEnum;

public class ClassContainerMockData {
    static public ClassContainer GetMockData() {
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

        return classContainer;
    }
}
