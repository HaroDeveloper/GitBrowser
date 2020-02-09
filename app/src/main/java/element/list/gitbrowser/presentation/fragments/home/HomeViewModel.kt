package element.list.gitbrowser.presentation.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import element.list.gitbrowser.model.GitRepository
import element.list.gitbrowser.model.RepositoryResponse
import element.list.gitbrowser.networking.GitHubRepository
import element.list.gitbrowser.utils.FilterStatus
import element.list.gitbrowser.utils.RepoStatus
import org.koin.core.KoinComponent
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val gitHubRepository: GitHubRepository) : KoinComponent, ViewModel() {

    var repositoryListLive: MutableLiveData<MutableList<GitRepository>> = MutableLiveData()
    var repositoryList: MutableList<GitRepository> = mutableListOf()
    var repoStatus: MutableLiveData<RepoStatus> = MutableLiveData()
    var filterStatus: MutableLiveData<FilterStatus> = MutableLiveData()

    fun getRepositories(searchText: String) {
        repoStatus.postValue(RepoStatus.LOADING)
        gitHubRepository.searchRepositories(searchText).enqueue(object : Callback<RepositoryResponse> {
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

    fun sortList(selectedFilter: FilterStatus) {
        if (repositoryList.size != 0) {
            when (selectedFilter) {
                FilterStatus.STARS -> {
                    repositoryList =
                            repositoryList.sortedByDescending { it.stars } as MutableList<GitRepository>
                    filterStatus.postValue(selectedFilter)
                }

                FilterStatus.FORKS -> {
                    repositoryList =
                            repositoryList.sortedByDescending { it.forksCount } as MutableList<GitRepository>
                    filterStatus.postValue(selectedFilter)
                }

                FilterStatus.UPDATED -> {
                    repositoryList =
                            repositoryList.sortedByDescending { it.updateDate } as MutableList<GitRepository>
                    filterStatus.postValue(selectedFilter)
                }
            }
        }
    }
}
