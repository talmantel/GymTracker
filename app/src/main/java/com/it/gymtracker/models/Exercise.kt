package com.it.gymtracker.models

class Exercise(id: Int,
               name: String,
               val description: String?,
               val url: String?,
               val sets: String?,
               workoutsSince: Int?): Workoutable(id, name, WorkoutableType.EXERCISE, workoutsSince){
}
