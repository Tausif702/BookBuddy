package com.tausif702.ebookstore.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tausif702.ebookstore.repository.BookRepo
import com.tausif702.ebookstore.repository.MainRepo
import com.tausif702.ebookstore.viewModel.BookViewModel
import com.tausif702.ebookstore.viewModel.MainViewModel

class MainViewModelFactory (private val repo:MainRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}
