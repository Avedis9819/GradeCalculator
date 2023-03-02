package com.example.myapplication

class GradeCalculator {
    private var homeworkGrades: ArrayList<Double> = ArrayList()
    private var participationGrade: Double = 100.0
    private var midterm1Grade: Double = 100.0
    private var midterm2Grade: Double = 100.0
    private var finalProject: Double = 100.0
    private var groupPresentationGrade: Double = 100.0

    fun addHomework(grade: Double) {
        if (homeworkGrades.size < 6)
            homeworkGrades.add(grade)
    }

    fun getHomeworks() {

    }
    fun resetHomework() {
        homeworkGrades = ArrayList()
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
        var homeworkGrade = 0.0
        var i = 0
        while (homeworkGrades.isNotEmpty()) {
            homeworkGrade += homeworkGrades[i++]
        }
        homeworkGrade /= 5
        return homeworkGrade
    }

    fun calculateFinalGrade(): Double {
        return 0.1 * (participationGrade) + 0.2 * (getHomeworkAverage()) + 0.1 * (groupPresentationGrade) + 0.1 * midterm1Grade + 0.2 * midterm2Grade + 0.3 * finalProject
    }
}