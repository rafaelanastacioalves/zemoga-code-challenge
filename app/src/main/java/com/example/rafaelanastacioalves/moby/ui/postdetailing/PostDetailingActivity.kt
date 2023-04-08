package com.example.rafaelanastacioalves.moby.ui.postdetailing

import android.os.Bundle

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.example.rafaelanastacioalves.moby.R


class PostDetailingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entity_detail)
        setupActionBar()


        if (savedInstanceState == null) {
            val arguments = Bundle()
            arguments.putString(EntityDetailsFragment.POST_ID,
                    intent.getStringExtra(EntityDetailsFragment.POST_ID))
            val fragment = EntityDetailsFragment()
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .add(R.id.entity_detail_fragment_container, fragment)
                    .commit()


        }
    }

    private fun setupActionBar() {
        val toolbar = findViewById<View>(R.id.detail_toolbar) as Toolbar
        setSupportActionBar(toolbar)

    }

}
