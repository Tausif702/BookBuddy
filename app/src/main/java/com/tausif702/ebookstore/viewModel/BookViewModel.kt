package com.tausif702.ebookstore.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tausif702.ebookstore.repository.BookRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(private val repo: BookRepo) : ViewModel() {
    val downloadLiveData get() = repo.downloadLiveData


    @RequiresApi(Build.VERSION_CODES.O)
    fun downloadFile(uri: String, fileName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.downloadPdf(
                uri,
                fileName
            )
        }
    }
}