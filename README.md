# 简介

一个好的Android应用开发框架，可以加快Android开发速度，今天笔记基于许多开源项目自写了一款Android应用框架。



# 内容

框架包括：界面管理（Activity管理）、数据库操作（orm框架、加密等）、http交互、缓存管理、下载管理、路径管理、日志输出、网络管理、系统命令、加解密、stl容器、任务管理、奔溃管理、存储管理、其它辅助管理…。



# 解决思路如下：

界面管理：使用Stack记录Activity的运行轨迹，并在每个Activity添加一个广播处理自定义事件。

	private static Stack mActivityStack;//进行Activity运行记录
数据库管理：对数据库的操作要求orm框架、加解密等，采用的是greendao和sqlcrypt开源项目解决。
http交互：实现http交互方式有同步、异步操作。采用的是android-async-http开源项目解决。

缓存管理：实现缓存方式有内存缓存、磁盘缓存。其中内存缓存只针对图片处理，磁盘缓存是针对某目录，实现的方式有：先进先出、最大量、使用频率等。

下载管理：下载管理方式有多线程下载、单线程下载。

路径管理：简单的说就是对程序的目录管理，分图片、视频、音频、缓存目录等。

日志输出：基于Log的管理（分调式、发布模式和文件输出、log输出）。

网络管理：2G、3G、4G等联网方式不同的管理和监听。

系统命令：直接命令运行。

加解密：aes、base64、des、md5等加密方式。

stl容器：重写stl部分容器。

任务管理：基于AsyncTask任务的管理。

奔溃管理：捕捉奔溃信息，并提供发送接口。采用的是acra方法解决。

存储管理：主要是SharedPreferences和Properties的管理。

其它辅助管理：版本、内存、手机格式、字符串操作……等等。

界面布局 适配：对所有的布局自定义适配 参考开源 autoSize 并精简优化。



导入第一步：

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
导入第二步：添加引用
	dependencies {
		//界面管理
		implementation 'com.github.banketree.ThinkCore-Kotlin:autolayout:v0.0.3'  //
		
	        implementation 'com.github.banketree.ThinkCore-Kotlin:thinkActivity:v0.0.3'  //
		
		//缓存管理
		implementation 'com.github.banketree.ThinkCore-Kotlin:thinkCache:v0.0.3'
		
		//加密解密管理
		implementation 'com.github.banketree.ThinkCore-Kotlin:thinkEncryption:v0.0.3'
		
		//日志管理
		implementation 'com.github.banketree.ThinkCore-Kotlin:thinkLog:v0.0.3'
		
		//网络管理
		implementation 'com.github.banketree.ThinkCore-Kotlin:thinkNetwork:v0.0.3'
		
		//偏好设置管理
		implementation 'com.github.banketree.ThinkCore-Kotlin:thinkPreference:v0.0.3'
		
		//路径管理
		implementation 'com.github.banketree.ThinkCore-Kotlin:thinkStorage:v0.0.3'
		
		//任务管理
		implementation 'com.github.banketree.ThinkCore-Kotlin:thinkTask:v0.0.3'
		
		//辅助工具管理
		implementation 'com.github.banketree.ThinkCore-Kotlin:thinkUtils:v0.0.3'

		//辅助界面布局适配
		implementation 'com.github.banketree.ThinkCore-Kotlin:thinkAdapt:v0.0.3'
	}

