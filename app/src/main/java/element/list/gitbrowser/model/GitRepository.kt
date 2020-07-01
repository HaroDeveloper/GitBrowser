package element.list.gitbrowser.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class GitRepository(
        var name: String?,
        var owner: Owner,
        @SerializedName("watchers_count")
        var watchersCount: Int?,
        @SerializedName("forks_count")
        var forksCount: Int?,
        @SerializedName("open_issues_count")
        var issuesCount: Int?,
        var language: String?,
        @SerializedName("created_at")
        var creationDate: Date?,
        @SerializedName("updated_at")
        var updateDate: Date?,
        var score: Double?,
        var url: String?,
        @SerializedName("stargazers_count")
        var stars: Int?,
        @SerializedName("html_url")
        var htmlUrl: String?
) : Serializable