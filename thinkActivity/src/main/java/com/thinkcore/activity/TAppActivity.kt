package com.thinkcore.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.HashMap

//界面
abstract class TAppActivity : AppCompatActivity() {
    private val TAG = TAppActivity::class.java.canonicalName

    protected var context: Context? = null
    lateinit var status: Status
    var iActivityResult = HashMap<Int, TActivityUtils.IActivityResult>()

    val isActivityByStatus: Boolean
        get() = (status != Status.DESTORYED && status != Status.PAUSED
                && status != Status.STOPPED)

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
    }

    override fun onResume() {
        status = Status.RESUMED
        super.onResume()
    }

    override fun onPause() {
        status = Status.PAUSED
        super.onPause()
    }

    override fun onStop() {
        status = Status.STOPPED
        super.onStop()
    }

    override fun onDestroy() {
        TActivityManager.get().removeActivity(this)
        iActivityResult.clear()
        status = Status.DESTORYED
        context = null
        super.onDestroy()
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
}
