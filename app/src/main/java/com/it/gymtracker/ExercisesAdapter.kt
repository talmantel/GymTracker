package com.it.gymtracker
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.it.gymtracker.models.Exercise
import kotlinx.android.synthetic.main.exercise_item.view.*


class ExercisesAdapter(context: Context, private val exercises: MutableList<Exercise>, val selectionMode: Boolean = false, val deleteButton: Boolean = true) : RecyclerView.Adapter<ExercisesAdapter.MyViewHolder>() {

    var exerciseRemovedListener: ((exercise: Exercise) -> Unit)? = null

    lateinit var selectedExercises: MutableList<Exercise>

    private val nonSelectedColor: Int
    private val selectedColor: Int

    init {
        if(selectionMode)
            selectedExercises = arrayListOf()

        nonSelectedColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        selectedColor = ContextCompat.getColor(context, R.color.colorPrimary)
    }

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    class MyViewHolder(val parentView: View,
                       val nameTextView: TextView,
                       val setsTextView: TextView,
                       val removeButton: ImageButton) : RecyclerView.ViewHolder(parentView)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {

        val view: View = mInflater.inflate(R.layout.exercise_item, parent, false)
        if(deleteButton)
            view.removeExercise.visibility = View.VISIBLE
        else
            view.removeExercise.visibility = View.GONE
        return MyViewHolder(view, view.name_text_view, view.sets_text_view, view.removeExercise)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameTextView.text = exercises[position].name


        if(selectionMode) {
            if (selectedExercises.contains(exercises[position]))
                holder.nameTextView.setTextColor(selectedColor)
            else
                holder.nameTextView.setTextColor(nonSelectedColor)
        }

        holder.setsTextView.text = exercises[position].sets ?: "No Sets"
        if(deleteButton) {
            holder.removeButton.setOnClickListener {
                val exercise = exercises[position]
                exerciseRemovedListener?.invoke(exercise)
            }
        }

        if(selectionMode) {
            holder.parentView.setOnClickListener {
                if (!selectedExercises.contains(exercises[position])) {
                    selectedExercises.add(exercises[position])
                    holder.nameTextView.setTextColor(selectedColor)
                } else {
                    holder.nameTextView.setTextColor(nonSelectedColor)
                    selectedExercises.remove(exercises[position])
                }

            }
        }
    }

    fun removeExercise(exercise: Exercise){
        exercises.remove(exercise)
        notifyDataSetChanged()
    }

    fun setExercises(newExercises: List<Exercise>){
        exercises.clear()
        exercises.addAll(newExercises)
        notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = exercises.size
}