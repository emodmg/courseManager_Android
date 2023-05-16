package com.example.courseManager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.courseManager.model.Course
import com.example.courseManager.myViewModel.MyViewModel

class AddCourseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // helper functions
        fun gradeValid(mark: Int): Boolean {
            return (mark >= 0) && (mark <= 100)
        }


        val view = inflater.inflate(R.layout.add_course, container, false)

        val myVM = ViewModelProvider(requireActivity())[MyViewModel::class.java]


        // define wd_switch on create page behavior
        view.findViewById<Switch>(R.id.wd_switch).setOnCheckedChangeListener { compoundButton, b_isChecked ->
            view.findViewById<EditText>(R.id.addCourse_edit_mark).isEnabled = !b_isChecked
        }


        // add course page Term Spinner Init
        val termSpinner = view.findViewById<Spinner>(R.id.addCourse_term_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(requireActivity(), R.array.termSpinnerArray, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            termSpinner.adapter = adapter
        }


        //  press Cancel button to navigate back to first fragment page
        view.findViewById<Button>(R.id.addCourse_cancel).setOnClickListener {
            findNavController().navigate(R.id.action_addCourseFragment_to_FirstFragment)
        }


//          press Create button to create a course navigate back to first fragment page
        view.findViewById<Button>(R.id.addCourse_create).setOnClickListener {
            // Create a course and save it to VM and Model ???

            // check if mandatory fields are valid to add a new course
            val code = view.findViewById<EditText>(R.id.addCourse_edit_coursecode).text.toString()
            val description = view.findViewById<EditText>(R.id.addCourse_edit_description).text.toString()
            val term = view.findViewById<Spinner>(R.id.addCourse_term_spinner).selectedItem.toString()
            val wdSwitch = view.findViewById<Switch>(R.id.wd_switch).isChecked

            // add course to VM and navigate back to fragment_first if input valid, otherwise NOT added and NOT navigated
            if (code.filter { !it.isWhitespace() } .isNotEmpty()) {
                if (wdSwitch.not()) {
                    if (view.findViewById<EditText>(R.id.addCourse_edit_mark).text.filter { !it.isWhitespace() }.isNotEmpty()) {
                        val mark = view.findViewById<EditText>(R.id.addCourse_edit_mark).text.toString().toInt()
                        if (gradeValid(mark)) {
                            val courseToAdd = Course(code, description, term, mark.toString())
                            myVM.addCourse(courseToAdd)
                            findNavController().navigate(R.id.action_addCourseFragment_to_FirstFragment)
                        }
                    }
                } else {
                    val courseToAdd = Course(code, description, term, "WD")
                    myVM.addCourse(courseToAdd)
                    findNavController().navigate(R.id.action_addCourseFragment_to_FirstFragment)
                }
            }

        }


        return view
    }
}