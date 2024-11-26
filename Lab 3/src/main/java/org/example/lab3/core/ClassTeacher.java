package org.example.lab3.core;

import java.util.*;

public class ClassTeacher {
    private final String teacherGroupName;
    private final Set<Teacher> teachersList = new HashSet<>();
    private final int maxTeachersCapacity;

    public ClassTeacher(String teacherGroupName, int maxTeachersCapacity) {
        this.teacherGroupName = teacherGroupName;
        this.maxTeachersCapacity = maxTeachersCapacity;
    }

    public int Fill() {
        return teachersList.size();
    }

    public void addTeacher(Teacher teacher) {
        int listSize = teachersList.size();

        if (listSize >= maxTeachersCapacity) {
            System.out.println("List nauczycieli jest pelna");
            return;
        }

        if (getTeacherFromList(teacher) != null) {
            System.out.println("Nauczyciel juz istnieje");
            return;
        }

        teachersList.add(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        teachersList.remove(teacher);
    }

    public void addSalary(Teacher teacher, double salary) {
        teacher.updateSalary(salary);
    }

    public void changeCondition(Teacher teacher, TeacherConditionEnum condition) {
        teacher.updateCondition(condition);
    }

    public Teacher search(String surname) {

        for (Teacher teacher1 : teachersList) {
            if (teacher1.compare(surname) == 0)
                return teacher1;
        }

        return null;
    }

    public List<Teacher> searchPartial(String str) {
        List<Teacher> result = new ArrayList<>();

        for (Teacher teacher1 : teachersList) {
            if (teacher1.contains(str))
                result.add(teacher1);
        }

        return result;
    }

    public int countByCondition(TeacherConditionEnum condition) {
        int result = 0;

        for (Teacher teacher1 : teachersList) {
            if (teacher1.compare(condition) == 0)
                result++;
        }

        return result;
    }

    public List<Teacher> sortByName() {
        var teachersList = new ArrayList<>(this.teachersList);
        teachersList.sort(Teacher.Comparators.NAME);
        return teachersList;
    }

    public List<Teacher> sortBySalary() {
        var teachersList = new ArrayList<>(this.teachersList);
        teachersList.sort(Teacher.Comparators.SALARY);
        return teachersList.reversed();
    }

    public Teacher max() {
        var teachersList = new ArrayList<>(this.teachersList);
        return Collections.max(teachersList, Teacher.Comparators.SALARY);
    }

    public int count() {
        return teachersList.size();
    }

    public int getMaxTeachersCapacity() {
        return maxTeachersCapacity;
    }

    private Teacher getTeacherFromList(Teacher teacher) {
        for (Teacher teacher1 : teachersList) {
            if (teacher1.equals(teacher))
                return teacher1;
        }

        return null;
    }

    public void summary() {
        System.out.println(teacherGroupName);
        for (Teacher teacher : teachersList) teacher.printing();
    }
}
