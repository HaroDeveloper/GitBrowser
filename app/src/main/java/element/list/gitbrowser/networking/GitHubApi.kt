package element.list.gitbrowser.networking

import element.list.gitbrowser.model.RepositoryResponse
import element.list.gitbrowser.model.OwnerDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
//    @GET("search/repositories")
//    fun searchRepositories(@Query("q") name: String): Call<RepositoryResponse>

    @GET("search/repositories")
    fun searchRepositories(@Query("q") name: String, @Query("sort") sort: String?): Call<RepositoryResponse>

    @GET("users/{user}")
    fun searchUser(@Path("user") name: String): Call<OwnerDetails>
}