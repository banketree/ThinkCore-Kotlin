package me.jessyan.autosize.internal

import android.app.Activity

/**
 * AndroidAutoSize 默认项目中的所有模块都使用适配功能, 三方库的 [Activity] 也不例外
 * 如果某个页面不想使用适配功能, 请让该页面 [Activity] 实现此接口
 * 实现此接口表示放弃适配, 所有的适配效果都将失效
 */
interface CancelAdapt
