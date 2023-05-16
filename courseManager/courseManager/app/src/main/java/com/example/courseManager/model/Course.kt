package com.example.courseManager.model

class Course(var courseCode: String, var courseName: String, var courseTerm: String, var courseGradeStr: String, var courseGradeInt: Int = 0) {

    init {
        courseGradeInt = if (courseGradeStr == "WD") -1 else courseGradeStr.toInt()
        println("Course: $courseCode Name: $courseName Term: $courseTerm Grade: $courseGradeStr Created!")
    }

    fun updateGrade(grade: String) {
        courseGradeStr = grade
        courseGradeInt = if (courseGradeStr == "WD") -1 else courseGradeStr.toInt()

        println("Course: $courseCode Name: $courseName Term: $courseTerm Grade: $courseGradeStr UPDATED!")
    }

    override fun toString(): String {
        return ("Course: $courseCode Name: $courseName Term: $courseTerm Grade: $courseGradeStr")
    }
}