package com.test.hotfix.app

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tencent.rfix.anno.ApplicationLike
import com.tencent.rfix.entry.DefaultRFixApplicationLike
import com.tencent.rfix.entry.RFixApplicationLike
import com.tencent.rfix.lib.RFixInitializer
import com.tencent.rfix.lib.RFixListener
import com.tencent.rfix.lib.RFixParams
import com.tencent.rfix.lib.config.PatchConfig
import com.tencent.rfix.lib.entity.RFixPatchResult
import com.tencent.rfix.loader.entity.RFixLoadResult


@ApplicationLike(application = ".MainLikeApplication")
class MainApplication : DefaultRFixApplicationLike {

    constructor(application: Application, loadResult: RFixLoadResult): super(application, loadResult)

    companion object {
        fun initRFix(applicationLike: RFixApplicationLike) {


            // 在初始化时可以注册回调，以便在各阶段处理业务逻辑

            RFixParams("60f44893b4", "a0b180f3-622b-41aa-bcea-f11d391aa88c")
                .setDeviceManufacturer(Build.MANUFACTURER)  // 设置设备厂商，用于下发规则控制
                .setDeviceModel(Build.MODEL)                // 设置设备型号，用于下发规则控制
                .setUserId("123456")                        // 设置用户ID，用于下发规则控制
                .setCustomProperty("guid", "123456")	    // 设置自定义属性，用于扩展下发规则
                .setCustomProperty("versionName", "1.0")	    // 设置自定义属性，用于扩展下发规则
                .setCustomProperty("versionCode", "1")	    // 设置自定义属性，用于扩展下发规则
                .apply {
                    RFixInitializer.initialize(applicationLike, this, object : RFixListener {
                        override fun onConfig(success: Boolean, resultCode: Int, patchConfig: PatchConfig?) {
                            Toast.makeText(applicationLike.application.applicationContext, "onConfig : $success \t $resultCode", Toast.LENGTH_LONG).show()
                        }

                        override fun onDownload(success: Boolean, resultCode: Int, patchConfig: PatchConfig?, patchFilePath: String?) {
                        }

                        override fun onInstall(success: Boolean, resultCode: Int, patchResult: RFixPatchResult) {
                            if (success && patchResult.isPatchSuccessFirstTime()) {
                            }
                        }
                    })
                    RFixInitializer.initialize(applicationLike, this)
                }
        }
    }

    override fun onBaseContextAttached(p0: Context?) {
        super.onBaseContextAttached(p0)
        initRFix(this)
    }
}