package com.example.lunchrecommendation.view.util

import com.example.lunchrecommendation.data.dao.MenuDao

object MenuListUtil {

    /**
     * 한식
     */
    fun koreaFood(): List<MenuDao> {
        val menuTitles = listOf(
            "된장찌개", "김치찌개", "간장게장", "비빔밥", "감자탕", "국밥", "삼겹살", "돼지갈비", "김치찜", "불고기", "갈비탕", "닭갈비", "닭볶음탕", "부대찌개", "소고기", "족발", "보쌈", "오리 고기", "김치 볶음밥")

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
            "https://cdn.pixabay.com/photo/2019/08/16/00/12/beef-4409138_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/01/29/07/46/ham-hocks-4801727_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/01/29/07/46/bossam-4801728_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/12/27/09/17/duck-meat-1933542_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/01/09/10/14/kimchi-fried-rice-241051_1280.jpg"
        )

        return createMenuList(menuTitles, menuImages)
    }

    /**
     * 면
     */
    fun noodle(): List<MenuDao> {
        val menuTitles = listOf("라면", "짜장면", "우동", "라멘", "파스타", "볶음면", "짬뽕", "칼국수", "잔치 국수", "쌀국수")

        val menuImages = listOf(
            "https://cdn.pixabay.com/photo/2015/12/08/19/08/steak-1083567_1280.jpg",
        )
        return createMenuList(menuTitles, menuImages)
    }

    /**
     * 일식
     */
    fun japanFood(): List<MenuDao> {
        val menuTitles = listOf("초밥", "육회", "회", "장어 덮밥", "라멘", "돈카츠", "텐동", "소바", "규동", "카레", "오므라이스", "연어 덮밥", "우동")

        val menuImages = listOf(
            "https://cdn.pixabay.com/photo/2015/10/06/19/10/sushi-975075_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/01/13/06/45/meat-597951_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/03/30/17/45/japanese-food-4984955_1280.jpg",
            "https://cdn.pixabay.com/photo/2017/06/13/15/16/eel-2399145_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/03/15/10/33/bars-ramen-in-saigon-3227779_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/09/23/23/23/restaurant-1690696_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/11/01/16/46/shrimp-tempura-1788864_1280.jpg",
            "https://cdn.pixabay.com/photo/2022/10/01/04/04/soba-7490600_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/04/06/14/11/lunch-709423_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/10/01/14/29/curry-967086_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/04/21/17/29/omurice-733566_1280.jpg",
            "https://cdn.pixabay.com/photo/2019/11/25/15/19/salmon-4652298_1280.jpg",
            "https://cdn.pixabay.com/photo/2021/10/08/13/25/udon-6691341_1280.jpg",
        )
        return createMenuList(menuTitles, menuImages)
    }

    /**
     * 중식
     */
    fun chinaFood(): List<MenuDao> {
        val menuTitles = listOf(
            "짜장면", "짬뽕", "탕수육", "깐풍기", "볶음밥", "가지 덮밥", "깐쇼 새우", "팔보채", "난자완스", "동파육", "마라탕")

        val menuImages = listOf(
            "https://cdn.pixabay.com/photo/2017/08/08/09/44/food-photography-2610864_1280.jpg",
        )
        return createMenuList(menuTitles, menuImages)
    }

    /**
     * 양식
     */
    fun westFood(): List<MenuDao> {
        val menuTitles = listOf("스테이크", "파스타", "리조또", "감바스", "필라프", "바베큐")

        val menuImages = listOf(
            "https://cdn.pixabay.com/photo/2015/12/08/19/08/steak-1083567_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/07/18/19/12/pasta-3547078_1280.jpg",
            "https://cdn.pixabay.com/photo/2022/10/30/05/42/pumpkin-risotto-7556652_1280.jpg",
            "https://cdn.pixabay.com/photo/2021/01/16/08/10/gambas-al-high-5921367_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/10/29/22/49/fried-rice-508721_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/06/25/21/24/spare-ribs-5340942_1280.jpg"
        )
        return createMenuList(menuTitles, menuImages)
    }

    /**
     * 분식
     */
    fun snackBar(): List<MenuDao> {
        val menuTitles = listOf(
            "김밥", "떡볶이", "순대", "라면", "돈가스", "튀김", "어묵", "유부 초밥", "즉석 떡볶이", "만두")

        val menuImages = listOf(
            "https://cdn.pixabay.com/photo/2017/08/08/09/44/food-photography-2610864_1280.jpg",
            "https://cdn.pixabay.com/photo/2017/09/10/14/23/toppokki-2735719_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/02/28/07/47/measly-1226570_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/09/17/12/25/ramen-5579023_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/06/30/17/26/cutlet-380537_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/05/22/15/05/cooking-351122_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/03/24/13/22/food-687613_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/02/03/15/44/bob-257462_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/11/04/09/08/toppokki-3793393_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/02/17/10/41/dumplings-1204814_1280.jpg"
        )
        return createMenuList(menuTitles, menuImages)
    }


    /**
     * 패스트 푸드
     */
    fun fastFood(): List<MenuDao> {
        val menuTitles = listOf(
            "피자", "치킨", "햄버거", "핫도그", "샌드위치", "토스트", "타코", "베이글")

        val menuImages = listOf(
            "https://cdn.pixabay.com/photo/2014/07/08/12/34/pizza-386717_1280.jpg",
            "https://cdn.pixabay.com/photo/2019/09/26/18/23/republic-of-korea-4506696_1280.jpg",
            "https://cdn.pixabay.com/photo/2019/04/22/08/37/burger-4145977_1280.jpg",
            "https://cdn.pixabay.com/photo/2023/07/08/17/44/food-8114889_1280.jpg",
            "https://cdn.pixabay.com/photo/2017/06/16/07/00/breakfast-2408035_1280.jpg",
            "https://cdn.pixabay.com/photo/2019/03/04/13/00/sandwich-4034047_1280.jpg",
            "https://cdn.pixabay.com/photo/2019/07/21/01/36/tacos-al-pastor-4351813_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/04/20/21/05/breakfast-732231_1280.jpg"
        )
        return createMenuList(menuTitles, menuImages)
    }

    private fun createMenuList(menuTitles: List<String>, menuImages: List<String>): List<MenuDao> {
        return menuTitles.zip(menuImages) { title, image ->
            MenuDao().apply {
                menu = title
                menuImage = image
            }
        }
    }
}