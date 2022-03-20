package com.example.noodoe.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noodoe.databinding.ItemFooterBinding
import com.example.noodoe.databinding.ItemHeaderBinding
import com.example.noodoe.databinding.ItemListBinding
import com.example.noodoe.databinding.ItemNoDataBinding
import com.example.noodoe.network.api.list.data.NewsResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallback) {

    enum class ItemType {
        HEADER, ITEM, FOOTER, NO_DATA
    }

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addFooterAndSubmitList(list: List<NewsResult>, isLastPage: Boolean = true) {
        adapterScope.launch {
            val items = listOf(DataItem.Header) + when {
                list.isNullOrEmpty() -> listOf(DataItem.NoData)
                isLastPage -> list.map { DataItem.Item(it) } + listOf(DataItem.Footer)
                else -> list.map { DataItem.Item(it) }
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.HEADER.ordinal -> HeaderViewHolder.from(parent)
            ItemType.ITEM.ordinal -> ItemViewHolder.from(parent)
            ItemType.FOOTER.ordinal -> FooterViewHolder.from(parent)
            ItemType.NO_DATA.ordinal -> NoDataViewHolder.from(parent)
            else -> NoDataViewHolder.from(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ItemType.HEADER.ordinal
            is DataItem.Item -> ItemType.ITEM.ordinal
            is DataItem.Footer -> ItemType.FOOTER.ordinal
            is DataItem.NoData -> ItemType.NO_DATA.ordinal
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                val item = (getItem(position) as DataItem.Item).item
                holder.bind(item)
            }

            is HeaderViewHolder -> {
            }

            is FooterViewHolder -> {
            }

            is NoDataViewHolder -> {
            }
        }
    }

    class ItemViewHolder(private var binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewsResult) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecyclerView.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemListBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }
    }

    class HeaderViewHolder(binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): RecyclerView.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemHeaderBinding.inflate(layoutInflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }

    class FooterViewHolder(binding: ItemFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): RecyclerView.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemFooterBinding.inflate(layoutInflater, parent, false)
                return FooterViewHolder(binding)
            }
        }
    }

    class NoDataViewHolder(binding: ItemNoDataBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): RecyclerView.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemNoDataBinding.inflate(layoutInflater, parent, false)
                return NoDataViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (item: NewsResult?) -> Unit) {
        fun onClick(item: NewsResult?) = clickListener(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, NewResultItem: DataItem): Boolean {
            return oldItem === NewResultItem
        }

        override fun areContentsTheSame(oldItem: DataItem, NewResultItem: DataItem): Boolean {
            return oldItem.chtMessage == NewResultItem.chtMessage
        }
    }

}

sealed class DataItem {

    abstract val chtMessage: String?

    data class Item(val item: NewsResult) : DataItem() {
        override val chtMessage = item.chtMessage
    }

    object Header : DataItem() {
        override val chtMessage: String? = null
    }

    object Footer : DataItem() {
        override val chtMessage: String? = null
    }

    object NoData : DataItem() {
        override val chtMessage: String? = null
    }

}
