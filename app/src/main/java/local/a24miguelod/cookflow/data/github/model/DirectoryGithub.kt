package local.a24miguelod.cookflow.data.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectoryGithub(
    val name: String?,
    @SerialName("download_url")
    val downloadUrl: String?
)