package com.example.courseManager.model


class Model {

    val courses = mutableListOf<Course>()

    fun addCourse(course: Course) {
        courses.add(course)
    }

    fun deleteCourse(code: String) {
        val deleteList = mutableListOf<Course>()
        courses.forEach { // avoid the concurrent modification exception
            if (it.courseCode == code) {
                deleteList.add(it)
            }
        }
        courses.removeAll(deleteList)
    }

    fun editCourse(code: String, name : String, term : String, mark : String) {
        courses.forEach {
            if (it.courseCode == code) {
                it.courseName = name
                it.courseTerm = term
                it.courseGradeStr = mark

                if (mark == "WD") {
                    it.courseGradeInt = -1
                } else {
                    it.courseGradeInt = mark.toInt()
                }

            }
        }
    }

    fun sortCourse(sortType : String) {

        when (sortType) {
            "By Course Code" -> courses.sortBy { it.courseCode }
            "By Term" ->  {
                val termList = listOf<String>("F20", "W21", "S21", "F21", "W22", "S22", "F22", "W23")
                courses.sortBy { termList.indexOf(it.courseTerm) }
            }
            "By Mark" -> courses.sortByDescending { it.courseGradeInt }
        }

    }

    fun filterCourse(filterType : String) : MutableList<Course> {
        val filteredCourseList : MutableList<Course> = mutableListOf()
        when (filterType) {
            "All Courses" -> {
                courses.forEach {
                    filteredCourseList.add(it)
                }
            }
            "CS Only" -> {
                courses.forEach {
                    if (it.courseCode.startsWith("CS")) {
                        filteredCourseList.add(it)
                    }
                }
            }
            "Math Only" -> {
                courses.forEach {
                    if (it.courseCode.startsWith("MATH")) {
                        filteredCourseList.add(it)
                    }
                }
            }
            "Other Only" -> {
                courses.forEach {
                    if (!((it.courseCode.startsWith("CS")) || (it.courseCode.startsWith("MATH")))) {
                        filteredCourseList.add(it)
                    }
                }
            }
        }

        return filteredCourseList
    }

    fun clear() {
        courses.clear()
    }


}
