package com.tausif702.ebookstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.tausif702.ebookstore.Adapter.CategoryAdapter
import com.tausif702.ebookstore.Models.BooksModel
import com.tausif702.ebookstore.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCategoryBinding.inflate(layoutInflater)
    }
    private val list = ArrayList<BooksModel>()
    private val adapter = CategoryAdapter(list, this@CategoryActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Category Book"

        binding.apply {
            mRvCategory.adapter = adapter
            SpringScrollHelper().attachToRecyclerView(mRvCategory)
            val bookList = intent.getSerializableExtra("book_list") as ArrayList<BooksModel>
            val temList = bookList.reversed()
            temList.forEach {
                list.add(it)
            }
        }
    }
}