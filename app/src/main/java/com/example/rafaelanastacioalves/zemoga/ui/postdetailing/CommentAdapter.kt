package com.example.rafaelanastacioalves.zemoga.ui.postdetailing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rafaelanastacioalves.zemoga.R
import com.example.rafaelanastacioalves.zemoga.domain.entities.Comment

class CommentAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

    override fun getItemCount() = comments.size

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.commentName)
        private val emailTextView: TextView = itemView.findViewById(R.id.commentEmail)
        private val bodyTextView: TextView = itemView.findViewById(R.id.commentBody)

        fun bind(comment: Comment) {
            nameTextView.text = comment.name
            emailTextView.text = comment.email
            bodyTextView.text = comment.body
        }
    }
}
