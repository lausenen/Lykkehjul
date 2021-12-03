package com.example.lykkehjul

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class CharAdapter(charArray: CharArray) : RecyclerView.Adapter<CharAdapter.CharViewHolder>() {

    private val charArray = charArray

    //All of the below code is taken from the kotlin words app codelab in class
    /**
     * Provides a reference for the views needed to display items in your list.
     */
    class CharViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.wordText)
    }

    /**
     * Creates new views with R.layout.letter_view as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.letter_view, parent, false)

        return CharViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: CharViewHolder, position: Int) {
        val item = charArray[position]
        holder.textView.text = item.toString()
    }

    override fun getItemCount(): Int {
        return charArray.size
    }
}