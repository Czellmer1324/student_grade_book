package com.czellmer1324;

public class Assignment {
    private String studentName;
    private String assignmentName;
    private int grade;

    public Assignment(String studentName, String assignmentName, int grade) {
        this.studentName = studentName;
        this.assignmentName = assignmentName;
        this.grade = grade;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }
}
