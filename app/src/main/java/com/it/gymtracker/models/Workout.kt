package com.it.gymtracker.models

class Workout(
    id: Int,
    val name: String,
    workoutsSince: Int?,
    val workoutables: ArrayList<Workoutable>,
    val topLevel: Boolean = false,
    val maxWorkoutables: Int? = null
): Workoutable(id, WorkoutableType.WORKOUT, workoutsSince){

    override fun toString() = name
}

