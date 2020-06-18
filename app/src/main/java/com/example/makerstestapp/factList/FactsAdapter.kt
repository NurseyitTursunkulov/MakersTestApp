package com.example.makerstestapp.factList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.Item
import com.example.makerstestapp.FactsViewModel
import com.example.makerstestapp.databinding.FactItemBinding

class FactsAdapter(
    private val viewModel: FactsViewModel
) : ListAdapter<Item, FactsAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)

//        if (isUserReachedTheBottom(position) && repositoryHasMoreItems()) {
//            viewModel.loadFacts()
//        }
    }

    private fun repositoryHasMoreItems(): Boolean {
        viewModel.totalItemsSize.value?.let { totalItemSize ->
            viewModel.items.value?.size?.let { currentItemSize ->
                return totalItemSize > currentItemSize+1
            }
        }
        return false
    }

    private fun isUserReachedTheBottom(position: Int) : Boolean {
        return position + 1 == viewModel.items.value?.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: FactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: FactsViewModel, newsModel: Item) {

            binding.viewmodel = viewModel
            binding.factItem = newsModel
            Glide.with(binding.root.context).load(newsModel.img).centerCrop().into( binding.imageView);

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FactItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

}
class TaskDiffCallback :  DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean =
        oldItem == newItem
}