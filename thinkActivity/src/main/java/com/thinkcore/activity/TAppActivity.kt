package com.thinkcore.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.KeyEvent
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.util.*


/**
 * @author banketree
 * @time 2020/1/3 18:23
 * @description activity 状态管理+按键管理封装
 */
abstract class TAppActivity : AppCompatActivity() {
    private val TAG = TAppActivity::class.java.canonicalName

    private var context: Context? = null
    lateinit var status: Status
    val iActivityResult = HashMap<Int, IActivityResult>()

    val isActivityByStatus: Boolean
        get() = (status != Status.DESTORYED && status != Status.PAUSED
                && status != Status.STOPPED)

    //状态 以及 按键监听
    private val iStateListenerHashMap = HashMap<String, IStateListener?>()
    private val iKeyDownListenerHashMap = HashMap<String, IKeyDownListener?>()

    enum class Status {
        NONE, CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTORYED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        status = Status.CREATED
        TActivityManager.get().addActivity(this)// 添加activity
    }


    override fun finish() {
        super.finish()
    }

    override fun onStart() {
        status = Status.STARTED
        super.onStart()

        if (iStateListenerHashMap.isNotEmpty()) {
            for ((_, iStateListener) in iStateListenerHashMap) {
                iStateListener?.onState(Status.STARTED)
            }
        }
    }

    override fun onResume() {
        status = Status.RESUMED
        super.onResume()

        if (iStateListenerHashMap.isNotEmpty()) {
            for ((_, iStateListener) in iStateListenerHashMap) {
                iStateListener?.onState(Status.RESUMED)
            }
        }
    }

    override fun onPause() {
        status = Status.PAUSED
        super.onPause()

        if (iStateListenerHashMap.isNotEmpty()) {
            for ((_, iStateListener) in iStateListenerHashMap) {
                iStateListener?.onState(Status.PAUSED)
            }
        }
    }

    override fun onStop() {
        status = Status.STOPPED
        super.onStop()

        if (iStateListenerHashMap.isNotEmpty()) {
            for ((_, iStateListener) in iStateListenerHashMap) {
                iStateListener?.onState(Status.STOPPED)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        status = Status.DESTORYED

        if (iStateListenerHashMap.isNotEmpty()) {
            for ((_, iStateListener) in iStateListenerHashMap) {
                iStateListener?.onState(Status.DESTORYED)
            }
        }

        TActivityManager.get().removeActivity(this)
        iActivityResult.clear()
        iStateListenerHashMap.clear()
        iKeyDownListenerHashMap.clear()
        context = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val iActivityResult = this.iActivityResult[requestCode]
        if (iActivityResult != null) {
            iActivityResult.onActivityResult(resultCode, data)
            try {
                this.iActivityResult.remove(requestCode)
            } catch (ex: Exception) {
            }
        }
    }


    //状态 以及 按键监听
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (iKeyDownListenerHashMap.isNotEmpty()) {
            for ((_, iKeyDownListener) in iKeyDownListenerHashMap) {
                if (iKeyDownListener != null && iKeyDownListener.onKeyDown(keyCode, event)) {
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    fun addStateListener(key: String, listener: IStateListener?) {
        if (listener == null || TextUtils.isEmpty(key))
            return

        iStateListenerHashMap[key] = listener
    }

    fun removeStateListener(key: String) {
        if (TextUtils.isEmpty(key))
            return
        iStateListenerHashMap[key] = null
//        iStateListenerHashMap.remove(key)
    }

    fun addKeyDownListener(key: String, listener: IKeyDownListener?) {
        if (listener == null || TextUtils.isEmpty(key))
            return
        iKeyDownListenerHashMap[key] = listener
    }

    fun removeKeyDownListener(key: String) {
        if (TextUtils.isEmpty(key))
            return
        iKeyDownListenerHashMap[key] = null
        //        iKeyDownListenerHashMap.remove(key);
    }

    interface IStateListener {
        fun onState(state: Status)
    }
}

/**
 * @author banketree
 * @time 2020/1/9 10:54
 * @description
 * 跳转封装成扩展函数
 */
inline fun <reified T : Any> TAppActivity.jumpToActivity(
    targetIntent: Intent? = null,
    bundle: Bundle? = null
) = run {
    var actionIntent = targetIntent
    if (targetIntent == null) {
        actionIntent = Intent(this, T::class.java)
    }
    bundle?.let {
        actionIntent?.putExtras(it)
    }

    startActivity(actionIntent)
}

inline fun <reified T : Any> TAppActivity.jumpToNewTopActivity(
    targetIntent: Intent? = null,
    bundle: Bundle?
) = run {
    var actionIntent = targetIntent
    if (targetIntent == null) {
        actionIntent = Intent(this, T::class.java)
    }
    bundle?.let {
        actionIntent?.putExtras(it)
    }
    actionIntent?.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(actionIntent)
}

inline fun <reified T : Any> TAppActivity.jumpToClearNewTaskActivity(
    targetIntent: Intent? = null,
    bundle: Bundle?
) = run {
    var actionIntent = targetIntent
    if (targetIntent == null) {
        actionIntent = Intent(this, T::class.java)
    }
    bundle?.let {
        actionIntent?.putExtras(it)
    }

    actionIntent?.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(actionIntent)
}

inline fun TAppActivity.getRandomRequestCode(): Int =
    Random().nextInt(10000) + Random().nextInt(1000) + Random().nextInt(100)

inline fun <reified T : Any> TAppActivity.jumpToActivityForResult(
    bundle: Bundle? = null
    , resultId: Int = getRandomRequestCode()
    , iActivityResult: IActivityResult? = null
) = run {
    var actionIntent = Intent(this, T::class.java)

    iActivityResult?.let {
        this.iActivityResult[resultId] = it
    }
    bundle?.let {
        actionIntent.putExtras(it)
    }
    startActivityForResult(actionIntent, resultId)
}

inline fun TAppActivity.jumpToActivityForResult(
    targetIntent: Intent,
    bundle: Bundle? = null
    , resultId: Int = getRandomRequestCode()
    , iActivityResult: IActivityResult? = null
) = run {
    iActivityResult?.let {
        this.iActivityResult[resultId] = it
    }
    bundle?.let {
        targetIntent.putExtras(it)
    }
    startActivityForResult(targetIntent, resultId)
}


/**
 * Activity show
 */
inline fun <reified T : Fragment> FragmentActivity.showFragment(
    replaceViewId: Int, init: (T).() -> Unit = {}
): T {
    val sfm = supportFragmentManager
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
    return fragment
}

/**
 * Activity show
 */
inline fun FragmentActivity.showFragment(
    fragment: Fragment,
    replaceViewId: Int
) {
    val sfm = supportFragmentManager
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