package com.czellmer1324

class Student (val name : String, var gpa : Float) {
    private val assignments = ArrayList<Assignment>()

    fun addAssignment(assignment : Assignment) {
        assignments.add(assignment)
        gpa += ((getGpaWeight(assignment.grade) - gpa) / assignments.size)
    }

    private fun getGpaWeight(grade : Int) : Float {
        return when (grade) {
            in 93..100 -> 4.00f
            in 90..92 -> 3.67f
            in 87..90 -> 3.33f
            in 83..86 -> 3.00f
            in 80..82 -> 2.67f
            in 77..79 -> 2.33f
            in 73..76 -> 2.00f
            in 70..72 -> 1.667f
            in 67..69 -> 1.33f
            in 60..66 -> 1.00f
            else -> 0.00f
        }
    }

    fun loadAssignment(assignment : Assignment) {
        assignments.add(assignment)
    }

    fun getAssignments() : ArrayList<Assignment> {
        return assignments
    }
}