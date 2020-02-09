package element.list.gitbrowser.utils

sealed class RepoStatus {
    object LOADING : RepoStatus()
    object SUCCESS : RepoStatus()
    object FAILURE : RepoStatus()
}