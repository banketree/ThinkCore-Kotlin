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
class MultiTypeAdapter<T> private constructor() :
    RecyclerView.Adapter<MultiTypeAdapter<T>.DataViewHolder>() {

    //数据
    var dataList: ArrayList<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    //绑定事件的lambda放发
    private var onBindView: ((itemView: View, itemData: T, index: Int, viewType: Int) -> Unit)? =
        null
    //绑定创建布局事件
    private var createViewHolder: ((parent: ViewGroup, viewType: Int) -> Int)? = null
    //绑定类型事件
    private var getBindViewType: ((itemData: T, index: Int) -> Int)? = null

    override fun getItemViewType(position: Int): Int {
        if (dataList == null || position >= dataList!!.size || getBindViewType == null) {
            return super.getItemViewType(position)
        }
        return getBindViewType!!.invoke(dataList!![position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layoutId = createViewHolder!!.invoke(parent, viewType)
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return DataViewHolder(view, viewType)
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: -1
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        dataList ?: return
        if (position >= dataList!!.size) return
        dataList?.get(position)?.let {
            onBindView?.invoke(holder.itemView, it, position, holder.itemType)
        }
    }

    fun getDataAt(index: Int): T? {
        return dataList?.get(index)
    }

    fun addData(data: T) {
        if (dataList == null) dataList = ArrayList<T>()
        dataList?.add(data)
    }

    inner class DataViewHolder(itemView: View, val itemType: Int) :
        RecyclerView.ViewHolder(itemView)

    /**
     * 建造者，用来完成adapter的数据组合
     */
    class Builder<B> {
        private var adapter: MultiTypeAdapter<B> = MultiTypeAdapter()

        /**
         * 设置数据
         */
        fun setData(lists: ArrayList<B>): Builder<B> {
            adapter.dataList = lists
            return this
        }

        /**
         * 绑定View和数据
         */
        fun onBindView(onBindView: ((itemView: View, itemData: B, index: Int, viewType: Int) -> Unit)): Builder<B> {
            adapter.onBindView = onBindView
            return this
        }

        /**
         * 绑定创建布局事件
         */
        fun createViewHolder(createViewHolder: ((parent: ViewGroup, viewType: Int) -> Int)): Builder<B> {
            adapter.createViewHolder = createViewHolder
            return this
        }

        /**
         * 绑定类型事件
         */
        fun getBindViewType(getBindViewType: ((itemData: B, index: Int) -> Int)): Builder<B> {
            adapter.getBindViewType = getBindViewType
            return this
        }

        fun create(): MultiTypeAdapter<B> {
            return adapter
        }
    }
}