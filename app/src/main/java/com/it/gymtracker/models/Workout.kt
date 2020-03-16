package com.it.gymtracker.models

class Workout(
    id: Int,
    name: String,
    workoutsSince: Int?,
    val workoutables: ArrayList<Workoutable>,
    val topLevel: Boolean = false,
    val maxWorkoutables: Int? = null
): Workoutable(id, name, WorkoutableType.WORKOUT, workoutsSince)

