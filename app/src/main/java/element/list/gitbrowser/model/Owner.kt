package element.list.gitbrowser.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Owner(
    var login: String?,
    @SerializedName("avatar_url")
    var avatarUrl: String?
) : Serializable