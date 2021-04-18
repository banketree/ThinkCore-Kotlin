package com.thinkcore.performance.config

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.thinkcore.module.core.AppLifeCycles
import com.thinkcore.module.core.ConfigModule

/***
 * 该类是整个module可以对整个App的Application/Activity/Fragment的生命周期进行逻辑注入
 * 例如我们平时的第三方代码就可以在这里去进行实现
 **/

class TestingConfiguration : ConfigModule {
    override fun injectAppLifecycle(context: Context, lifeCycles: MutableList<AppLifeCycles>) {
        lifeCycles.add(TestingApplicationLifeCyclesImpl())
    }

    override fun injectActivityLifecycle(
        context: Context,
        lifeCycles: MutableList<Application.ActivityLifecycleCallbacks>
    ) {
        lifeCycles.add(TestingActivityLifecycleCallbacksImpl())
    }

    override fun injectFragmentLifecycle(
        context: Context,
        lifeCycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>
    ) {
        lifeCycles.add(TestingFragmentLifecycleCallbacksImpl())
    }
}
