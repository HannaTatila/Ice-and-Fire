package com.example.iceandfire.house.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.iceandfire.R
import com.example.iceandfire.databinding.ItemMainListBinding
import com.example.iceandfire.house.domain.model.House

class HousePagingAdapter(
    private val onHouseItemClickListener: (String) -> Unit
) : PagingDataAdapter<House, HousePagingAdapter.HouseViewHolder>(HousePagingDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val binding = ItemMainListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HouseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        val house = getItem(position)
        house?.let { holder.bind(it, position) }
    }

    inner class HouseViewHolder(private val houseBinding: ItemMainListBinding) :
        RecyclerView.ViewHolder(houseBinding.root) {
        fun bind(house: House, position: Int) = with(houseBinding) {
            textTitleBook.text =
                root.context.getString(R.string.item_title_format, position.inc(), house.name)
            root.setOnClickListener {
                onHouseItemClickListener.invoke(house.url)
            }
        }
    }

    class HousePagingDiffUtil : DiffUtil.ItemCallback<House>() {
        override fun areItemsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem == newItem
        }
    }
}