package com.tausif702.ebookstore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.tausif702.ebookstore.Adapter.HomeChildAdapter
import com.tausif702.ebookstore.Models.BooksModel
import com.tausif702.ebookstore.Models.HomeModel
import com.tausif702.ebookstore.Utils.loadOnline
import com.tausif702.ebookstore.databinding.ItemBodBinding
import com.tausif702.ebookstore.databinding.ItemHomeBinding


const val LAYOUT_HOME = 0
const val LAYOUT_BOD = 1

class HomeAdapter(private val list: ArrayList<HomeModel>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class HomeItemViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val mViewPool = RecyclerView.RecycledViewPool()
        fun bind(model: HomeModel, context: Context) {
            binding.apply {
                model.apply {
                    mCategoryTitle.text = catTitle

                    seeAllBtn.setOnClickListener {
                        // handle here
                        val intent = Intent(context, CategoryActivity::class.java)
                        intent.putExtra("book_list", booksList)
                        context.startActivity(intent)

                    }

                    if (booksList != null) {
                        mChildRvBook.setupChildRv(
                            booksList.reversed() as ArrayList<BooksModel>,
                            context
                        )
                    }
                }
            }

        }

        private fun RecyclerView.setupChildRv(list: ArrayList<BooksModel>, context: Context) {
            val adapter = HomeChildAdapter(list, context)
            this.adapter = adapter
            setRecycledViewPool(mViewPool)
//            SpringScrollHelper().attachToRecyclerView(this)
        }
    }

    class BODItemViewHolder(private val binding: ItemBodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: HomeModel, context: Context) {

            binding.apply {
                mReadbook.setOnClickListener {
                    model.bod?.apply {
                        binding.imageView.loadOnline(image)
                        Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, DetailsActivity::class.java)
                        intent.putExtra("book_model", model.bod)
                        context.startActivity(intent)
                    }
                }
            }


        }
    }

    override fun getItemViewType(position: Int): Int {
        val model = list[position]
        return when (model.LAYOUT_TYPE) {
            LAYOUT_HOME -> LAYOUT_HOME
            else -> LAYOUT_BOD
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LAYOUT_HOME -> {
                HomeItemViewHolder(
                    ItemHomeBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                BODItemViewHolder(
                    ItemBodBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        when (model.LAYOUT_TYPE) {
            LAYOUT_HOME -> {
                (holder as HomeItemViewHolder).bind(model, context)
            }
            else -> {
                (holder as BODItemViewHolder).bind(model, context)
            }
        }
    }
}