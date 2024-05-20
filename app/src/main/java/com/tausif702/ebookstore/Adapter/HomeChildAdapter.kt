package com.tausif702.ebookstore.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tausif702.ebookstore.DetailsActivity
import com.tausif702.ebookstore.Models.BooksModel
import com.tausif702.ebookstore.Utils.loadOnline
import com.tausif702.ebookstore.databinding.ItemBookBinding
import java.util.ArrayList
import kotlin.collections.ArrayList as ArrayList1

class HomeChildAdapter(private val list: ArrayList1<BooksModel>, val context: Context) :
    RecyclerView.Adapter<HomeChildAdapter.ChildViewHolder>() {

    class ChildViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: BooksModel, context: Context) {
            binding.apply {
                model.apply {
                    imageView.loadOnline(image)
                    cardView.setOnClickListener {
                        Intent().apply {
                            putExtra("book_model", model)
                            setClass(context, DetailsActivity::class.java)
                            
                            context.startActivity(this@apply)
                        }
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        return ChildViewHolder(ItemBookBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val model = list[position]
        holder.bind(model, context)

    }
}