package com.andriiginting.jetpackpro.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapter<T, VH : RecyclerView.ViewHolder>(
    private val onCreateViewHolder: (parent: ViewGroup, viewType: Int) -> VH,
    private val onBindViewHolder: (viewHolder: VH, position: Int, item: T) -> Unit,
    private val onViewType: ((viewType: Int, item: List<T>) -> Int)? = null,
    private val onDetachedFromWindow: ((VH) -> Unit)? = null) : RecyclerView.Adapter<VH>(), AdapterObserver<T> {

    var items = mutableListOf<T>()
    private var onGetItemViewType: ((position: Int) -> Int)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        onCreateViewHolder.invoke(parent, viewType)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        onBindViewHolder.invoke(holder, position, item)
    }

    override fun getItemViewType(position: Int): Int {
        return if (onViewType != null) {
            onViewType.invoke(position, items)
        } else {
            val onGetItemViewType = onGetItemViewType
            onGetItemViewType?.invoke(position) ?: super.getItemViewType(position)
        }
    }

    override fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.size)
    }

    /* define you item checked, for example items.size */
    override fun add(item: T, index: Int) {
        items.add(index, item)
        notifyItemInserted(index)
    }

    /* define you item checked, for example items.size */
    override fun addAll(collection: Collection<T>, index: Int) {
        items.addAll(index, collection)
        notifyItemRangeInserted(index, items.size)
    }

    override fun addAll(collection: Collection<T>) {
        items.addAll(collection)
        notifyDataSetChanged()
    }

    override fun safeAddAll(collection: Collection<T>?) {
        collection?.let {
            items.addAll(collection)
            notifyDataSetChanged()
        }
    }

    override fun clearAndAddAll(collection: Collection<T>) {
        items.clear()
        addAll(collection)
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        onDetachedFromWindow?.invoke(holder)
    }

    /* remove only single item */
    override fun remove(item: T) {
        val index = items.indexOfFirst { it == item }
        if (index >= 0) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    /* remove only single item with index */
    override fun safeRemove(index: Int) {
        if (index >= 0) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    /* this use for example range of collection from n to t checked */
    override fun removeRange(collection: Collection<T>) {
        collection.forEachIndexed { index, item ->
            items.remove(item)
            notifyItemRemoved(index)
        }
    }

    /* this use for example collection but in difference order */
    override fun removeRange(vararg item: T) {
        for (i in item) {
            remove(i)
        }
    }

    override fun update(item: T) {
        val index = items.indexOfFirst { it == item }
        notifyItemChanged(index)
    }

    override fun update(index: Int) {
        update(items[index])
    }

    override fun updateAll(collection: Collection<T>) {
        collection.forEachIndexed { position, item ->
            items[position] = item
        }
        notifyDataSetChanged()
    }

    override fun safeClearAndAddAll(collection: Collection<T>) {
        collection.let {
            clear()
            addAll(collection)
        }
    }

    override fun safeUpdateAll(collection: Collection<T>?) {
        collection?.forEachIndexed { position, item ->
            items[position] = item
        }
        notifyDataSetChanged()
    }

    override fun updateRange(collection: Collection<T>) {
        collection.forEachIndexed { index, _ ->
            update(items[index])
        }
    }

    override fun updateRange(vararg item: T) {
        for (i in item) {
            update(i)
        }
    }

    override fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }

}

interface AdapterObserver<T> {
    fun add(item: T)

    fun add(item: T, index: Int)

    fun addAll(collection: Collection<T>, index: Int)

    fun addAll(collection: Collection<T>)

    fun safeAddAll(collection: Collection<T>?)

    fun remove(item: T)

    fun safeRemove(index: Int)

    fun removeRange(collection: Collection<T>)

    fun removeRange(vararg item: T)

    fun clearAndAddAll(collection: Collection<T>)

    fun update(item: T)

    fun update(index: Int)

    fun updateAll(collection: Collection<T>)

    fun safeClearAndAddAll(collection: Collection<T>)

    fun safeUpdateAll(collection: Collection<T>?)

    fun updateRange(collection: Collection<T>)

    fun updateRange(vararg item: T)

    fun clear()
}