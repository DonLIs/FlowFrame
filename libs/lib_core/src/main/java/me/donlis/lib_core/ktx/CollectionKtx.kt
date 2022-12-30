package me.donlis.lib_core.ktx


/**
 * Desc:容器类工具
 */

/**
 * 删除符合条件的条目
 */
fun <T> ArrayList<T>.removeIf2(filter: (T) -> Boolean): Boolean {
    return this.listIterator().removeIf2(filter)
}

/**
 * 删除符合条件的第一个条目
 */
fun <T> ArrayList<T>.removeFirst2(predicate: (T) -> Boolean): Boolean {
    return this.listIterator().removeFirst2(predicate)
}

/**
 * 删除符合条件的条目
 */
fun <T> ArrayList<T>.removeIfIndexed2(predicate: (Int, T) -> Boolean): Boolean {
    var removed = false
    var i = 0
    while (i < this.size) {
        val item: T = this[i]
        if (predicate.invoke(i, item)) {
            this.removeAt(i)
            removed = true
            i--
        }
        i++
    }
    return removed
}


/**
 * 删除符合条件的条目
 */
fun <T> MutableList<T>.removeIf2(predicate: (T) -> Boolean): Boolean {
    return this.listIterator().removeIf2(predicate)
}

/**
 * 删除符合条件的第一个条目
 */
fun <T> MutableList<T>.removeFirst2(predicate: (T) -> Boolean): Boolean {
    return this.listIterator().removeFirst2(predicate)
}

/**
 * 删除符合条件的条目
 */
fun <T> MutableList<T>.removeIfIndexed2(predicate: (Int, T) -> Boolean): Boolean {
    var removed = false
    var i = 0
    while (i < this.size) {
        val item: T = this[i]
        if (predicate.invoke(i, item)) {
            this.removeAt(i)
            removed = true
            i--
        }
        i++
    }
    return removed
}

/**
 * 删除符合条件的条目
 */
fun <T> MutableIterator<T>.removeIf2(predicate: (T) -> Boolean): Boolean {
    var removed = false
    while (this.hasNext()) {
        if (predicate.invoke(this.next())) {
            this.remove()
            removed = true
        }
    }
    return removed
}


/**
 * 删除符合条件的第一个条目
 */
fun <T> MutableIterator<T>.removeFirst2(predicate: (T) -> Boolean): Boolean {
    while (this.hasNext()) {
        if (predicate.invoke(this.next())) {
            this.remove()
            return true
        }
    }
    return false
}

/**
 * 拆分列表
 * @param pageSize 每页大小
 */
fun <T> List<T>.split(pageSize: Int): ArrayList<ArrayList<T>> {
    val pagerList = ArrayList<ArrayList<T>>()
    var currentList = ArrayList<T>()
    this.forEachIndexed { index, item ->
        currentList.add(item)
        if (currentList.size == pageSize || index == this.size - 1) {
            pagerList.add(currentList)
            currentList = ArrayList()
        }
    }
    return pagerList
}

/**
 * 对每个剩余元素执行给定的操作，直到所有元素都已处理或该操作引发异常。如果指定了迭代顺序，则按迭代顺序执行操作。操作引发的异常将转发给调用者。
 */
fun <T> Iterator<T>.forEachRemaining2(action: (T) -> Unit) {
    while (hasNext()) {
        action.invoke(next())
    }
}


/**
 * 对每个剩余元素执行给定的操作，直到所有元素都已处理或该操作引发异常。如果指定了迭代顺序，则按迭代顺序执行操作。操作引发的异常将转发给调用者。
 */
fun <T> List<T>.forEachRemaining2(action: (T) -> Unit) {
    iterator().forEachRemaining2(action)
}