package org.example;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();

        db.createUser("John Doe", "john.doe@example.com");
        db.readUsers();
        db.updateUser(1, "Jane Doe", "jane.doe@example.com");
        db.deleteUser(1);
        db.readUsers();
    }
}