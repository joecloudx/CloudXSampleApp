package com.example.cloudxsampleapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val eventName: TextView = itemView.findViewById(R.id.event_name)
    private val eventStatus: TextView = itemView.findViewById(R.id.event_status)

    fun bind(event: Event) {
        eventName.text = event.name
        eventStatus.text = event.status
    }
}