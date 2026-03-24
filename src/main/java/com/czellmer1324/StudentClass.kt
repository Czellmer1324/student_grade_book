package com.czellmer1324

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

class StudentClass {
    private val students = HashMap<String, Student>()
    private var avgGPA : Float = 0.00f
    private var topStudent : Student? = null
    private var bottomStudent: Student? = null
    private val COMMA_DELIMITER = ","


    init {
        loadClass()
    }

    private fun loadClass() {
        loadStudents()
        loadAssignments()
    }

    private fun loadStudents() {
        val studentInfo = ArrayList<List<String>>()
        val reader = BufferedReader(FileReader("class.csv"))
        var line = reader.readLine()

        while (line != null) {
            val values = line.split(COMMA_DELIMITER)
            studentInfo.add(values)
            line = reader.readLine()
        }

        reader.close()

        if (studentInfo.isEmpty()) return

        var total = 0.00f

        studentInfo.forEach { info ->
            val s = Student(info[0], info[1].toFloat())
            students.putIfAbsent(info[0], s)

            total += s.gpa

            if (topStudent == null) topStudent = s
            else if (s.gpa > topStudent!!.gpa) {
                topStudent = s
            }

            if (bottomStudent == null) bottomStudent = s
            if (s.gpa < bottomStudent!!.gpa) {
                bottomStudent = s
            }
        }

        avgGPA = total / students.size
    }

    private fun loadAssignments() {
        if (students.isEmpty()) return

        val assignmentInfo = ArrayList<List<String>>()
        val reader = BufferedReader(FileReader("assignments.csv"))
        var line = reader.readLine()

        while (line != null) {
            val value = line.split(COMMA_DELIMITER)
            assignmentInfo.add(value)
            line = reader.readLine()
        }

        reader.close()

        assignmentInfo.forEach { info ->
            val studentName = info[0]
            val assignment = Assignment(studentName, info[1], info[2].toInt())
            students[studentName]?.loadAssignment(assignment)
        }
    }

    fun addStudent(name : String) {
        val stu = Student(name, 0.00f)
        students.putIfAbsent(name, stu)
        saveInfo()
    }

    fun addAssignment(name : String, assignmentName : String, grade : Int) : Boolean = runBlocking {
        val stu = students[name] ?: return@runBlocking false

        val assignment = Assignment(stu.name, assignmentName, grade)
        stu.addAssignment(assignment)
        launch(Dispatchers.Default) {
            calculateGpa()
        }

        saveInfo()

        true
    }

    fun calculateGpa() {
        var total = 0.00f
        students.forEach { (string, student) ->
            val gpa = student.gpa
            total += gpa
            if (gpa > (topStudent?.gpa ?: Float.NEGATIVE_INFINITY)) topStudent = student
            else if (gpa < (bottomStudent?.gpa ?: Float.MAX_VALUE)) bottomStudent = student
        }

        avgGPA = total / students.size
    }

    fun viewTopStudent() : Student? {
        return topStudent
    }

    fun viewBottomStudent() : Student? {
        return bottomStudent
    }

    fun removeStudent(name: String) {
        students.remove(name)
        saveInfo()
    }

    fun viewGPA() : String{
        return String.format("%.2f", avgGPA)
    }

    fun viewStudents() : Collection<Student> {
        return students.values
    }

    fun viewStudent(name: String) : Student? {
        return students[name]
    }

    fun getStudentNames() : Set<String> {
        return students.keys
    }

    fun saveInfo() = runBlocking {
        launch(Dispatchers.IO) {
            val assignmentWriter = BufferedWriter(FileWriter("assignments.csv"))
            val studentWriter = BufferedWriter(FileWriter("class.csv"))
            val assignments = ArrayList<String>()
            val studentList = ArrayList<String>()
            students.forEach { (name, student) ->
                student.getAssignments().forEach { a ->
                    val builder = StringBuilder()
                    builder.append(a.studentName)
                    builder.append(',')
                    builder.append(a.assignmentName)
                    builder.append(',')
                    builder.append(a.grade)
                    assignments.add(builder.toString())
                }
                val builder = StringBuilder()
                builder.append(name)
                builder.append(',')
                builder.append(student.gpa)
                studentList.add(builder.toString())
            }

            assignments.forEach { string ->
                assignmentWriter.write(string)
                assignmentWriter.newLine()
            }

            studentList.forEach { string ->
                studentWriter.write(string)
                studentWriter.newLine()
            }

            assignmentWriter.close()
            studentWriter.close()

        }
    }
}