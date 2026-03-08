package com.example.carstockapplication.domain.model

data class Pageable<T>(
    val content: List<T>,
    val metadata: PageMetadata
)
data class PageMetadata(
    val first: Boolean,
    val last: Boolean,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)