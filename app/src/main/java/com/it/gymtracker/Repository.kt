package com.it.gymtracker

import android.content.Context
import android.util.Log
import com.it.gymtracker.models.*
import org.json.JSONArray
import org.json.JSONObject

class Repository {

    companion object{
        private const val SHARED_PREFS = "gym_prefs"
        private const val SHARED_PREFS_WORKOUTS = "workouts"
        private const val SHARED_PREFS_EXERCISES = "exercises"
        private const val SHARED_PREFS_SELECTED_WORKOUT = "selected_workout"
        private const val SHARED_PREFS_CURRENT_WORKOUT = "current_workout"

        private const val WORKOUT_ID = "id"
        private const val WORKOUT_WORKOUTABLES = "workoutables"
        private const val WORKOUT_NAME = "name"
        private const val WORKOUT_IS_TOP_LEVEL = "topLevel"
        private const val WORKOUT_MAX_WORKOUTABLES = "maxWorkoutables"
        private const val WORKOUT_WORKOUTS_SINCE = "workoutsSince"

        private const val WORKOUTABLE_TYPE = "type"
        private const val WORKOUTABLE_TYPE_EXERCISE = "exercise"
        private const val WORKOUTABLE_TYPE_WORKOUT = "workout"
        private const val WORKOUTABLE_ID = "id"

        private const val EXERCISE_ID = "id"
        private const val EXERCISE_NAME = "name"
        private const val EXERCISE_DESCRIPTION = "description"
        private const val EXERCISE_URL = "url"
        private const val EXERCISE_SETS = "sets"
        private const val EXERCISE_WORKOUTS_SINCE = "workoutsSince"

        private var instance: Repository? = null

        fun getInstance(): Repository{
            return instance ?: Repository().also { instance = it }
        }
    }

    fun setWorkouts(context: Context, workouts: List<Workout>){
        val workoutsArray = JSONArray()
        for(workout in workouts){
            JSONObject().apply {
                put(WORKOUT_ID, workout.id)
                put(WORKOUT_NAME, workout.name)
                if(workout.topLevel) put(WORKOUT_IS_TOP_LEVEL, true)
                workout.maxWorkoutables?.let { put(WORKOUT_MAX_WORKOUTABLES, it)}
                workout.workoutsSince?.let { put(WORKOUT_WORKOUTS_SINCE, it)}

                val workoutables = JSONArray()
                for (workoutable in workout.workoutables){
                    JSONObject().apply {
                        put(WORKOUTABLE_ID, workoutable.id)
                        put(WORKOUTABLE_TYPE, when(workoutable.type){
                            WorkoutableType.WORKOUT -> WORKOUTABLE_TYPE_WORKOUT
                            WorkoutableType.EXERCISE -> WORKOUTABLE_TYPE_EXERCISE
                        })
                    }.also { workoutables.put(it) }
                }
                put(WORKOUT_WORKOUTABLES, workoutables)
            }.also { workoutsArray.put(it) }
        }

        Log.d("GymTracker", workoutsArray.toString())

        context.getSharedPreferences(SHARED_PREFS, 0).edit().apply{
            putString(SHARED_PREFS_WORKOUTS, workoutsArray.toString())
            commit()
        }
    }

    fun setExercises(context: Context, exercises: List<Exercise>){
        val exercisesArray = JSONArray()
        for(exercise in exercises){
            JSONObject().apply {
                put(EXERCISE_ID, exercise.id)
                put(EXERCISE_NAME, exercise.name)
                exercise.description?.let { put(EXERCISE_DESCRIPTION, it)}
                exercise.url?.let { put(EXERCISE_URL, it)}
                exercise.sets?.let { put(EXERCISE_SETS, it)}
                exercise.workoutsSince?.let { put(EXERCISE_WORKOUTS_SINCE, it)}
            }.also { exercisesArray.put(it) }
        }

        Log.d("GymTracker", exercisesArray.toString())

        context.getSharedPreferences(SHARED_PREFS, 0).edit().apply{
            putString(SHARED_PREFS_EXERCISES, exercisesArray.toString())
            commit()
        }
    }

    fun setWorkoutsJSON(context: Context, workouts: JSONArray){
        context.getSharedPreferences(SHARED_PREFS, 0).edit().apply{
            putString(SHARED_PREFS_WORKOUTS, workouts.toString())
            commit()
        }
    }

    fun setExercisesJSON(context: Context, exercises: JSONArray){
        context.getSharedPreferences(SHARED_PREFS, 0).edit().apply{
            putString(SHARED_PREFS_EXERCISES, exercises.toString())
            commit()
        }
    }

    fun clearMemory(context: Context){
        context.getSharedPreferences(SHARED_PREFS, 0).edit().apply{
            clear()
            commit()
        }
    }


    fun getWorkoutsJSON(context: Context): JSONArray{
        val sp = context.getSharedPreferences(SHARED_PREFS, 0)
        return JSONArray(sp.getString(SHARED_PREFS_WORKOUTS, "[]"))
    }

    fun getWorkouts(context: Context): ArrayList<Workout> {
        val sp = context.getSharedPreferences(SHARED_PREFS, 0)
        val workoutsJSON = JSONArray(sp.getString(SHARED_PREFS_WORKOUTS, "[]"))

        val workouts = ArrayList<Workout>()
        for (i in 0 until workoutsJSON.length()) {
            workouts.add(getWorkoutFromJSON(workoutsJSON.getJSONObject(i)))
        }
        return workouts
    }

