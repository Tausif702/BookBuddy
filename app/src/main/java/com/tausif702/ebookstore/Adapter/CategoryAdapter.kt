package com.tausif702.ebookstore.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tausif702.ebookstore.DetailsActivity
import com.tausif702.ebookstore.Models.BooksModel
import com.tausif702.ebookstore.Utils.loadOnline
import com.tausif702.ebookstore.databinding.ItemCategoryBinding

class CategoryAdapter(private val list: ArrayList<BooksModel>, private val context: Context) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: BooksModel, context: Context) {
            binding.apply {
                mBookImage.loadOnline(model.image)
                mAuthorName.text = model.author
                mBookDesc.text = model.description
                mBookTitle.text=model.title

                binding.root.setOnClickListener{
                    Intent().apply {
                        putExtra("book_model",model)
                        setClass(context, DetailsActivity::class.java)
                        context.startActivity(this)
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(
            model = list[position], context = context
        )
    }

}