package com.it.gymtracker.models

open class Workoutable(val id: Int, val name: String?, val type: WorkoutableType, var workoutsSince: Int? = null)
{
    override fun toString() = "$name"
}

enum class WorkoutableType{ WORKOUT, EXERCISE }