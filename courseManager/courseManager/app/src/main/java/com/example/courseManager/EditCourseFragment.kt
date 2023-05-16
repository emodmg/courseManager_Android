package com.example.courseManager

import android.os.Bundle
import android.text.InputType
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.courseManager.myViewModel.MyViewModel

class EditCourseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // helper functions
        fun gradeValid(mark: Int): Boolean {
            return (mark >= 0) && (mark <= 100)
        }


        val view = inflater.inflate(R.layout.edit_course, container, false)

        val myVM = ViewModelProvider(requireActivity())[MyViewModel::class.java]


        // define wd_switch on edit page behavior
        view.findViewById<Switch>(R.id.editfrag_wd_switch).setOnCheckedChangeListener { compoundButton, b_isChecked ->
            view.findViewById<EditText>(R.id.editfrag_edit_mark).isEnabled = !b_isChecked
        }


        // edit course page Term Spinner Init
        val termSpinner = view.findViewById<Spinner>(R.id.editfrag_term_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(requireActivity(), R.array.termSpinnerArray, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            termSpinner.adapter = adapter
        }



        // Init the edit_frag view when editing an (existing) course
        view.findViewById<TextView>(R.id.editfrag_coursecode_textview).text = requireArguments().getString("code")
        view.findViewById<EditText>(R.id.editfrag_edit_description).setText(requireArguments().getString("name"))

        val tempMark = requireArguments().getString("mark")

        if (tempMark == "WD") {
            view.findViewById<Switch>(R.id.editfrag_wd_switch).isChecked = true
        } else {
            view.findViewById<EditText>(R.id.editfrag_edit_mark).setText(tempMark)
        }

        val termList = listOf<String>("F20", "W21", "S21", "F21", "W22", "S22", "F22", "W23")
        val termNum = termList.indexOf(requireArguments().getString("term"))
        view.findViewById<Spinner>(R.id.editfrag_term_spinner).setSelection(termNum)





        //  press Cancel button to navigate back to first fragment page
        view.findViewById<Button>(R.id.editfrag_cancel).setOnClickListener {
            findNavController().navigate(R.id.action_editCourseFragment_to_FirstFragment)
        }


        // press Submit button to submit the edit course require and navigate back to first fragment if successful, otherwise stay on this frag
        view.findViewById<Button>(R.id.editfrag_submit).setOnClickListener {
            // check if fields are valid to edit the course
            val code = requireArguments().getString("code")
            val description = view.findViewById<EditText>(R.id.editfrag_edit_description).text.toString()
            val term = view.findViewById<Spinner>(R.id.editfrag_term_spinner).selectedItem.toString()
            val wdSwitch = view.findViewById<Switch>(R.id.editfrag_wd_switch).isChecked




            if (wdSwitch.not()) {
                if (view.findViewById<EditText>(R.id.editfrag_edit_mark).text.filter { !it.isWhitespace() }.isNotEmpty()) {
                    val mark = view.findViewById<EditText>(R.id.editfrag_edit_mark).text.toString().toInt()
                    if (gradeValid(mark)) {
                        myVM.editCourse(code.toString(), description, term, mark.toString())
                        findNavController().navigate(R.id.action_editCourseFragment_to_FirstFragment)
                    }
                }
            } else {
                myVM.editCourse(code.toString(), description, term, "WD")
                findNavController().navigate(R.id.action_editCourseFragment_to_FirstFragment)
            }

        }




        return view

    }
}