package com.tausif702.ebookstore.viewModel

import androidx.lifecycle.ViewModel
import com.tausif702.ebookstore.repository.MainRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepo) : ViewModel() {
    val homeLiveData get() = repo.homeLiveData

    fun getHomeData(){
        CoroutineScope(Dispatchers.IO).launch {
            repo.getHomeData()
        }
    }

}