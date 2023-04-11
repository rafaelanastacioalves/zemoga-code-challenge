package com.example.rafaelanastacioalves.moby.domain.interactors

import com.example.rafaelanastacioalves.moby.domain.entities.Post
import com.example.rafaelanastacioalves.moby.domain.entities.Resource
import com.example.rafaelanastacioalves.moby.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PostListInteractor(val  appRepository: AppRepository = AppRepository) : Interactor<Resource<List<Post>>, PostListInteractor.RequestValues>() {


    override suspend fun run(requestValue: RequestValues?): Resource<List<Post>> {
        lateinit var resultOne: List<Post>
        withContext(Dispatchers.IO) {

            val deferredOne = async { appRepository.getPosts() }

            resultOne =
                (deferredOne.await().data.orEmpty().filter { it.deleted == false }.sortedWith(
                    compareBy({ it.favorited.not() }, { it.id.toInt() })
                )
                        )


        }

        val result = Resource.success(resultOne.toList())

        return result


    }

    class RequestValues(nothing: Nothing?) :
        Interactor.RequestValues// in this case we don't need nothing for this use case
}
