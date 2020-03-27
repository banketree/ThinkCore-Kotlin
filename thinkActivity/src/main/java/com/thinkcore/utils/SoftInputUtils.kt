package com.thinkcore.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener


/**
 * @author banketree
 * @time 2020/3/27 14:06
 * @description
 * 软盘监听
 */
object SoftInputUtils {
    fun monitorSoftAction(editText: EditText, iSoftAction: ISoftAction) {
        editText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE || event != null && KeyEvent.KEYCODE_ENTER == event.keyCode && KeyEvent.ACTION_DOWN == event.action) {
                iSoftAction.actionDo()
                return@OnEditorActionListener true
            }
            false
        })
    }

    fun monitorSoftInput(editText: EditText, iSoftInput: ISoftInput) {
        if (editText.parent == null) return
        monitorSoftInput(editText.parent as ViewGroup, iSoftInput)
    }

    fun monitorSoftInput(activity: Activity, iSoftInput: ISoftInput) {
        monitorSoftInput(activity.window.decorView, iSoftInput)
    }

    fun isShowKeyboard(context: Context, v: View): Boolean {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return if (imm.hideSoftInputFromWindow(v.windowToken, 0)) {
            imm.showSoftInput(v, 0)
            true
            //软键盘已弹出
        } else {
            false
            //软键盘未弹出
        }
    }

    fun monitorSoftInput(view: View, iSoftInput: ISoftInput) {
        //记录每次的状态  避免重复
        val softInputRecord = SoftInputRecord(iSoftInput)
        view.viewTreeObserver.addOnGlobalLayoutListener(
            OnGlobalLayoutListener {
                //获取当前根视图在屏幕上显示的大小
                val r = Rect()
                view.getWindowVisibleDisplayFrame(r)
                val visibleHeight = r.height()
                if (softInputRecord.rootViewVisibleHeight === 0) {
                    softInputRecord.rootViewVisibleHeight = visibleHeight
                    return@OnGlobalLayoutListener
                }
                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                if (softInputRecord.rootViewVisibleHeight === visibleHeight) {
                    return@OnGlobalLayoutListener
                }
                //根视图显示高度变小超过200，可以看作软键盘显示了
                if (softInputRecord.rootViewVisibleHeight - visibleHeight > 200) {
                    iSoftInput.softInputStatus(
                        true,
                        softInputRecord.rootViewVisibleHeight - visibleHeight
                    )
                    softInputRecord.rootViewVisibleHeight = visibleHeight
                    return@OnGlobalLayoutListener
                }
                //根视图显示高度变大超过200，可以看作软键盘隐藏了
                if (visibleHeight - softInputRecord.rootViewVisibleHeight > 200) {
                    iSoftInput.softInputStatus(false, 0)
                    softInputRecord.rootViewVisibleHeight = visibleHeight
                    return@OnGlobalLayoutListener
                }
            })
    }

    interface ISoftInput {
        fun softInputStatus(show: Boolean, keyboardHeight: Int)
    }

    interface ISoftAction {
        fun actionDo()
    }
}

//记录软盘状态
class SoftInputRecord {
    var rootViewVisibleHeight = 0
    var iSoftInput: SoftInputUtils.ISoftInput? = null

    constructor(iSoftInput: SoftInputUtils.ISoftInput?) {
        this.iSoftInput = iSoftInput
    }
}

inline fun EditText.monitorSoftAction(iSoftAction: SoftInputUtils.ISoftAction) {
    SoftInputUtils.monitorSoftAction(this, iSoftAction)
}

inline fun EditText.monitorSoftInput(iSoftInput: SoftInputUtils.ISoftInput) {
    SoftInputUtils.monitorSoftInput(this, iSoftInput)
}

inline fun Activity.monitorSoftInput(iSoftInput: SoftInputUtils.ISoftInput) {
    SoftInputUtils.monitorSoftInput(this, iSoftInput)
}

inline fun Activity.isShowKeyboard(): Boolean {
    return SoftInputUtils.isShowKeyboard(this, this.window.decorView)
}
