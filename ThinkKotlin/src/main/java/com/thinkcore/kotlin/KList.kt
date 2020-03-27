@file:Suppress("NOTHING_TO_INLINE")

package com.thinkcore.kotlin


inline fun <reified T : Any> subList(
    sourceList: List<T>,
    pageCount: Int
): ArrayList<ArrayList<T>>? {
    if (sourceList.isEmpty()) return null
    val resultList = arrayListOf<ArrayList<T>>()
    if (pageCount == 0 || sourceList.size <= pageCount) {
        val tempList = arrayListOf<T>()
        tempList.addAll(sourceList)
        resultList.add(tempList)
        return resultList
    }
    //取整数
    var nums = sourceList.size / pageCount
    //取余数
    val remain = sourceList.size % pageCount;
    if (remain > 0) nums += 1
    for (index in (0 until nums)) {
        var sub = if (index == nums - 1 && remain > 0) {
            sourceList.subList(index * pageCount, index * pageCount + remain)
        } else {
            sourceList.subList(index * pageCount, (index + 1) * pageCount)
        }
        val tempList = arrayListOf<T>()
        tempList.addAll(sub)
        resultList.add(tempList)
    }

    return resultList
}

inline fun <reified T : Any> removeList(
    sourceList: ArrayList<T>,
    isFilter: (T).() -> Boolean
) {
    val sourceIterator = sourceList.iterator()
    while (sourceIterator.hasNext()) {
        val next = sourceIterator.next()
        if (isFilter(next)) {
            sourceIterator.remove()
        }
    }
}

inline fun <reified T : Any> hasList(
    sourceList: ArrayList<T>,
    isFilter: (T).() -> Boolean
): Boolean {
    val sourceIterator = sourceList.iterator()
    while (sourceIterator.hasNext()) {
        val next = sourceIterator.next()
        if (isFilter(next)) {
            return true
        }
    }
    return false
}