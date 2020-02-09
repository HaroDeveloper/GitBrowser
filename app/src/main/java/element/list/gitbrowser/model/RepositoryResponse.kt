package element.list.gitbrowser.model

import com.google.gson.annotations.SerializedName

data class RepositoryResponse (
    @SerializedName("total_count")
    var totalCount: Int,
    var items: MutableList<GitRepository>
)