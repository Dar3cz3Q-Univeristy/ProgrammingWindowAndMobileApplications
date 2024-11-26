package org.example.lab3.core;

import java.util.Comparator;

public class Teacher implements Comparable<Teacher> {
    private final String name;
    private final String surname;
    private TeacherConditionEnum condition;
    private final int yearOfBirth;
    private double salary;

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public TeacherConditionEnum getCondition() {
        return condition;
    }

    public void setCondition(TeacherConditionEnum condition) {
        this.condition = condition;
    }

    public String getSurname() {
        return surname;
    }

    public Teacher(String name, String surname, TeacherConditionEnum condition, int yearOfBirth, double salary) {
        this.name = name;
        this.surname = surname;
        this.condition = condition;
        this.yearOfBirth = yearOfBirth;
        this.salary = salary;
    }

    public void printing() {
        System.out.println("Imie nauczyciela: " + name);
        System.out.println("Nazwisko nauczyciela: " + surname);
        System.out.println("Stan nauczyciela: " + condition.toString());
        System.out.println("Rok urodzenia: " + yearOfBirth);
        System.out.println("Wynagrodzenie: " + salary);
    }

    public void updateSalary(double salary) {
        this.salary += salary;
    }

    public void updateCondition(TeacherConditionEnum condition) {
        this.condition = condition;
    }

    @Override
    public int compareTo(Teacher o) {
        return Comparators.SURNAME.compare(this, o);
    }

    public boolean equals(Teacher o) {
        if (!this.surname.equals(o.surname)) return false;
        if (!this.name.equals(o.name)) return false;
        return this.yearOfBirth == o.yearOfBirth;
    }

    public int compare(String surname) {
        return this.surname.compareTo(surname);
    }

    public int compare(TeacherConditionEnum condition) {
        return this.condition.compareTo(condition);
    }

    public boolean contains(String str) {
        return this.surname.contains(str) || this.name.contains(str);
    }

    public static class Comparators {

        public static Comparator<Teacher> NAME = Comparator.comparing(o -> o.name);

        public static Comparator<Teacher> SURNAME = Comparator.comparing(o -> o.surname);

        public static Comparator<Teacher> SALARY = (o1, o2) -> (int) (o1.salary - o2.salary);
    }
}
