package com.example.rafaelanastacioalves.zemoga.ui.postlisting;

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rafaelanastacioalves.zemoga.R
import com.example.rafaelanastacioalves.zemoga.domain.entities.Post
import com.example.rafaelanastacioalves.zemoga.listeners.RecyclerViewClickListener

class PostListAdapter(context: Context) : RecyclerView.Adapter<PostItemViewHolder>() {
    private lateinit var viewModel: PostListingViewModel
    lateinit private var recyclerViewClickListener: RecyclerViewClickListener
    private var items: List<Post>? = null

    private val mContext: Context = context

    fun setRecyclerViewClickListener(aRVC: RecyclerViewClickListener ) {
        this.recyclerViewClickListener = aRVC;
    }

    fun setViewModel(viewModel : PostListingViewModel){
        this.viewModel = viewModel
    }

    fun getItems(): List<Post>? {
        return this.items;
    }

    fun setItems(items: List<Post>?) {
        this.items = items
        updateList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder  {
        return PostItemViewHolder(
            LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_post_detail, parent, false),
            recyclerViewClickListener,
            viewModel
        );
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int ) {
        val aRepoW = getItems()?.get(position) as Post;
        holder.bind(aRepoW, mContext);
    }

    override fun getItemCount(): Int {
        if (getItems() != null){
            return getItems()!!.size;
        }else{
            return 0;
        }
    }

    fun updateList() {
        notifyDataSetChanged()
    }
}

