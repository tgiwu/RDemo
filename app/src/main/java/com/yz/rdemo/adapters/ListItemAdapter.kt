package com.yz.rdemo.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.yz.rdemo.R
import com.yz.rdemo.net.model.ListItem

class ListItemAdapter(context: Context): RecyclerView.Adapter<ListItemAdapter.VH>() {
    var mList: List<ListItem>? = null
    var mOnItemClickListener : IOnItemClickListener? = null
    private val mContext = context

    fun changeList(list: List<ListItem>) {
        mList = list
        notifyDataSetChanged()
    }

    fun setOnItemClick(l: IOnItemClickListener) {
        mOnItemClickListener = l
    }

    fun getList() = mList

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): VH
        = VH(LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, null))


    override fun getItemCount(): Int = mList?.size?:0

    override fun onBindViewHolder(vh: VH, position: Int) {
        vh.portraitImg.setImageURI(mList!![position].portrait)
        vh.idTxt.text = mList!![position].id
        vh.nameTxT.text = mList!![position].name
        vh.view.setOnClickListener {
            if (vh.checkbox.visibility == View.VISIBLE) {
                mList!![position].checked = 2
            } else {
                mOnItemClickListener?.onItemClick(mList!![position])
            }
        }
        when(mList!![position].checked) {
            0 -> {
                vh.checkbox.isChecked = false
                vh.checkbox.isClickable = false
                if (vh.checkbox.visibility == View.VISIBLE) vh.checkbox.visibility = View.GONE
            }
            1 -> {
                vh.checkbox.isChecked = false
                vh.checkbox.isClickable = true
                if (vh.checkbox.visibility == View.GONE) vh.checkbox.visibility = View.VISIBLE
            }
            2 -> {
                vh.checkbox.isChecked = true
                vh.checkbox.isClickable = true
                if (vh.checkbox.visibility == View.GONE) vh.checkbox.visibility = View.VISIBLE
            }
            else ->{}
        }
    }

    inner class VH(v: View) : RecyclerView.ViewHolder(v){
        val view = v
        val portraitImg: ImageView = v.findViewById(R.id.item_portrait)
        val nameTxT: TextView = v.findViewById(R.id.item_name)
        val idTxt: TextView = v.findViewById(R.id.item_id)
        val checkbox: CheckBox = v.findViewById(R.id.item_checkbox)
    }

    interface IOnItemClickListener{
        fun onItemClick(item: ListItem)
    }
}