package element.list.gitbrowser.presentation.fragments.owner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import element.list.gitbrowser.model.OwnerDetails
import element.list.gitbrowser.networking.GitHubRepository
import retrofit2.Callback
import retrofit2.Response

class OwnerViewModel(private val gitHubRepository: GitHubRepository) : ViewModel() {
    var ownerDetails: MutableLiveData<OwnerDetails> = MutableLiveData()

    fun getOwnerDetails(searchText: String) {
        gitHubRepository.gitHubApi.searchUser(searchText).enqueue(object : Callback<OwnerDetails> {
            override fun onFailure(call: retrofit2.Call<OwnerDetails>, t: Throwable) {
            }

            override fun onResponse(call: retrofit2.Call<OwnerDetails>, response: Response<OwnerDetails>) {
                ownerDetails.value = response.body()
            }
        })
    }
}