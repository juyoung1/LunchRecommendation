package com.example.lunchrecommendation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lunchrecommendation.R
import com.example.lunchrecommendation.data.dao.MenuDao
import com.example.lunchrecommendation.databinding.ItemMenuListBinding
import com.example.lunchrecommendation.databinding.ItemTabMenuListBinding

/**
 * 메뉴 - 메뉴 리스트 어댑터
 */
class MenuListAdapter(val context: Context?, private val list: ArrayList<MenuDao>): RecyclerView.Adapter<MenuListAdapter.CustomViewHolder>() {

    interface SelectItem { fun selectItem(position: Int) }
    var selectItem: SelectItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val binding = ItemMenuListBinding.inflate(LayoutInflater.from(context))
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

    inner class CustomViewHolder(private val binding: ItemMenuListBinding): RecyclerView.ViewHolder(binding.root) {

        fun binding(context: Context?, position: Int, dao: MenuDao, selectItem: SelectItem?) {

            with(binding) {

                context?.let { ctx ->

                    // 메뉴 이미지
                    Glide.with(context).load(dao.menuImage).into(ivProfile)

                    // 메뉴 이름
                    tvMenu.text = dao.menu

                    // 찜
                    ivHeart.isSelected = dao.isSelected
                    ivHeart.setClickListener(position, selectItem)
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