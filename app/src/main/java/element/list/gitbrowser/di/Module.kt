package element.list.gitbrowser.di

import element.list.gitbrowser.constants.Constants
import element.list.gitbrowser.networking.GitHubApi
import element.list.gitbrowser.networking.GitHubRepository
import element.list.gitbrowser.presentation.fragments.home.HomeViewModel
import element.list.gitbrowser.presentation.fragments.owner.OwnerViewModel
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {

    fun provideOkHttpClient(): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
        httpBuilder.connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .cookieJar(CookieJar.NO_COOKIES)
        return httpBuilder.build()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .build()
    }

    fun provideGitHubApi(retrofit: Retrofit): GitHubApi {
        return retrofit.create(GitHubApi::class.java)
    }

    fun provideGitHubRepository(gitHubApi: GitHubApi): GitHubRepository {
        return GitHubRepository(gitHubApi)
    }

    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideGitHubApi(get()) }
    single { provideGitHubRepository(get()) }
}

val viewModelModule = module {

    fun provideHomeViewModel(gitHubRepository: GitHubRepository): HomeViewModel {
        return HomeViewModel(gitHubRepository)
    }

    fun provideOwnerViewModel(gitHubRepository: GitHubRepository): OwnerViewModel {
        return OwnerViewModel(gitHubRepository)
    }

    viewModel { provideHomeViewModel(get()) }
    viewModel { provideOwnerViewModel(get()) }
}