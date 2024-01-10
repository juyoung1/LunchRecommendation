package com.example.lunchrecommendation.component

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.lunchrecommendation.extensions.toDp
import kotlin.math.ceil

class GridLayoutItemDecoration(var context: Context, private val outVerticalSpacing: Int, private val spacing: Int, var spanCount: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        val totalLine = ceil(state.itemCount.toDouble() / spanCount).toInt()
        val currentLine = ceil((position + 1).toDouble() / spanCount).toInt()

        val mOutVerticalSpacing = outVerticalSpacing.toDp(context).toInt()
        val mSpacing = spacing.toDp(context).toInt()

        // inner horizontal margin
        outRect.left = (column * mSpacing / spanCount)
        outRect.right = (mSpacing - (column + 1) * mSpacing / spanCount)

        // vertical top margin
        if (position < spanCount) {
            // outRect.top = mOutVerticalSpacing          // first line
            outRect.top = 0          // first line
        } else {
            outRect.top = mSpacing                     // other lines
        }

        // if (currentLine == totalLine) outRect.bottom = mOutVerticalSpacing   // last line vertical bottom margin
        if (currentLine == totalLine) outRect.bottom = 0   // last line vertical bottom margin
    }
}