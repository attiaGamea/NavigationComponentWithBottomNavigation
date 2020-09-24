package com.ansargroup.add.selectcategory.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ansargroup.R
import com.multilevel.treelist.Node
import com.multilevel.treelist.TreeRecyclerAdapter
import kotlinx.android.synthetic.main.item_category.view.*

class CategoriesAdapter(
    datas: List<Node<*, *>?>?,
    defaultExpandLevel: Int,
    iconExpand: Int,
    iconNoExpand: Int,
) : TreeRecyclerAdapter(datas, defaultExpandLevel, iconExpand, iconNoExpand) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SubCategoriesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        node: Node<*, *>,
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val viewHolder = holder as SubCategoriesHolder

        if (node.icon == -1) {
            viewHolder.icon.visibility = View.INVISIBLE
        } else {
            viewHolder.icon.visibility = View.VISIBLE
            viewHolder.icon.setImageResource(node.icon)
        }
        viewHolder.label.text = node.name

        if (node.children.isEmpty()) {
            viewHolder.imgNext.visibility = View.VISIBLE
        } else {
            viewHolder.imgNext.visibility = View.GONE
        }

//        viewHolder.itemView.setOnClickListener {
//            if (node.children.isEmpty()){
//                onLeafNodeClick(node)
//            }
//        }
    }

    internal inner class SubCategoriesHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var label: TextView = itemView.tvLabel
        var icon: ImageView = itemView.imgCollapseExpandIcon
        var imgNext: ImageView = itemView.imgNext
    }
}