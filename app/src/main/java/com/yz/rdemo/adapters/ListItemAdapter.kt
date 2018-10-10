package com.yz.rdemo.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import com.yz.rdemo.R
import com.yz.rdemo.Utils.toStringList
import com.yz.rdemo.net.model.ListItem

class ListItemAdapter(context: Context): RecyclerView.Adapter<ListItemAdapter.VH>(), CompoundButton.OnCheckedChangeListener {
    private var mList: List<ListItem>? = null

    private var mOnItemClickListener : IOnItemClickListener? = null
    private var mSelectedSet = HashSet<String>()
    private val mContext = context

    fun changeList(list: List<ListItem>) {
        mList = list
        mSelectedSet.clear()
        notifyDataSetChanged()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        Log.i("zhy", " changed listener $isChecked")
        buttonView?.let {
            Log.i("zhy", " tag ${it.tag}")
            val checkBox = it as CheckBox
            Log.i("zhy", "checkbox tag ${checkBox.tag}")
            checkBox.isChecked = isChecked
            if (isChecked) {
                mSelectedSet.add(it.getTag().toString())
            } else {
                mSelectedSet.remove(it.getTag().toString())
            }
        }
    }

    fun setOnItemClick(l: IOnItemClickListener) {
        mOnItemClickListener = l
    }

    fun getSelected() : List<String>? {
        Log.i("zhy", "get selected ${mSelectedSet.size}")
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
                vh.checkbox.isChecked = !vh.checkbox.isChecked
            } else {
                mOnItemClickListener?.onItemClick(mList!![position])
            }
        }
        vh.checkbox.tag = mList!![position].id
        vh.checkbox.setOnCheckedChangeListener(this)
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
