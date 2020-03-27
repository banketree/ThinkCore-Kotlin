package com.thinkcore.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class TFragment : Fragment() {
    protected var that: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    open fun onShow() {
    }

    open fun onHide() {
    }

    fun isShowed(): Boolean {
        return isAdded && isVisible && isResumed
    }
}

/**
 * Fragment show
 */
inline fun <reified T : Fragment> Fragment.showFragment(
    replaceViewId: Int, init: (T).() -> Unit = {}
): T {
    val sfm = childFragmentManager
    val transaction = sfm.beginTransaction()
    var fragment = sfm.findFragmentByTag(T::class.java.name)
    val isFirstFragment = fragment == null
    if (fragment == null) {
        fragment = T::class.java.newInstance()
        transaction.add(replaceViewId, fragment, T::class.java.name)
    }
    sfm.fragments.filter { it != fragment }.forEach {
        (it as? TFragment)?.onHide()
        transaction.hide(it)
    }
    transaction.show(fragment)
    transaction.commitAllowingStateLoss()
    sfm.executePendingTransactions()
    init(fragment as T)
    (fragment as? TFragment)?.onShow()
    return fragment as T
}

/**
 * Fragment show
 */
inline fun Fragment.showFragment(
    fragment: Fragment,
    replaceViewId: Int
) {
    val sfm = childFragmentManager
    val transaction = sfm.beginTransaction()
    val isFirstFragment = !fragment.isAdded
    if (!fragment.isAdded) {
        transaction.add(replaceViewId, fragment, fragment.javaClass.name)
    }
    sfm.fragments.filter { it != fragment }.forEach {
        (it as? TFragment)?.onHide()
        transaction.hide(it)
    }
    transaction.show(fragment)
    transaction.commitAllowingStateLoss()
    sfm.executePendingTransactions()
    (fragment as? TFragment)?.onShow()
}