package element.list.gitbrowser.presentation.fragments.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import element.list.gitbrowser.ResultWrapper
import element.list.gitbrowser.model.GitRepository
import element.list.gitbrowser.networking.GitHubRepository
import element.list.gitbrowser.utils.RepoStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class HomeViewModel(private val gitHubRepository: GitHubRepository) : KoinComponent, ViewModel() {
    var repositoryListLive: MutableLiveData<MutableList<GitRepository>> = MutableLiveData()
    var repositoryList: MutableList<GitRepository> = mutableListOf()
    var repoStatus: MutableLiveData<RepoStatus> = MutableLiveData()

    fun getRepositories(searchText: String, sort: String = "") {
        repoStatus.postValue(RepoStatus.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            when (val repositories =
                gitHubRepository.searchRepositories(searchText, sort)) {
                is ResultWrapper.Success -> {
                    repoStatus.postValue(RepoStatus.SUCCESS)
                    repositoryList = repositories.value.items
                    repositoryListLive.postValue(repositories.value.items)
                }
                is ResultWrapper.Error -> {
                    Log.d("search user api", "failure")
                    repoStatus.postValue(RepoStatus.FAILURE)
                }
            }
        }
    }
}
