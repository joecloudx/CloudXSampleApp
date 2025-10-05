package com.example.cloudxsampleapp

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val eventName: TextView = itemView.findViewById(R.id.event_name)
    private val eventStatus: TextView = itemView.findViewById(R.id.event_status)

    fun bind(event: Event) {
        eventName.text = event.name
        val context = itemView.context
        when (event.status) {
            "Pending" -> {
                eventStatus.text = context.getString(R.string.status_pending)
                eventStatus.setTextColor(ContextCompat.getColor(context, R.color.status_pending))
            }
            "Success" -> {
                eventStatus.text = context.getString(R.string.status_success)
                eventStatus.setTextColor(ContextCompat.getColor(context, R.color.status_success))
            }
            "Failure" -> {
                eventStatus.text = context.getString(R.string.status_failure)
                eventStatus.setTextColor(ContextCompat.getColor(context, R.color.status_failure))
            }
            else -> {
                eventStatus.text = event.status
                eventStatus.setTextColor(ContextCompat.getColor(context, R.color.black))
            }
        }
    }
}