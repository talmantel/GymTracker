package com.it.gymtracker

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.it.gymtracker.models.Exercise
import com.it.gymtracker.models.Workout
import com.it.gymtracker.models.Workoutable
import com.it.gymtracker.models.WorkoutableType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.exercise_picker.*


class MainActivity : AppCompatActivity() {

    private lateinit var workouts: ArrayList<Workout>
    private lateinit var topWorkouts: List<Workout>
    private lateinit var currentWorkout: ArrayList<Workoutable>
    private lateinit var exercises: ArrayList<Exercise>

    private lateinit var viewAdapter: ExercisesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private fun testInit() {

        exercises = ArrayList()
        var counter = 0

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Bench Press",
            sets = "3 X 5"
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Incline Bench Press",
            sets = "3 X 5"
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Squat",
            sets = "3 X 5"
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Reverse Lunge",
            sets = null
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Pull Up",
            sets = "3 X 6-10"
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Chin Up",
            sets = "3 X 6-10"
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Dumbell Pressout",
            sets = null
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Dumbell Urlachers",
            sets = null
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Straight Lateral Raise",
            sets = null
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Down Crunches",
            sets = null
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Face Pull",
            sets = "2 X 12"
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Straight Arm Push Down",
            sets = null
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Deadlift",
            sets = "3 X 5"
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Barbell Rows",
            sets = "3-4 X 10-12"
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Overhead Shoulder Press",
            sets = "3 X 5"
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Barbell Hip Thrust",
            sets = "3-4 X 10-12"
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Dips",
            sets = null
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Leg Raises",
            sets = null
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Bicep Curls",
            sets = null
        ))

        exercises.add(Exercise(id = ++counter, description = null, url = null, workoutsSince = null,
            name = "Lat Pulldown",
            sets = null
        ))

        Repository.getInstance().setExercises(this, exercises)

        workouts = ArrayList()
        counter = 0

        workouts.add(Workout(
            id = ++counter,
            name = "Bench Press",
            maxWorkoutables = 1,
            workoutables = arrayListOf(
                Workoutable(1, WorkoutableType.EXERCISE),
                Workoutable(2, WorkoutableType.EXERCISE)
            ), workoutsSince = null))

        workouts.add(Workout(
            id = ++counter,
            name = "Squat",
            maxWorkoutables = 1,
            workoutables = arrayListOf(
                Workoutable(3, WorkoutableType.EXERCISE),
                Workoutable(4, WorkoutableType.EXERCISE)
            ), workoutsSince = null))

        workouts.add(Workout(
            id = ++counter,
            name = "Pull up",
            maxWorkoutables = 1,
            workoutables = arrayListOf(
                Workoutable(5, WorkoutableType.EXERCISE),
                Workoutable(6, WorkoutableType.EXERCISE)
            ), workoutsSince = null))

        workouts.add(Workout(
            id = ++counter,
            name = "Secondary Shoulder",
            maxWorkoutables = 1,
            workoutables = arrayListOf(
                Workoutable(7, WorkoutableType.EXERCISE),
                Workoutable(8, WorkoutableType.EXERCISE),
                Workoutable(9, WorkoutableType.EXERCISE)
            ), workoutsSince = null))

        workouts.add(Workout(
            id = ++counter,
            name = "Face Pull OR Straight Arm Push Down",
            maxWorkoutables = 1,
            workoutables = arrayListOf(
                Workoutable(11, WorkoutableType.EXERCISE),
                Workoutable(12, WorkoutableType.EXERCISE)
            ), workoutsSince = null))


       workouts.add(Workout(
            id = ++counter,
            name = "A",
            workoutables = arrayListOf(
                Workoutable(1, WorkoutableType.WORKOUT),
                Workoutable(2, WorkoutableType.WORKOUT),
                Workoutable(3, WorkoutableType.WORKOUT),
                Workoutable(4, WorkoutableType.WORKOUT),
                Workoutable(10, WorkoutableType.EXERCISE),
                Workoutable(5, WorkoutableType.WORKOUT)
            ), workoutsSince = null))

       workouts.add(Workout(
            id = ++counter,
            name = "B",
            workoutables = arrayListOf(
                Workoutable(13, WorkoutableType.EXERCISE),
                Workoutable(14, WorkoutableType.EXERCISE),
                Workoutable(15, WorkoutableType.EXERCISE),
                Workoutable(16, WorkoutableType.EXERCISE),
                Workoutable(17, WorkoutableType.EXERCISE),
                Workoutable(18, WorkoutableType.EXERCISE)
            ), workoutsSince = null))

       workouts.add(Workout(
            id = ++counter,
            name = "C",
            topLevel = true,
            workoutables = arrayListOf(
                Workoutable(18, WorkoutableType.EXERCISE),
                Workoutable(5, WorkoutableType.WORKOUT),
                Workoutable(10, WorkoutableType.EXERCISE),
                Workoutable(16, WorkoutableType.EXERCISE),
                Workoutable(19, WorkoutableType.EXERCISE),
                Workoutable(9, WorkoutableType.WORKOUT)
            ), workoutsSince = null))

        workouts.add(Workout(
            id = ++counter,
            name = "C finish",
            maxWorkoutables = 1,
            workoutables = arrayListOf(
                Workoutable(20, WorkoutableType.EXERCISE),
                Workoutable(5, WorkoutableType.EXERCISE),
                Workoutable(4, WorkoutableType.WORKOUT),
                Workoutable(17, WorkoutableType.EXERCISE)
            ), workoutsSince = null))

       workouts.add(Workout(
            id = ++counter,
            name = "A - B",
            topLevel = true,
            maxWorkoutables = 1,
            workoutables = arrayListOf(
                Workoutable(6, WorkoutableType.WORKOUT),
                Workoutable(7, WorkoutableType.WORKOUT)
            ), workoutsSince = null))

        Repository.getInstance().setWorkouts(this, workouts)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testInit()

        initialize()

        val arrayAdapter: ArrayAdapter<Workout> =
            ArrayAdapter(this, R.layout.spinner_selected_item,
                topWorkouts)

        arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
        workoutSetSpinner.adapter = arrayAdapter
        workoutSetSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val workout = parent.getItemAtPosition(position) as Workout
                loadWorkout(workout)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        workoutSetSpinner.setSelection(0)

        button.setOnClickListener {
            val dialogClickListener =
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            workouts.forEach{
                                it.workoutsSince = if(it.workoutsSince == null) null else it.workoutsSince!! + 1
                            }

                            exercises.forEach{
                                it.workoutsSince = if(it.workoutsSince == null) null else it.workoutsSince!! + 1
                            }

                            currentWorkout.forEach{
                                it.workoutsSince = 0
                            }

                            Repository.getInstance().setWorkouts(this@MainActivity, workouts)
                            Repository.getInstance().setExercises(this@MainActivity, exercises)

                            loadWorkout(workoutSetSpinner.selectedItem as Workout)
                            dialog.dismiss()
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                }

            AlertDialog.Builder(this@MainActivity)
                .setTitle("Finish the training?")
                .setMessage("Are you sure you want to finish this training?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()
        }


        addExerciseButton.setOnClickListener {
            val dialog = Dialog(this@MainActivity)
            dialog.setContentView(R.layout.exercise_picker)
            dialog.setTitle("Add Exercise")


            @Suppress("UNCHECKED_CAST")
            val exercisesViewAdapter = ExercisesAdapter(this,
                exercises.clone() as ArrayList<Exercise>,
                selectionMode = true,
                deleteButton = false
            )

            dialog.exercises_recycler_view.apply{
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = exercisesViewAdapter
            }

            val dialogButton = dialog.findViewById(R.id.dialogPickButton) as Button
            dialogButton.setOnClickListener {
                //TODO add the exercises
                dialog.dismiss()
            }

            dialog.show()
        }
    }


    private fun initialize(){
        val repo = Repository.getInstance()
        workouts = repo.getWorkouts(this)
        exercises = repo.getExercises(this)
        topWorkouts = workouts.filter { it.topLevel }
        viewManager = LinearLayoutManager(this)
    }

    private fun loadWorkout(workout: Workout){
        currentWorkout = workout.getBestWorkout()

        viewAdapter = ExercisesAdapter(this,
            currentWorkout.filterIsInstance<Exercise>().toMutableList()
        )

        viewAdapter.exerciseRemovedListener = { exercise ->
            val dialogClickListener =
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            currentWorkout.remove(exercise)
                            dialog.dismiss()
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                }

            AlertDialog.Builder(this@MainActivity)
                .setTitle("Remove exercise?")
                .setMessage("Are you sure you want remove this exercise?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()
        }

        recyclerView.apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun Workout.getBestWorkout(): ArrayList<Workoutable>{
        val bestWorkout = ArrayList<Workoutable>()

        var fullWorkoutables = workoutables.mapNotNull { getWorkoutable(it) }

        maxWorkoutables?.let { max ->
            fullWorkoutables = fullWorkoutables
                .sortedByDescending { it.workoutsSince ?: Int.MAX_VALUE }
                .take(max)
        }

        bestWorkout.addAll(fullWorkoutables)

        fullWorkoutables.filterIsInstance<Workout>().forEach { workoutable ->
            bestWorkout.addAll(workoutable.getBestWorkout())
        }

        return bestWorkout
    }

    private fun getWorkoutable(workoutable: Workoutable): Workoutable?{
        return when (workoutable.type) {
            WorkoutableType.WORKOUT -> workouts.find { it.id == workoutable.id }
            WorkoutableType.EXERCISE -> exercises.find { it.id == workoutable.id }
        }
    }
}
