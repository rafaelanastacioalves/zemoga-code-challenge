package com.example.rafaelanastacioalves.moby.repository

import com.example.rafaelanastacioalves.moby.domain.entities.Post
import com.example.rafaelanastacioalves.moby.domain.entities.Resource
import com.example.rafaelanastacioalves.moby.repository.database.AppDataBase
import com.example.rafaelanastacioalves.moby.repository.database.DAO
import com.example.rafaelanastacioalves.moby.repository.http.APIClient
import com.example.rafaelanastacioalves.moby.repository.http.ServiceGenerator

object AppRepository {
    private val appDao: DAO = AppDataBase.getInstance().appDAO()
    var apiClient: APIClient = ServiceGenerator.createService(APIClient::class.java);

    suspend fun getPosts(): Resource<List<Post>> {
        return object : NetworkBoundResource<List<Post>, List<Post>>() {
            override suspend fun fecthFromHttp(): List<Post>? {

                return apiClient.getMainEntityList()
            }

            override suspend fun getFromDB(): List<Post>? {
                val mainEntityList = appDao.getPostList()
                return if(mainEntityList.isNotEmpty()){
                    mainEntityList
                }else{
                    null
                }
            }

            override fun saveIntoDB(resultData: List<Post>?) {
                appDao.savePostList(resultData)
            }

        }.fromHttpAndDB()
    }

}