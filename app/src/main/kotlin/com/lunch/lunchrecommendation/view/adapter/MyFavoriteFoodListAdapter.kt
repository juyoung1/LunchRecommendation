package com.lunch.lunchrecommendation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lunch.lunchrecommendation.data.dao.MenuDao
import com.lunch.lunchrecommendation.databinding.ItemMyLikeFoodListBinding
import com.lunch.lunchrecommendation.extensions.onClick

/**
 * 내 찜 목록 어댑터
 */
class MyFavoriteFoodListAdapter(val context: Context?, private val list: ArrayList<MenuDao>): RecyclerView.Adapter<MyFavoriteFoodListAdapter.CustomViewHolder>() {

    interface SelectItem { fun selectItem(position: Int, type: String) }
    var selectItem: SelectItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val binding = ItemMyLikeFoodListBinding.inflate(LayoutInflater.from(context))
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

    inner class CustomViewHolder(private val binding: ItemMyLikeFoodListBinding): RecyclerView.ViewHolder(binding.root) {

        fun binding(context: Context?, position: Int, dao: MenuDao, selectItem: SelectItem?) {

            with(binding) {

                context?.let { ctx ->

                    // 찜한 메뉴 이미지
                    Glide.with(ctx).load(dao.menuImage).into(ivFood)

                    clItem.tag = position
                    clItem.onClick {

                        val pos = it.tag.toString().toInt()
                        selectItem?.selectItem(pos, "click")
                    }
                }
            }
        }
    }
}