    private fun getWorkoutFromJSON(workoutJSON: JSONObject): Workout{
        val workoutables = ArrayList<Workoutable>()
        val workoutablesJSON = JSONArray(workoutJSON.getString(WORKOUT_WORKOUTABLES))
        for (i in 0 until workoutablesJSON.length()) {
            getWorkoutableFromJSON(workoutablesJSON.getJSONObject(i))
                .let{workoutables.add(it)}

        }

        return Workout(workoutJSON.getInt(WORKOUT_ID),
            workoutJSON.getString(WORKOUT_NAME),

            if(workoutJSON.has(WORKOUT_WORKOUTS_SINCE))
                workoutJSON.getInt(WORKOUT_WORKOUTS_SINCE)
            else null,

            workoutables,

            workoutJSON.has(WORKOUT_IS_TOP_LEVEL),

            if(workoutJSON.has(WORKOUT_MAX_WORKOUTABLES))
                workoutJSON.getInt(WORKOUT_MAX_WORKOUTABLES)
            else null

        )
    }

    private fun getWorkoutableFromJSON(workoutableJSON: JSONObject): Workoutable{
        return Workoutable(workoutableJSON.getInt(WORKOUTABLE_ID), null,
            when (workoutableJSON.getString(WORKOUTABLE_TYPE)){
              WORKOUTABLE_TYPE_EXERCISE -> WorkoutableType.EXERCISE
              WORKOUTABLE_TYPE_WORKOUT -> WorkoutableType.WORKOUT
                else -> throw IllegalArgumentException(
                    "Workoutable type has to be either '$WORKOUTABLE_TYPE_EXERCISE' or '$WORKOUTABLE_TYPE_WORKOUT'")
            }
        )
    }

    fun getSelectedWorkout(context: Context): Int?{
        val sp = context.getSharedPreferences(SHARED_PREFS, 0)
        val selectedWorkout = sp.getInt(SHARED_PREFS_SELECTED_WORKOUT, -1)

        if(selectedWorkout == -1)
            return null
        return selectedWorkout
    }

    fun setSelectedWorkout(context: Context, workoutId: Int){
        context.getSharedPreferences(SHARED_PREFS, 0).edit().apply{
            putInt(SHARED_PREFS_SELECTED_WORKOUT, workoutId)
            commit()
        }
    }

    fun getCurrentWorkout(context: Context): List<Workoutable>?{
        val sp = context.getSharedPreferences(SHARED_PREFS, 0)

        if(!sp.contains(SHARED_PREFS_CURRENT_WORKOUT))
            return null

        val workoutables = ArrayList<Workoutable>()
        val workoutablesJSON = JSONArray(sp.getString(SHARED_PREFS_CURRENT_WORKOUT, "[]"))
        Log.d("GymTracker", "saved workoutablesJSON $workoutablesJSON")
        for (i in 0 until workoutablesJSON.length()) {
            getWorkoutableFromJSON(workoutablesJSON.getJSONObject(i))
                .let{workoutables.add(it)}

        }

        return workoutables
    }

    fun setCurrentWorkout(context: Context, currentWorkout: List<Workoutable>){
        val workoutables = JSONArray()
        for (workoutable in currentWorkout){
            JSONObject().apply {
                put(WORKOUTABLE_ID, workoutable.id)
                put(WORKOUTABLE_TYPE, when(workoutable.type){
                    WorkoutableType.WORKOUT -> WORKOUTABLE_TYPE_WORKOUT
                    WorkoutableType.EXERCISE -> WORKOUTABLE_TYPE_EXERCISE
                })
            }.also { workoutables.put(it) }
        }

        context.getSharedPreferences(SHARED_PREFS, 0).edit().apply{
            putString(SHARED_PREFS_CURRENT_WORKOUT, workoutables.toString())
            commit()
        }
    }

    fun getExercisesJSON(context: Context): JSONArray{
        val sp = context.getSharedPreferences(SHARED_PREFS, 0)
        return JSONArray(sp.getString(SHARED_PREFS_EXERCISES, "[]"))
    }

    fun getExercises(context: Context): ArrayList<Exercise>{
        val sp = context.getSharedPreferences(SHARED_PREFS, 0)
        val exercisesJSON = JSONArray(sp.getString(SHARED_PREFS_EXERCISES, "[]"))

        val exercises = ArrayList<Exercise>()
        for (i in 0 until exercisesJSON.length()) {
            exercises.add(getExerciseFromJSON(exercisesJSON.getJSONObject(i)))
        }
        return exercises
    }

    private fun getExerciseFromJSON(exerciseJSON: JSONObject): Exercise{
        return Exercise(exerciseJSON.getInt(EXERCISE_ID),
                exerciseJSON.getString(EXERCISE_NAME),

                if(exerciseJSON.has(EXERCISE_DESCRIPTION))
                    exerciseJSON.getString(EXERCISE_DESCRIPTION)
                else null,

                if(exerciseJSON.has(EXERCISE_URL))
                    exerciseJSON.getString(EXERCISE_URL)
                else null,

                if(exerciseJSON.has(EXERCISE_SETS))
                    exerciseJSON.getString(EXERCISE_SETS)
                else null,

                if(exerciseJSON.has(EXERCISE_WORKOUTS_SINCE))
                    exerciseJSON.getInt(EXERCISE_WORKOUTS_SINCE)
                else null
        )
    }

}