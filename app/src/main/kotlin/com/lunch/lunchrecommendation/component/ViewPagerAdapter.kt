package com.lunch.lunchrecommendation.component

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lunch.lunchrecommendation.base.BaseFragment

class ViewPagerAdapter(activity: AppCompatActivity, val fragments: ArrayList<BaseFragment<*>>) :
    FragmentStateAdapter(activity) {

    interface SelectItem { fun selectItem(position: Int, type: String) }
    var selectItem: SelectItem? = null

    override fun getItemCount(): Int {

        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun clear(activity: FragmentActivity?) {

        try{

            activity?.let {
                for (fragment in fragments) {
                    it.supportFragmentManager.beginTransaction().remove(fragment).commit()
                }
            }

        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
}