package com.it.gymtracker.models

open class Workoutable(val id: Int, val type: WorkoutableType, var workoutsSince: Int? = null)

enum class WorkoutableType{ WORKOUT, EXERCISE }