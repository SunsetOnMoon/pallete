package com.example.pallete.itemdecoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridDividerItemDecoration(private val dividerSize: Int, private val dividerColor: Int) : RecyclerView.ItemDecoration() {
    private val dividerPaint = Paint()

    init {
        dividerPaint.color = dividerColor
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(dividerSize, dividerSize, dividerSize, dividerSize)
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val childAdapterPosition = parent.getChildAdapterPosition(child)
            val layoutManager = parent.layoutManager
            if (layoutManager is GridLayoutManager) {
                val gridLayoutManager = layoutManager

                val spanCount = gridLayoutManager.spanCount
                val column = childAdapterPosition % spanCount

                if (column != 0) {
                    // Draw left border
                    val left = child.left - dividerSize
                    val top = child.top
                    val right = child.left
                    val bottom = child.bottom
                    canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), dividerPaint)
                }

                if (childAdapterPosition >= spanCount) {
                    // Draw top border
                    val left = child.left
                    val top = child.top - dividerSize
                    val right = child.right
                    val bottom = child.top
                    canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), dividerPaint)
                }
            }
        }
    }
}