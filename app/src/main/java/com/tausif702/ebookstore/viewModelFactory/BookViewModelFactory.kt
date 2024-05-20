package com.tausif702.ebookstore.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tausif702.ebookstore.repository.BookRepo
import com.tausif702.ebookstore.viewModel.BookViewModel

class BookViewModelFactory (private val repo:BookRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookViewModel(repo) as T
    }
}
