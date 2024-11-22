package org.example.lab3.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassContainer {
    private final Map<String, ClassTeacher> teachersGroups = new HashMap<>();

    public Map<String, ClassTeacher> getTeachersGroups() { return teachersGroups; }

    public ClassTeacher getClassTeacher(String group) {
        return teachersGroups.get(group);
    }

    public void addClass(String name, int capacity) {
        if (teachersGroups.containsKey(name))
            throw new IllegalArgumentException("Klasa o takiej nazwie juz istnieje");

        ClassTeacher temporaryClass = new ClassTeacher(name, capacity);
        teachersGroups.put(name, temporaryClass);
    }

    public void removeClass(String name) {
        teachersGroups.remove(name);
    }

    public List<String> findEmpty() {
        List<String> emptyClasses = new ArrayList<>();

        for (Map.Entry<String, ClassTeacher> entry : teachersGroups.entrySet()) {
            if (entry.getValue().count() == 0)
                emptyClasses.add(entry.getKey());
        }

        return emptyClasses;
    }

    public void summary() {
        for (Map.Entry<String, ClassTeacher> entry : teachersGroups.entrySet()) {
            double fillPercentage = (double) entry.getValue().count() / (double) entry.getValue().getMaxTeachersCapacity() * 100;
            System.out.println(entry.getKey() + ": " + fillPercentage + "%");
        }
    }

    // ClassTeacher API

    public void addTeacher(String group, Teacher teacher) {
        if (!teachersGroups.containsKey(group)) {
            throw new IllegalArgumentException("Grupa o takiej nazwie juz istnieje");
        }

        teachersGroups.get(group).addTeacher(teacher);
    }

    public void removeTeacher(String group, Teacher teacher) {
        teachersGroups.get(group).removeTeacher(teacher);
    }

    public void addSalary(String group, Teacher teacher, double salary) {
        teachersGroups.get(group).addSalary(teacher, salary);
    }

    public void changeCondition(String group, Teacher teacher, TeacherConditionEnum condition) {
        teachersGroups.get(group).changeCondition(teacher, condition);
    }

    public Teacher search(String group, String surname) {
        return teachersGroups.get(group).search(surname);
    }

    public List<Teacher> searchPartial(String group, String str) {
        return teachersGroups.get(group).searchPartial(str);
    }

    public int countByCondition(String group, TeacherConditionEnum condition) {
        return teachersGroups.get(group).countByCondition(condition);
    }

    public List<Teacher> sortByName(String group) {
        return teachersGroups.get(group).sortByName();
    }

    public List<Teacher> sortBySalary(String group) {
        return teachersGroups.get(group).sortBySalary();
    }

    public Teacher max(String group) {
        return teachersGroups.get(group).max();
    }

    public void summary(String group) {
        teachersGroups.get(group).summary();
    }
}
