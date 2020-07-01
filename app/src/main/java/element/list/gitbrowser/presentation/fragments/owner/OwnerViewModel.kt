package element.list.gitbrowser.presentation.fragments.owner

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import element.list.gitbrowser.ResultWrapper
import element.list.gitbrowser.model.OwnerDetails
import element.list.gitbrowser.networking.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OwnerViewModel(private val gitHubRepository: GitHubRepository) : ViewModel() {
    var ownerDetails: MutableLiveData<OwnerDetails> = MutableLiveData()

    fun getOwnerDetails(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val details = gitHubRepository.searchUser(searchText)) {
                is ResultWrapper.Success -> {
                    this@OwnerViewModel.ownerDetails.postValue(details.value)
                }
                is ResultWrapper.Error -> {
                    Log.d("search user api", "failure")
                }
            }
        }
    }
}