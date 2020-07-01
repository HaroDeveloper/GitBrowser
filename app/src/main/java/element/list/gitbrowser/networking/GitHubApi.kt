package element.list.gitbrowser.networking

import element.list.gitbrowser.model.OwnerDetails
import element.list.gitbrowser.model.RepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("users/{user}")
    suspend fun searchUser(@Path("user") name: String): OwnerDetails

    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") name: String, @Query("sort") sort: String?): RepositoryResponse
}