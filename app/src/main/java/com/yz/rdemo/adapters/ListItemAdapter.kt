package com.yz.rdemo.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.yz.rdemo.R
import com.yz.rdemo.Utils.toStringList
import com.yz.rdemo.net.model.ListItem

class ListItemAdapter(context: Context): RecyclerView.Adapter<ListItemAdapter.VH>() {
    private var mList: List<ListItem>? = null
    private var mOnItemClickListener : IOnItemClickListener? = null
    private var mSelectedSet = HashSet<String>()
    private val mContext = context

    fun changeList(list: List<ListItem>) {
        Log.i("zhy", "changeList")
        mList = list
        mSelectedSet.clear()
        notifyDataSetChanged()
    }

    fun setOnItemClick(l: IOnItemClickListener) {
        mOnItemClickListener = l
    }

    fun getSelected() : List<String>? {
        if (mSelectedSet.size == 0) return null
        return toStringList(mSelectedSet)
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): VH
        = VH(LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, p0, false))

    override fun getItemViewType(position: Int): Int = mList!![position].type

    override fun getItemCount(): Int = mList?.size?:0

    override fun onBindViewHolder(vh: VH, position: Int) {
        vh.portraitImg.setImageURI(mList!![position].portrait)
        vh.idTxt.text = mList!![position].id
        vh.nameTxT.text = mList!![position].name
        if (mList!![position].type == 1 ) {
            vh.checkbox.visibility = View.VISIBLE
        } else {
            vh.checkbox.visibility = View.GONE
        }
        vh.view.setOnClickListener {
            if (vh.checkbox.visibility == View.VISIBLE) {
                if (mSelectedSet.contains(mList!![position].id)) {
                    mSelectedSet.remove(mList!![position].id)
                    vh.checkbox.isChecked = false
                } else {
                    mSelectedSet.add(mList!![position].id)
                    vh.checkbox.isChecked = true
                }
            } else {
                mOnItemClickListener?.onItemClick(mList!![position])
            }
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
