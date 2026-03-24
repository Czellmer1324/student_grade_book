package com.czellmer1324;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Scanner sc = new Scanner(System.in);
        StudentClass stuClass = new StudentClass();
        IO.println("Hello");
        stuClass.addStudent("Cody");
        stuClass.addStudent("Jenna");
        stuClass.viewStudents().forEach((s, student) -> {
            IO.println(s);
            IO.println(student.getGpa());
        });

        IO.println(stuClass.viewGPA());
        IO.println("Select the student you want to add a grade for");
        HashMap<String, Student> students = stuClass.viewStudents();
        Set<String> names = students.keySet();

        int counter = 1;
        for (String name : names) {
            IO.println(counter + ": " + name);
            counter++;
        }

        String name = sc.nextLine();
        stuClass.addAssignment(students.get(name), "random", 93);

        IO.println(stuClass.viewGPA());

    }
}
