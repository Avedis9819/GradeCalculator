package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.myapplication.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar
import java.util.InputMismatchException

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Grade Calculator"
        _binding = FragmentFirstBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runCalculation()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val gradeCalculator = GradeCalculator()
    private val homeworkGradeList = gradeCalculator.getHomeworkList()

    private fun runCalculation() {
        val homeworkButton: Button = requireView().findViewById(R.id.homeworkAddButton)
        val homeworkTextField: TextView? = view?.findViewById(R.id.homeworkGradesTextView)
        val homeworkSize: Int = gradeCalculator.getHomeworkSize()

        homeworkButton.setOnClickListener {view ->
            try {
                val currentHomeworkGrade: EditText = requireView().findViewById<EditText?>(R.id.homeworkInputField)
                val homeworkGrade: Double = currentHomeworkGrade.text.toString().toDouble()
                try {
                    println(homeworkSize)
                    if(homeworkGrade in 0.0..100.0 && homeworkSize < 5) {
                        gradeCalculator.addHomework(homeworkGrade)
                        val output = StringBuilder()

                        for ((key, value) in homeworkGradeList) {
                            output.append("Homework $key: $value\n")
                        }
                        if (homeworkTextField != null) {
                            homeworkTextField.setText(output.toString())
                        }
                        Snackbar.make(view, "Homework Added", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                    } else if(homeworkGrade > 100.0 || homeworkGrade < 0.0){
                        Snackbar.make(view, "Please insert a valid homework grade", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                    } else {
                        Snackbar.make(view, "Homework Capacity reached", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                        for(homework in homeworkGradeList)
                            println(homework)
                    }
                } catch (e: InputMismatchException) {
                    println(e.message)
                }

            } catch (e: Exception) {
                println("Error while adding the homeworks!")
                println(e.message)
            }
        }

        val resetHomeworkButton: Button = requireView().findViewById(R.id.resetHomeworks)
        resetHomeworkButton.setOnClickListener {view -> Snackbar.make(view, "Homeworks reset!", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            gradeCalculator.resetHomework()
            homeworkTextField?.setText("")
        }

        val calculateButton: Button = requireView().findViewById(R.id.calculateButton)
        calculateButton.setOnClickListener {
            try {
                var participationGrade: EditText = requireView().findViewById<EditText?>(R.id.participationGradeInput)
                var midterm1Grade: EditText = requireView().findViewById<EditText?>(R.id.midterm1GradeInput)
                var midterm2Grade: EditText = requireView().findViewById<EditText?>(R.id.midterm2GradeInput)
                var groupPresentationGrade: EditText = requireView().findViewById<EditText?>(R.id.groupPresentationGradeInput)
                var finalProjectGrade: EditText = requireView().findViewById<EditText?>(R.id.finalProjectGradeInput)
                val finalNumericalField: TextView? = view?.findViewById(R.id.finalNumericalGrade)


                gradeCalculator.setMidterm1(midterm1Grade.text.toString().toDouble())
                gradeCalculator.setParticipationGrade(participationGrade.text.toString().toDouble())
                gradeCalculator.setMidterm2(midterm2Grade.text.toString().toDouble())
                gradeCalculator.setGroupPresGrade(groupPresentationGrade.text.toString().toDouble())
                gradeCalculator.setFinalProject(finalProjectGrade.text.toString().toDouble())

                val finalGrade: Double = gradeCalculator.calculateFinalGrade()
                println(finalGrade)

                val finalGradeString: String = finalGrade.toString()
                finalNumericalField?.setText(finalGradeString)

                println(participationGrade.toString())
                println(midterm1Grade.toString())
                println(midterm2Grade.toString())
                println(groupPresentationGrade.toString())
                println(finalProjectGrade.toString())

            } catch (e: Exception) {
                println("Error converting the number")
                println(e.message)
            }
        }
    }

    inner class GradeCalculator {
        private var homeworkGradesList: HashMap<Int, Double> = HashMap()
        private var participationGrade: Double = 100.0
        private var midterm1Grade: Double = 100.0
        private var midterm2Grade: Double = 100.0
        private var finalProject: Double = 100.0
        private var groupPresentationGrade: Double = 100.0
        private var addedHomeworkNumber: Int = 0

        init {
            homeworkGradesList[1] = 100.0
            homeworkGradesList[2] = 100.0
            homeworkGradesList[3] = 100.0
            homeworkGradesList[4] = 100.0
            homeworkGradesList[5] = 100.0
        }

        fun addHomework(grade: Double) {
            if (grade in 0.0..100.0 && addedHomeworkNumber < 5) {
                homeworkGradesList[addedHomeworkNumber + 1] = grade
                addedHomeworkNumber++
            }
        }

        fun getHomeworkList(): HashMap<Int, Double> {
            return homeworkGradesList
        }

        fun getHomeworkSize(): Int {
            return addedHomeworkNumber + 1
        }

        fun getHomework(i: Int): Double? {
            return homeworkGradesList.get(i)
        }

        fun resetHomework() {
            homeworkGradesList.clear()
            addedHomeworkNumber = 0
        }

        fun setParticipationGrade(grade: Double) {
            if (grade in 0.0..100.0)
                participationGrade = grade
            else
                println("input error Participation grade")
        }

        fun setMidterm1(grade: Double) {
            if (grade in 0.0..100.0)
                midterm1Grade = grade
            else
                println("Midterm 1 grade is not acceptable")
        }

        fun setMidterm2(grade: Double) {
            if (grade in 0.0..100.0) {
                midterm2Grade = grade
            } else
                println("Midterm 2 grade is not acceptable")
        }

        fun setGroupPresGrade(grade: Double) {
            if (grade in 0.0..100.0)
                groupPresentationGrade = grade
            else
                println("group presentation input error")
        }

        fun setFinalProject(grade: Double) {
            if (grade in 0.0..100.0)
                finalProject = grade
            else
                println("Error setting the final project grade")
        }

        private fun getHomeworkAverage(): Double {
            var homeworkGradeSum: Double = 0.0
            var i: Int = 0
            while(i <= 5) {
                var currentHomeworkGrade = homeworkGradesList[i]
                if (currentHomeworkGrade != null) {
                    homeworkGradeSum += currentHomeworkGrade
                }
                i++;
            }
            homeworkGradeSum /= 5
            return homeworkGradeSum
        }

        fun calculateFinalGrade(): Double {
            println("Participation Grade: $participationGrade")
            println("Homework Average: " + getHomeworkAverage())
            println("Group Presentation: $groupPresentationGrade")
            println("Midterm 1 grade: $midterm1Grade")
            println("Midterm 2 grade: $midterm2Grade")
            println("Final project grade: $finalProject")
            return 0.1 * (participationGrade) + 0.2 * (getHomeworkAverage()) + 0.1 * (groupPresentationGrade) + (0.1 * midterm1Grade) + (0.2 * midterm2Grade) + (0.3 * finalProject)
        }
    }
}