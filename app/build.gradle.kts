import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.rfix)
    kotlin("kapt")
}

android {
    namespace = "com.test.hotfix"
    compileSdk = 36
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.test.hotfix"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
//    splits {
//        abi {
//            isEnable =  true
//            reset()
//            include("armeabi-v7a", "arm64-v8a")
//            isUniversalApk = true
//        }
//    }
    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    lint {
        abortOnError = false
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.rfix)
    kapt(libs.rfix.anno)
    compileOnly(libs.rfix.anno)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}


RFixPatch {
    // 补丁类型：Disable/Tinker
    patchType = "Tinker"
    // 修复目标Apk，通常为对外发布的Release版本
    oldApks = listOf("${projectDir.absolutePath}/RFix/old.apk")
    // 修复后的Apk，在目标版本基础上修改代码后的版本
    newApks = listOf("${projectDir.absolutePath}/RFix/new.apk")
    // 补丁输出目录
    outputFolder = "${projectDir.absolutePath}/RFix/"

    // 忽略补丁编译中的告警信息（该功能谨慎开启，忽略某些告警后生成的补丁可能异常）
    ignoreWarning = true

    buildConfig {
        // Apk的唯一补丁标识，用于识别补丁和Apk是否匹配
        patchId = SimpleDateFormat("MMddHHmmss").format(Date())
        // 使用splits实现多架构编译时需要开启该功能，以确保每个Apk的PatchId不同
        appendOutputNameToPatchId = false

        // 修复目标Apk的代码混淆文件和资源ID映射文件
        applyMapping = "${projectDir.absolutePath}/RFix/old_mapping.txt"
        applyResourceMapping = "${projectDir.absolutePath}/RFix/old_R.txt"

        signingConfig {
            this.keyAlias = "123456"
            this.storeFile = file("../123456.jks").also { println("jks file is ${it.absolutePath}") }
            this.keyPassword = "123456"
            this.storePassword = "123456"
        }

        // 开启多架构补丁独立打包功能
        enablePackageSeparate = false
        // 开启加固包的补丁构建模式
        isProtectedApp = false


    }

    dex {
        loader = mutableListOf(
            //use sample, let BaseBuildInfo unchangeable with tinker
            "com.tencent.rfix.loader.*",
        )
        /**
         * 这个官网没有写
         * 如果更改了AppLike类，就会有几个类（com.tencent.tinker.loader.*）提示被修改了但是不在忽略列表里面，导致打patch包失败
         */
        ignoreWarningLoader = mutableListOf(
            "com.tencent.tinker.loader.*",
            "com.tencent.rfix.loader.*",
            "com.google.android.material.*",
            "androidx.*")

    }
}

tasks.register<Copy>("backup") {
    // 备份 mapping.txt
    from("${buildDir}/outputs/mapping/release/mapping.txt")
    into("${projectDir.absolutePath}/RFix/old_mapping.txt")

// 备份 R.txt
// 不同AGP版本下的R.txt位置有差异
    from("${buildDir}/intermediates/symbols/release/R.txt")
    from("${buildDir}/intermediates/symbol_list/release/R.txt")
    from("${buildDir}/intermediates/runtime_symbol_list/release/R.txt")
    from("${buildDir}/intermediates/runtime_symbol_list/release/processReleaseResources/R.txt")
    into("${projectDir.absolutePath}/RFix/old_R.txt")
}