package com.it.gymtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_preferences.*
import org.json.JSONArray

class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        exercisesEditText.setText(Repository.getInstance().getExercisesJSON(this).toString())
        workoutsEditText.setText(Repository.getInstance().getWorkoutsJSON(this).toString())

        save.setOnClickListener {
            Repository.getInstance().clearMemory(this)
            try {
                Repository.getInstance().setExercisesJSON(this, JSONArray(exercisesEditText.text.toString()))
                Repository.getInstance().setWorkoutsJSON(this, JSONArray(workoutsEditText.text.toString()))
            }
            catch (e: Exception){
                e.printStackTrace()
            }
            finish()
        }
    }

}
