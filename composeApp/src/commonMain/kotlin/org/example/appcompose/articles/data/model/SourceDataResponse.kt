package org.example.appcompose.articles.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SourceDataResponse(
    @SerialName("status")
    val status: String,
    @SerialName("sources")
    val sources: List<SourceResponse>
)