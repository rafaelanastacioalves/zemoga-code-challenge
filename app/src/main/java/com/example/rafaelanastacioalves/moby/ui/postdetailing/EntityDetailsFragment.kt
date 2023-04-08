package com.example.rafaelanastacioalves.moby.ui.postdetailing


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rafaelanastacioalves.moby.R
import com.example.rafaelanastacioalves.moby.domain.entities.Post
import com.example.rafaelanastacioalves.moby.domain.entities.Resource
import com.example.rafaelanastacioalves.moby.domain.entities.User
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_entity_detail_view.*


/**
 * A simple [Fragment] subclass.
 */
class EntityDetailsFragment : Fragment(), View.OnClickListener {
    lateinit private var mPostDetailViewModel: PostDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
    }

    private fun loadData() {
        val postId = arguments!!.getString(POST_ID)
        mPostDetailViewModel =
            ViewModelProvider.NewInstanceFactory().create(PostDetailViewModel::class.java)
        mPostDetailViewModel.loadData(postId)
            .observe(this, Observer { entityDetails -> setViewsWith(entityDetails?.data) })
        mPostDetailViewModel.userInfo.observe(this, { userInfo -> setViewsWith(userInfo.data) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflateViews(inflater, container)
    }


    private fun inflateViews(inflater: LayoutInflater, container: ViewGroup?): View {
        val rootView =
            inflater.inflate(R.layout.fragment_detail_entity_detail_view, container, false)
        return rootView
    }


    private fun setupActionBarWithTitle(title: String) {
        val mActivity = activity as AppCompatActivity?
        val actionBar = mActivity!!.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = title


        }
    }

    private fun setViewsWith(postDetail: Post?) {
        titleText.setText(postDetail?.title ?: getString(R.string.nothing_to_show))
        descriptionText.setText(postDetail?.body ?: getString(R.string.nothing_to_show))
    }


    private fun setViewsWith(user: User?) {
        userName.setText(
            HtmlCompat.fromHtml(
                getResources().getString(R.string.user_name_text, user?.username ?: getString(R.string.user_name_text)),
                HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV
            )
        )
        userEmail.setText(
            HtmlCompat.fromHtml(
                getResources().getString(R.string.user_email_text, user?.email ?: getString(R.string.nothing_to_show)),
                HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV
            )
        )
    }


    override fun onClick(v: View) {
        Toast.makeText(activity, "Comprado!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        var POST_ID: String? = null
    }


}
