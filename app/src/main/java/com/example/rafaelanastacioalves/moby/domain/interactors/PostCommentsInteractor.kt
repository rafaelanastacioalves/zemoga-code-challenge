package com.example.rafaelanastacioalves.moby.domain.interactors

import com.example.rafaelanastacioalves.moby.domain.entities.Comment



import com.example.rafaelanastacioalves.moby.domain.entities.Resource
import com.example.rafaelanastacioalves.moby.domain.entities.User
import com.example.rafaelanastacioalves.moby.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostCommentsInteractor :
    Interactor<Resource<List<Comment>>?, PostCommentsInteractor.RequestValues>() {
    val appRepository: AppRepository

    init {
        appRepository = AppRepository
    }

    override suspend fun run(requestValue: PostCommentsInteractor.RequestValues?): Resource<List<Comment>>? {
        var result: Resource<List<Comment>>? = null
        withContext(Dispatchers.IO) {
            result = requestValue?.requestId?.let { appRepository.getCommentsForPostId(it) }
        }
        return result
    }

    class RequestValues(val requestId: String) : Interactor.RequestValues

}
