package com.it.gymtracker.models

class Exercise(id: Int,
               val name: String,
               val description: String?,
               val url: String?,
               val sets: String?,
               workoutsSince: Int?): Workoutable(id, WorkoutableType.EXERCISE, workoutsSince){

    override fun toString() = name
}
