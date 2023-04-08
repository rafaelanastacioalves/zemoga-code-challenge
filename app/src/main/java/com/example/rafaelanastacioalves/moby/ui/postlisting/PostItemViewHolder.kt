package com.example.rafaelanastacioalves.moby.ui.postlisting;

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rafaelanastacioalves.moby.R

import com.example.rafaelanastacioalves.moby.domain.entities.Post
import com.example.rafaelanastacioalves.moby.listeners.RecyclerViewClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_post_detail.view.*

class PostItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), View.OnClickListener, LayoutContainer{

    private lateinit var viewModel: PostListingViewModel
    lateinit private var aRecyclerViewListener: RecyclerViewClickListener


    constructor(
        itemView: View,
        clickListener: RecyclerViewClickListener,
        viewModel: PostListingViewModel
    ) : this(itemView) {
        this.aRecyclerViewListener = clickListener
        this.viewModel = viewModel

    }
    init {
        itemView.detail_container.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        aRecyclerViewListener.onClick(v, getAdapterPosition());
    }

    fun bind(aPost: Post, context: Context) {

        itemView.postTitle.setText(aPost.title)
        itemView.postIdText.setText(context.getString(R.string.post_id_text, aPost.id.toString()))
        itemView.deletedText.setText(context.getString(R.string.deleted_value, aPost.deleted))
        itemView.favoritedText.setText(context.getString(R.string.favorited_value, aPost.favorited))
        itemView.favoriteButton.isSelected = aPost.favorited

        itemView.deleteButton.setOnClickListener {
            aPost.deleted = aPost.deleted.not()
            viewModel.onDeleteItemPosition(aPost)
        }
        itemView.favoriteButton.setOnClickListener {
            aPost.favorited = aPost.favorited.not()
            viewModel.onFavoriteItemPosition(aPost)
        }



    }
}
