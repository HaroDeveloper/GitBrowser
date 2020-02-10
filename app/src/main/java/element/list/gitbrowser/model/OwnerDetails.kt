package element.list.gitbrowser.model

import com.google.gson.annotations.SerializedName

data class OwnerDetails(
        var login: String?,
        @SerializedName("avatar_url")
        var avatarUrl: String?,
        var name: String?,
        var company: String?,
        var followers: String?,
        var blog: String?,
        var url: String?,
        @SerializedName("html_url")
        var htmlUrl: String?,
        var location: String?
)