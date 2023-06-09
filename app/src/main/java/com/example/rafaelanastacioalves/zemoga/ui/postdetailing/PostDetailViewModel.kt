package com.example.rafaelanastacioalves.zemoga.ui.postdetailing


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rafaelanastacioalves.zemoga.domain.entities.Comment
import com.example.rafaelanastacioalves.zemoga.domain.entities.Post
import com.example.rafaelanastacioalves.zemoga.domain.entities.Resource
import com.example.rafaelanastacioalves.zemoga.domain.entities.User
import com.example.rafaelanastacioalves.zemoga.domain.interactors.PostCommentsInteractor
import com.example.rafaelanastacioalves.zemoga.domain.interactors.PostDetailsInteractor
import com.example.rafaelanastacioalves.zemoga.domain.interactors.UserDetailInteractor


internal class PostDetailViewModel(
    var userDetailsInteractor: UserDetailInteractor = UserDetailInteractor(),
    var postDetailsInteractor: PostDetailsInteractor = PostDetailsInteractor(),
    var postCommentsInteractor: PostCommentsInteractor = PostCommentsInteractor()

) : ViewModel() {

    val postDetails = MutableLiveData<Resource<Post>>()
    val postComments = MutableLiveData<Resource<List<Comment>>>()
    val userInfo = MutableLiveData<Resource<User>>()

    fun loadData(postId: String?): MutableLiveData<Resource<Post>> {

        postDetails.postValue(Resource.loading())
        postDetailsInteractor.execute(viewModelScope,
            postId?.let { PostDetailsInteractor.RequestValues(it) }, { it -> handlePost(it) })
        return postDetails
    }

    private fun handlePost(postResource: Resource<Post>?) {
        postResource?.data?.let { post ->
            userDetailsInteractor.execute(viewModelScope,
                post.userId.let { UserDetailInteractor.RequestValues(it.toString()) }
            ) { it -> handleUser(it) }
        }

        postResource?.data?.let { post ->
            postCommentsInteractor.execute(viewModelScope,
                post.id.let { PostCommentsInteractor.RequestValues(it.toString()) }
            ) { it -> handleComments(it) }
        }
        postDetails.postValue(postResource)
    }

    private fun handleComments(commentsResource: Resource<List<Comment>>?) {
        commentsResource?.let {
            postComments.postValue(it)
        }
    }

    private fun handleUser(it: Resource<User>?) {
        it?.let {
            userInfo.postValue(it)
        }
    }


}

