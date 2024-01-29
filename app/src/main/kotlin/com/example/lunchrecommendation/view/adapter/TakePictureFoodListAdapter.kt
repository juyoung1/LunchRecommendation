package com.example.lunchrecommendation.view.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lunchrecommendation.data.dao.MenuDao
import com.example.lunchrecommendation.databinding.ItemMyLikeFoodListBinding
import com.example.lunchrecommendation.util.PreferencesUtil
import com.example.lunchrecommendation.view.mypage.activity.ActTakePictureFood

/**
 * 내가 찍은 음식 리스트 어댑터
 */
class TakePictureFoodListAdapter(val context: Context?, private val list: ArrayList<MenuDao>): RecyclerView.Adapter<TakePictureFoodListAdapter.CustomViewHolder>() {

    interface SelectItem { fun selectItem(position: Int) }
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

                    // 내가 찍은 음식 이미지
                    Glide.with(ctx).load(dao.menuImage).into(ivFood)

                    // 찍은 이미지 삭제
                    ivDelete.tag = position
                    ivDelete.visibility = View.VISIBLE
                    ivDelete.setOnClickListener {

                        val pos = it.tag.toString().toInt()
                        selectItem?.selectItem(pos)
                    }
                }
            }
        }
    }

    // 사진 찍거나 앨범에서 선택 시 리사이클러뷰에 추가
    fun addPhoto(photoUri: Uri) {

        list.add(MenuDao(menuImage = photoUri.toString()))

        // 찍은 사진 저장
        saveFoodPhotos()

        // 찍은 사진 없을 시 문구 노춡
        (context as? ActTakePictureFood)?.noPhotoVisibility()

        notifyDataSetChanged()
    }

    // 찍은 이미지 삭제
    fun removePhoto(position: Int) {

        if (position in 0 until list.size) {
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)

            saveFoodPhotos()

            // 찍은 사진 없을 시 문구 노춡
            (context as? ActTakePictureFood)?.noPhotoVisibility()
        }
    }

    // 찍거나 삭제한 상태 저장
    private fun saveFoodPhotos() {

        val saveFoodPhotos = list.map { it.menuImage.toString() }.toSet()
        PreferencesUtil.setPreferencesStringSet("saveFoodPhotos", saveFoodPhotos)
    }
}