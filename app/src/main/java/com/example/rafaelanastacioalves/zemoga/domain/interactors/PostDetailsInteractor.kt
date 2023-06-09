package com.example.rafaelanastacioalves.zemoga.domain.interactors


import com.example.rafaelanastacioalves.zemoga.domain.entities.Post
import com.example.rafaelanastacioalves.zemoga.domain.entities.Resource
import com.example.rafaelanastacioalves.zemoga.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostDetailsInteractor :
        Interactor<Resource<Post>?, PostDetailsInteractor.RequestValues>() {
    val appRepository: AppRepository

    init {
        appRepository = AppRepository
    }

    override suspend fun run(requestValue: PostDetailsInteractor.RequestValues?): Resource<Post>? {
        var result : Resource<Post>? = null
        withContext(Dispatchers.IO) {
            result = requestValue?.requestId?.let { appRepository.getPostById(it) }
        }
        return result

    }

    class RequestValues(val requestId: String) : Interactor.RequestValues

}
