package com.example.newsfeed.presentation.home

import android.util.Log
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult
import com.example.newsfeed.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    operator fun invoke(): Flow<RequestResult<List<NewsUi>>> {
        return repository.getOllNewsList()
          /*  .map { requestResult ->
                Log.i("GetAllNewsUseCase", "$requestResult")
                requestResult.map { news ->
                    news.map { it.toUiNews() }
                }
            }*/
    }
}

private fun NewsUi.toUiNews(): NewsUi {
    return NewsUi(
        id = id,
        image = image,
        title = title,
        publishedAt = publishedAt,
        description = description,
        addedBy = addedBy,
        isBookmarked = isBookmarked,
        source = source
    )
}