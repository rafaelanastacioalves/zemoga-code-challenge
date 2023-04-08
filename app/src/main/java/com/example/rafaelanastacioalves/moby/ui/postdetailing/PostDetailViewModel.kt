package com.example.rafaelanastacioalves.moby.ui.postdetailing


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rafaelanastacioalves.moby.domain.entities.Post
import com.example.rafaelanastacioalves.moby.domain.entities.Resource
import com.example.rafaelanastacioalves.moby.domain.entities.User
import com.example.rafaelanastacioalves.moby.domain.interactors.PostDetailsInteractor
import com.example.rafaelanastacioalves.moby.domain.interactors.UserDetailInteractor


internal class PostDetailViewModel : ViewModel() {

    private val userDetailsInteractor: UserDetailInteractor = UserDetailInteractor()
    val postDetails = MutableLiveData<Resource<Post>>()

    val userInfo = MutableLiveData<Resource<User>>()

    val postDetailsInteractor: PostDetailsInteractor = PostDetailsInteractor()

    fun loadData(postId: String?) : MutableLiveData<Resource<Post>> {

        postDetails.postValue(Resource.loading())
        postDetailsInteractor.execute(viewModelScope,
                postId?.let{PostDetailsInteractor.RequestValues(it)},{ it -> handlePost(it)})
        return postDetails
    }

    private fun handlePost(postResource: Resource<Post>?) {
        postResource?.data?.let { post ->
            userDetailsInteractor.execute(viewModelScope,
                post.id.let{UserDetailInteractor.RequestValues(it.toString())}, { it -> handleUser(it)}) }
        postDetails.postValue(postResource)
    }

    private fun handleUser(it: Resource<User>?) {
        it?.let {
            userInfo.postValue(it)
        }
    }


}

