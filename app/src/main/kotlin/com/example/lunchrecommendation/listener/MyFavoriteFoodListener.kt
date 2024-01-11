package com.example.lunchrecommendation.listener

import com.example.lunchrecommendation.data.dao.MenuDao

interface MyFavoriteFoodListener {
    fun onFavoriteFood(menuList: List<MenuDao>)
}