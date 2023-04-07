package com.example.rafaelanastacioalves.moby.domain.interactors


import com.example.rafaelanastacioalves.moby.domain.entities.Resource
import com.example.rafaelanastacioalves.moby.domain.entities.User
import com.example.rafaelanastacioalves.moby.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserDetailInteractor :
        Interactor<Resource<User>?, UserDetailInteractor.RequestValues>() {
    val appRepository: AppRepository

    init {
        appRepository = AppRepository
    }

    override suspend fun run(requestValue: UserDetailInteractor.RequestValues?): Resource<User>? {
        var result: Resource<User>? = null
        withContext(Dispatchers.IO) {
            result = requestValue?.requestId?.let { appRepository.getUserById(it) }
        }
        return result
    }

    class RequestValues(val requestId: String) : Interactor.RequestValues

}
