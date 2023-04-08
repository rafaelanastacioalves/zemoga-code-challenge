package com.example.rafaelanastacioalves.moby.ui.postlisting


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rafaelanastacioalves.moby.R
import com.example.rafaelanastacioalves.moby.domain.entities.Post
import com.example.rafaelanastacioalves.moby.domain.entities.Resource
import com.example.rafaelanastacioalves.moby.ui.postdetailing.PostDetailingActivity
import com.example.rafaelanastacioalves.moby.ui.postdetailing.EntityDetailsFragment
import com.example.rafaelanastacioalves.moby.listeners.RecyclerViewClickListener

class PostListingActivity : AppCompatActivity(), RecyclerViewClickListener{

    private val mClickListener = this
    private var postListAdapter: PostListAdapter? = null
    private var mRecyclerView: RecyclerView? = null
    private val mPostListingViewModel: PostListingViewModel by lazy {
        ViewModelProvider(this).get(PostListingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setupRecyclerView()
        subscribe()
        loadData()

    }

    private fun loadData() {
        mPostListingViewModel.loadDataIfNecessary()
    }


    private fun subscribe() {
        mPostListingViewModel.finalPostLiveData.observeForever(Observer { mainEntities ->
            populateRecyclerView(mainEntities)
        })
    }

    private fun setupViews() {
        setContentView(R.layout.activity_main)

    }

    private fun setupRecyclerView() {
        mRecyclerView = findViewById<View>(R.id.main_entity_list) as RecyclerView
        val layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView!!.layoutManager = layoutManager
        if (postListAdapter == null) {
            postListAdapter = PostListAdapter(this)
        }
        postListAdapter!!.setRecyclerViewClickListener(mClickListener)
        postListAdapter!!.setViewModel(mPostListingViewModel)
        mRecyclerView!!.adapter = postListAdapter
    }


    private fun populateRecyclerView(list: Resource<List<Post>>?) {
        if (list == null) {
            postListAdapter!!.setItems(null)

        } else if (list.data!=null) {
            postListAdapter!!.setItems(list.data)
        }

    }


    override fun onClick(view: View, position: Int) {
        val post = postListAdapter!!.getItems()!!.get(position)

        val i = Intent(this, PostDetailingActivity::class.java)
        i.putExtra(EntityDetailsFragment.POST_ID, post.id)
        startActivity(i)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                showOnlyFav()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showOnlyFav() {
        mPostListingViewModel.showOnlyFavorites()
    }


}
