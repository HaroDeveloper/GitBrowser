package element.list.gitbrowser.networking

import element.list.gitbrowser.model.OwnerDetails
import element.list.gitbrowser.model.RepositoryResponse
import retrofit2.Call

class GitHubRepository(var gitHubApi: GitHubApi) {
    fun searchRepositories(searchText: String): Call<RepositoryResponse> {
        return gitHubApi.searchRepositories(searchText)
    }

    fun searchUser(searchText: String): Call<OwnerDetails> {
        return gitHubApi.searchUser(searchText)
    }
}