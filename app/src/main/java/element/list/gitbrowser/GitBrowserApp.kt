package element.list.gitbrowser

import android.app.Application
import element.list.gitbrowser.di.apiModule
import element.list.gitbrowser.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GitBrowserApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GitBrowserApp)
            modules(
                listOf(
                    apiModule,
                    viewModelModule
                )
            )
        }
    }

}