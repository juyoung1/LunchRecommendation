package com.example.lunchrecommendation.view.util

import com.example.lunchrecommendation.data.dao.MenuDao

object MenuListUtil {

    /**
     * 한식
     */
    fun koreaFood(): List<MenuDao> {
        val menuTitles = listOf(
            "된장찌개", "김치찌개", "간장게장", "비빔밥", "감자탕", "국밥", "삼겹살", "돼지갈비", "김치찜", "불고기", "갈비탕", "닭갈비", "닭볶음탕", "부대찌개")

        val menuImages = listOf(
            "https://cdn.pixabay.com/photo/2019/11/21/18/28/miso-soup-4643141_1280.jpg",
            "https://cdn.pixabay.com/photo/2022/12/29/01/01/food-7683985_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/11/15/09/46/korean-food-1825766_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/02/28/12/40/bibimbap-4887394_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/07/08/19/40/food-836806_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/04/14/17/01/soup-1329266_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/10/30/14/03/pork-1014180_1280.jpg",
            "https://cdn.pixabay.com/photo/2019/11/21/18/28/garlic-ribs-4643142_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/02/17/04/23/cooking-4855383_1280.jpg",
            "https://cdn.pixabay.com/photo/2017/07/19/03/13/pot-2517765_1280.jpg",
            "https://cdn.pixabay.com/photo/2023/07/21/09/18/soup-8141231_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/02/17/04/15/cooking-4855374_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/11/22/03/19/chicken-1848280_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/01/12/03/28/food-3077481_1280.jpg",
        )

        return createMenuList(menuTitles, menuImages)
    }

    /**
     * 일식
     */
    fun japanFood(): List<MenuDao> {
        val menuTitles = listOf(
            "초밥", "육회", "회", "장어 덮밥", "라멘", "돈카츠", "텐동", "소바", "돈카츠 덮밥", "카레", "오므라이스", "연어 덮밥", "우동")

        val menuImages = listOf(
            "https://cdn.pixabay.com/photo/2020/10/11/10/05/meal-5645374_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/01/13/06/45/meat-597951_1280.jpg",
        )

        return createMenuList(menuTitles, menuImages)
    }

    /**
     * 양식
     */
    fun westFood(): List<MenuDao> {
        val menuTitles = listOf(
            "스테이크", "파스타", "리조또", "스파게티", "감바스", "필라프",
        )

        val menuImages = listOf(
            "https://cdn.pixabay.com/photo/2020/10/11/10/05/meal-5645374_1280.jpg",
        )

        return createMenuList(menuTitles, menuImages)
    }

    /**
     * 분식
     */
    fun snackBar(): List<MenuDao> {
        val menuTitles = listOf(
            "김밥", "떡볶이", "순대", "라면", "돈가스", "튀김", "어묵", "유부초밥", "만두")

        val menuImages = listOf(
            "https://cdn.pixabay.com/photo/2017/08/08/09/44/food-photography-2610864_1280.jpg",
        )

        return createMenuList(menuTitles, menuImages)
    }


    /**
     * 패스트 푸드
     */
    fun fastFood(): List<MenuDao> {
        val menuTitles = listOf(
            "피자", "치킨", "햄버거", "핫도그", "샌드위치", "토스트", "타코")

        val menuImages = listOf(
            "https://cdn.pixabay.com/photo/2020/10/11/10/05/meal-5645374_1280.jpg",
        )

        return createMenuList(menuTitles, menuImages)
    }

    private fun createMenuList(menuTitles: List<String>, menuImages: List<String>): List<MenuDao> {
        return menuTitles.zip(menuImages) { title, image ->
            MenuDao().apply {
                menu = title
                file = image
            }
        }
    }
}