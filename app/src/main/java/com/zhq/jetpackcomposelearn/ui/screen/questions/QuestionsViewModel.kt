package com.zhq.jetpackcomposelearn.ui.screen.questions

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.repo.QuestionsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/7 17:48
 * Description
 */
private const val TAG = "QuestionsViewModel"

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repo: QuestionsRepositoryImpl) :
    BaseViewModel<Unit>() {
    init {
        Log.d(TAG, "===QuestionsViewModel init: ")
    }

    fun getQuestionsList(): Flow<PagingData<ArticleDTO>> {
        return repo.getQuestionsList().cachedIn(viewModelScope)
    }
}