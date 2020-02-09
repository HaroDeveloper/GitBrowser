package element.list.gitbrowser.networking

import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit

class ApiService : KoinComponent {
    private var apiClient: GitHubApi
    private val retrofit: Retrofit by inject()

    init {
        apiClient = retrofit.run { this.create(GitHubApi::class.java) }
    }
}