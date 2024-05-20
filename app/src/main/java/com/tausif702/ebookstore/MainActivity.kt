package com.tausif702.ebookstore

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.tausif702.ebookstore.databinding.ActivityMainBinding
import com.tausif702.ebookstore.Models.HomeModel
import com.tausif702.ebookstore.Utils.MyResponses
import com.tausif702.ebookstore.repository.MainRepo
import com.tausif702.ebookstore.viewModel.MainViewModel
import com.tausif702.ebookstore.viewModelFactory.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list: ArrayList<HomeModel> = ArrayList()
    private val adapter = HomeAdapter(list, this@MainActivity)

    private val repo = MainRepo(this)
    private val viewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory(repo))[MainViewModel::class.java]

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.purple_700))


        binding.apply {
            mRvhome.adapter = adapter
            SpringScrollHelper().attachToRecyclerView(mRvhome)

            viewModel.getHomeData()
            handleHomebackend()
            binding.errorBtn.tryAgainBtn.setOnClickListener {
                viewModel.getHomeData()
            }

        }


        binding.fb.setOnClickListener {
            val bottomSheet = BottomFragment()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleHomebackend() {
        viewModel.homeLiveData.observe(this) { it ->
            when (it) {
                is MyResponses.Error -> {
                    Log.d("Tausif", "handleHomebackend:${it.errorMessage} ")

                    binding.apply {
                        loadingHolder.visibility = View.GONE
                        errorHolder.visibility = View.VISIBLE
                    }
                }
                is MyResponses.Loading -> {
                    Log.d("Tausif", "handleHomebackend loading........ ")
                    binding.apply {
                        loadingHolder.visibility = View.VISIBLE
                        errorHolder.visibility = View.GONE
                    }
                }
                is MyResponses.Success -> {

                    Log.d("Tausif", "handleHomebackend Success........")

                    val tempList = (it.data)
                    list.clear()
                    tempList?.forEach {
                        list.add(it)
                    }
                    adapter.notifyDataSetChanged()

                    binding.apply {
                        loadingHolder.visibility = View.GONE
                        errorHolder.visibility = View.GONE
                    }
                }
            }
        }
    }


}