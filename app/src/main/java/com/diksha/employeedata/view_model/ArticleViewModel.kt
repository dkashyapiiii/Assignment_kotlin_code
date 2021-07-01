package com.diksha.employeedata.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.diksha.employeedata.ModelClass.EmployeeModel
import com.diksha.employeedata.repository.ArticleRepository

class ArticleViewModel(application: Application) : AndroidViewModel(application) {
    private val articleRepository: ArticleRepository
    val articleResponseLiveData: LiveData<EmployeeModel?>

    init {
        articleRepository = ArticleRepository()
        articleResponseLiveData = articleRepository.employeeModel
    }
}