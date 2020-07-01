package element.list.gitbrowser.networking

import element.list.gitbrowser.utils.safeApiCall
import kotlinx.coroutines.Dispatchers

class GitHubRepository(private var gitHubApi: GitHubApi) {
    suspend fun searchRepositories(searchText: String, sort: String) =
        safeApiCall(Dispatchers.IO) {
            gitHubApi.searchRepositories(searchText, sort)
        }

    suspend fun searchUser(searchText: String) =
        safeApiCall(Dispatchers.IO) { gitHubApi.searchUser(searchText) }
}