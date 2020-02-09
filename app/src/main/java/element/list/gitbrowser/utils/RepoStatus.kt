package element.list.gitbrowser.utils

sealed class RepoStatus {
    object LOADING : RepoStatus()
    object SUCCES : RepoStatus()
    object FAILURE : RepoStatus()

}