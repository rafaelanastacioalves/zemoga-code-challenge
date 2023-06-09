package com.example.rafaelanastacioalves.zemoga.ui.postdetailing


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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rafaelanastacioalves.zemoga.R
import com.example.rafaelanastacioalves.zemoga.domain.entities.Comment
import com.example.rafaelanastacioalves.zemoga.domain.entities.Post
import com.example.rafaelanastacioalves.zemoga.domain.entities.User
import kotlinx.android.synthetic.main.fragment_post_detail.*


/**
 * A simple [Fragment] subclass.
 */
class PostDetailsFragment : Fragment(), View.OnClickListener {
    lateinit var recyclerView: RecyclerView
    lateinit private var mPostDetailViewModel: PostDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.commentsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadData() {
        val postId = arguments!!.getLong(POST_ID)
        mPostDetailViewModel =
            ViewModelProvider.NewInstanceFactory().create(PostDetailViewModel::class.java)
        mPostDetailViewModel.loadData(postId.toString())
            .observe(this, Observer { result -> setViewsWith(result?.data) })
        mPostDetailViewModel.userInfo.observe(this, { userInfo -> setViewsWith(userInfo.data) })
        mPostDetailViewModel.postComments.observe(
            this,
            { comments -> setCommentsWith(comments.data) })
    }

    private fun setCommentsWith(data: List<Comment>?) {
        data?.let { data ->
            val adapter = CommentAdapter(data)
            recyclerView.adapter = adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflateViews(inflater, container)
    }


    private fun inflateViews(inflater: LayoutInflater, container: ViewGroup?): View {
        val rootView =
            inflater.inflate(R.layout.fragment_post_detail, container, false)
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
                getResources().getString(
                    R.string.user_name_text,
                    user?.username ?: getString(R.string.nothing_to_show)
                ),
                HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV
            )
        )
        userEmail.setText(
            HtmlCompat.fromHtml(
                getResources().getString(
                    R.string.user_email_text,
                    user?.email ?: getString(R.string.nothing_to_show)
                ),
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
