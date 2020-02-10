package element.list.gitbrowser.presentation.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import element.list.gitbrowser.model.GitRepository
import element.list.gitbrowser.model.RepositoryResponse
import element.list.gitbrowser.networking.GitHubRepository
import element.list.gitbrowser.utils.RepoStatus
import org.koin.core.KoinComponent
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val gitHubRepository: GitHubRepository) : KoinComponent, ViewModel() {

    var repositoryListLive: MutableLiveData<MutableList<GitRepository>> = MutableLiveData()
    var repositoryList: MutableList<GitRepository> = mutableListOf()
    var repoStatus: MutableLiveData<RepoStatus> = MutableLiveData()

    fun getRepositories(searchText: String, sort: String = "") {
        repoStatus.postValue(RepoStatus.LOADING)
        gitHubRepository.searchRepositories(searchText, sort).enqueue(object : Callback<RepositoryResponse> {
            override fun onFailure(call: retrofit2.Call<RepositoryResponse>, t: Throwable) {
                repoStatus.postValue(RepoStatus.FAILURE)
            }

            override fun onResponse(call: retrofit2.Call<RepositoryResponse>, response: Response<RepositoryResponse>) {
                repoStatus.postValue(RepoStatus.SUCCESS)
                repositoryList = response.body()?.items!!
                repositoryListLive.value = response.body()?.items
            }
        })
    }
}
