package com.example.courseManager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.courseManager.model.Course
import com.example.courseManager.myViewModel.MyViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val view = inflater.inflate(R.layout.fragment_first, container, false)

        // retrieve the viewModel for first fragment
        val myVM = ViewModelProvider(requireActivity())[MyViewModel::class.java]


        // Filter Spinner Init
        val filterSpinner = view.findViewById<Spinner>(R.id.filterSpinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(requireContext(), R.array.filterSpinnerStringArray, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            filterSpinner.adapter = adapter
        }

        // define the Filter Spinner behavior
        filterSpinner.apply {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    // does nothing, just a fake model change, so it will invoke the course list to draw the courses again
                    myVM.filterNotify()

                    // filter the VM course list when item selected
//                    val filterType : String = parent?.getItemAtPosition(p2).toString()

//                    Log.i( "REPORT MSG", "MY SORTING SPINNER OPTION: is :  ${parent?.getItemAtPosition(p2).toString()} !!! ")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    println("Nothing is selected for sorting!!!")
                }
            }
        }


        // helper fun
        // Create a single course bar View and add it to the root ViewGroup
        fun addOneCourseView(course: Course, root : ViewGroup) {

            // create a new bar view for a single course
            val resultBar = View.inflate(requireActivity(), R.layout.single_course_bar, null)

            // set the color according to mark
            if (course.courseGradeStr == "WD") {
                resultBar.findViewById<LinearLayout>(R.id.bar_background).setBackgroundResource(R.color.wd)
            } else if (course.courseGradeStr.toInt() < 50) {
                resultBar.findViewById<LinearLayout>(R.id.bar_background).setBackgroundResource(R.color.fail)
            } else if (course.courseGradeStr.toInt() >= 96) {
                resultBar.findViewById<LinearLayout>(R.id.bar_background).setBackgroundResource(R.color.excellent)
            } else if (course.courseGradeStr.toInt() >= 91) {
                resultBar.findViewById<LinearLayout>(R.id.bar_background).setBackgroundResource(R.color.great)
            } else if (course.courseGradeStr.toInt() >= 60) {
                resultBar.findViewById<LinearLayout>(R.id.bar_background).setBackgroundResource(R.color.good)
            } else {
                resultBar.findViewById<LinearLayout>(R.id.bar_background).setBackgroundResource(R.color.low)
            }

            // set course code
            resultBar.findViewById<TextView>(R.id.courseBar_courseCode).text = course.courseCode
            // set course mark
            resultBar.findViewById<TextView>(R.id.courseBar_mark).text = course.courseGradeStr
            // set course term
            resultBar.findViewById<TextView>(R.id.courseBar_term).text = course.courseTerm
            // set course description
            resultBar.findViewById<TextView>(R.id.courseBar_description).text = course.courseName


            // set the functionality of edit button, edit the course using VM
            resultBar.findViewById<ImageButton>(R.id.courseBar_edit).setOnClickListener {
//                myVM.editCourse(course.courseCode, )
                findNavController().navigate(R.id.action_FirstFragment_to_editCourseFragment,
                    Bundle().apply {
                        putString("code", course.courseCode)
                        putString("name", course.courseName)
                        putString("mark", course.courseGradeStr)
                        putString("term", course.courseTerm)
                    })
            }

            // set the functionality of delete button, delete the course using VM
            resultBar.findViewById<ImageButton>(R.id.courseBar_delete).setOnClickListener {
                myVM.deleteCourse(course.courseCode)
            }



            // add the single course bar View to the root ViewGroup
            root.addView(resultBar)
        }

        // add courses from model course list to the scrollView course_list
        fun addCourseList(root: ViewGroup) {

            val filterType : String = view.findViewById<Spinner>(R.id.filterSpinner).selectedItem.toString()
            val courseSourceList : MutableList<Course> = myVM.filterCourse(filterType)
            courseSourceList.forEach {
                addOneCourseView(it, root)
            }

        }


        // fragment 1 observes the VM's changes and update instantly
        // data is safely stored in VM
        myVM.modelUpdateDetector.observe(requireActivity()) {
            view.findViewById<LinearLayout>(R.id.course_list).removeAllViews()
            addCourseList(view.findViewById<LinearLayout>(R.id.course_list))

        }



//         press Add button to navigate to Add Course page
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_addCourseFragment)
        }





        // Sorting Spinner Init
        val sortingSpinner = view.findViewById<Spinner>(R.id.sortingSpinner)
        ArrayAdapter.createFromResource(requireContext(), R.array.sortingSpinnerStringArray, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sortingSpinner.adapter = adapter
        }

        // define the Sorting Spinner behavior
        sortingSpinner.apply {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    // sort the VM course list when item selected
                    val sortType : String = parent?.getItemAtPosition(p2).toString()
                    myVM.sortCourse(sortType)

//                    Log.i( "REPORT MSG", "MY SORTING SPINNER OPTION: is :  ${parent?.getItemAtPosition(p2).toString()} !!! ")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    println("Nothing is selected for sorting!!!")
                }
            }
        }

        return view
    }


}