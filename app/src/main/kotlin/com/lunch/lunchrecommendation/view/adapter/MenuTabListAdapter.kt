package com.lunch.lunchrecommendation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lunch.lunchrecommendation.data.dao.MenuDao
import com.lunch.lunchrecommendation.databinding.ItemTabMenuListBinding

/**
 * 메뉴 - 상단 메뉴 탭 어댑터
 */
class MenuTabListAdapter(val context: Context?, private val list: ArrayList<MenuDao>): RecyclerView.Adapter<MenuTabListAdapter.CustomViewHolder>() {

    interface SelectItem { fun selectItem(position: Int) }
    var selectItem: SelectItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val binding = ItemTabMenuListBinding.inflate(LayoutInflater.from(context))
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        if (list.size > position) {
            holder.binding(context, position, list[position], selectItem)
        }
    }

    inner class CustomViewHolder(private val binding: ItemTabMenuListBinding): RecyclerView.ViewHolder(binding.root) {

        fun binding(context: Context?, position: Int, dao: MenuDao, selectItem: SelectItem?) {

            with(binding) {

                context?.let { ctx ->

                    // 메뉴 카테고리 이름
                    tvMenu.text = dao.menu

                    clItem.isSelected = dao.isSelected
                    clItem.setClickListener(position, selectItem)
                }
            }
        }

        private fun View.setClickListener(position: Int, selectItem: SelectItem?) {

            tag = position
            setOnClickListener {

                val pos = it.tag.toString().toInt()
                selectItem?.selectItem(pos)
            }
        }
    }
}