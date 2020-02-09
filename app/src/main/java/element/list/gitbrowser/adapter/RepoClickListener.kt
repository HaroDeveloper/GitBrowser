package element.list.gitbrowser.adapter

import element.list.gitbrowser.model.GitRepository

interface RepoClickListener {
    fun ownerImageClicked(ownerName: String)
    fun repoClicked(gitRepository: GitRepository)
}