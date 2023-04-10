package com.example.rafaelanastacioalves.moby.ui.postlisting;

import androidx.lifecycle.*
import com.example.rafaelanastacioalves.moby.domain.entities.Post
import com.example.rafaelanastacioalves.moby.domain.entities.Resource
import com.example.rafaelanastacioalves.moby.domain.interactors.PostListInteractor
import com.example.rafaelanastacioalves.moby.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PostListingViewModel(
    var postListInteractor: PostListInteractor = PostListInteractor(),
    private val repository: AppRepository = AppRepository
) : ViewModel() {
    val _isOnlyFavorites = MutableLiveData<Boolean>(false)
    val isOnlyFavorites : LiveData<Boolean> = _isOnlyFavorites
    val postListLiveData = MutableLiveData<Resource<List<Post>>>()

    val finalPostLiveData = isOnlyFavorites.switchMap { onlyFav ->
        if (onlyFav && postListLiveData.value?.data.isNullOrEmpty().not()) {
            postListLiveData.map { resource ->
                val list = resource.data.orEmpty()
                resource.data = list.filter { post ->
                    post.favorited == true

                }
                resource
            }
        }else {
            postListLiveData
        }

    }


    fun loadDataIfNecessary() {
        postListInteractor.execute(viewModelScope, null, {
            handle(it)
        })
    }

    private fun handle(it: Resource<List<Post>>) {
        postListLiveData.postValue(it)
    }

    fun onDeleteItemPosition(post: Post) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                repository.savePost(post)
                loadDataIfNecessary()
            }
        }

    }

    fun onFavoriteItemPosition(post: Post) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                repository.savePost(post)
                loadDataIfNecessary()
            }
        }

    }

    fun showOnlyFavorites() {
        _isOnlyFavorites.postValue(_isOnlyFavorites.value!!.not())
        loadDataIfNecessary()
    }

}
