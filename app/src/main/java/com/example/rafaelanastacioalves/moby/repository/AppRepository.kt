package com.example.rafaelanastacioalves.moby.repository

import com.example.rafaelanastacioalves.moby.domain.entities.Post
import com.example.rafaelanastacioalves.moby.domain.entities.Resource
import com.example.rafaelanastacioalves.moby.domain.entities.User
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

    suspend fun getPostById(id : String) : Resource<Post> {
        return object : NetworkBoundResource<Post, Post>(){
            override suspend fun fecthFromHttp(): Post? {
                return apiClient.getPostById(id)
            }

            override suspend fun getFromDB(): Post? {
                return appDao.getPostById(id)
            }

            override fun saveIntoDB(resultData: Post?) {
                resultData?.let{ appDao.savePost(it) }
            }

        }.fromHttpAndDB()
    }

    suspend fun getUserById(id : String) : Resource<User> {
        return object : NetworkBoundResource<User, User>(){
            override suspend fun fecthFromHttp(): User? {
                return apiClient.getUserById(id)
            }

            override suspend fun getFromDB(): User? {
                return appDao.getUserById(id)
            }

            override fun saveIntoDB(resultData: User?) {
                resultData?.let{ appDao.saveUser(it) }
            }

        }.fromHttpAndDB()
    }

}