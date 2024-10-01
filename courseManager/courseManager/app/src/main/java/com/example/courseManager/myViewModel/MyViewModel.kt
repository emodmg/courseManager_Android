package com.example.courseManager.myViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courseManager.model.Course
import com.example.courseManager.model.Model

class MyViewModel : ViewModel() {

    init {


    }

    val model: Model = Model()

//    private val _model = MutableLiveData(Model())
//
//    val model : LiveData<Model> get() {
//        return _model
//    } 

    private val _modelUpdateDetector = MutableLiveData<Int>(0)

    val modelUpdateDetector : LiveData<Int>
        get() {
            return _modelUpdateDetector
        }// this getter can be removed



    fun addCourse(course: Course) {
        model.addCourse(course)
        _modelUpdateDetector.value = _modelUpdateDetector.value?.plus(1)

    }

    fun deleteCourse(code : String) {
        model.deleteCourse(code)
        _modelUpdateDetector.value = _modelUpdateDetector.value?.minus(1)
    }

    fun editCourse(code : String, name : String, term : String, mark : String) {
        model.editCourse(code, name, term, mark)
        _modelUpdateDetector.value = _modelUpdateDetector.value?.plus(1)
        _modelUpdateDetector.value = _modelUpdateDetector.value?.minus(1)
    }

    fun sortCourse(sortType : String) {
        model.sortCourse(sortType)
        _modelUpdateDetector.value = _modelUpdateDetector.value?.plus(1)
        _modelUpdateDetector.value = _modelUpdateDetector.value?.minus(1)
    }

    // return a filtered course list
    fun filterCourse(filterType : String) : MutableList<Course> {

        return model.filterCourse(filterType)
        // getAllCourses() has no notify() ability
        // so should filterCourse()
        // or it will incur an infinity notify loop
    }

    fun filterNotify() { // does nothing, just act as a notifier for filterCourse()
        _modelUpdateDetector.value = _modelUpdateDetector.value?.plus(1)
        _modelUpdateDetector.value = _modelUpdateDetector.value?.minus(1)
    }

    fun getAllCourses() : MutableList<Course> {
        return model.courses
    }


    // How do we implement this???
    fun getAllDisplayedCourses() : MutableList<Course> {
        return model.courses
    }



    //
    private val _greeting = MutableLiveData("Hello CS349")

    val greeting : LiveData<String>
        get() {
        return _greeting
    }

    fun addExcl() {
        _greeting.value = "${_greeting.value}!"
    }






}
