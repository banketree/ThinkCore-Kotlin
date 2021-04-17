package com.thinkcore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author banketree
 * @time 2020/1/7 15:27
 * @description
 * 封装基础使用
 */
class DataAdapter<T> private constructor() : RecyclerView.Adapter<DataAdapter<T>.DataViewHolder>() {

    //数据
    var dataList: ArrayList<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    //布局id
    private var layoutId: Int? = null
    //绑定事件的lambda放发
    private var addBindView: ((itemView: View, itemData: T, index: Int) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DataViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(layoutId!!, p0, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: -1
    }

    override fun onBindViewHolder(p0: DataViewHolder, p1: Int) {
        addBindView?.invoke(p0.itemView, dataList?.get(p1)!!, p1)
    }

    fun getDataAt(index: Int): T? {
        return dataList?.get(index)
    }

    fun addData(data: T) {
        if (dataList == null) dataList = ArrayList<T>()
        dataList?.add(data)
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * 建造者，用来完成adapter的数据组合
     */
    class Builder<B> {
        private var adapter: DataAdapter<B> = DataAdapter()

        /**
         * 设置数据
         */
        fun setData(lists: ArrayList<B>): Builder<B> {
            adapter.dataList = lists
            return this
        }

        /**
         * 设置布局id
         */
        fun setLayoutId(layoutId: Int): Builder<B> {
            adapter.layoutId = layoutId
            return this
        }

        /**
         * 绑定View和数据
         */
        fun addBindView(itemBind: ((itemView: View, itemData: B, index: Int) -> Unit)): Builder<B> {
            adapter.addBindView = itemBind
            return this
        }

        fun create(): DataAdapter<B> {
            return adapter
        }
    }
}