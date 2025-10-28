pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven { setUrl("https://tencent-tds-maven.pkg.coding.net/repository/shiply/repo/") }
        maven { setUrl("https://tencent-tds-maven.pkg.coding.net/repository/shiply/repo-snapshot/") }
        maven {
            setUrl("https://maven.cnb.cool/tencent-tds/shiply-public/-/packages//")
        }
    }

    resolutionStrategy {
        eachPlugin {
            // 当 Gradle 尝试解析 ID 为 "com.tencent.rfix" 的插件时...
            if (requested.id.id == "com.tencent.rfix") {
                // ...明确告诉它应该使用哪个 Maven 构件
                useModule("com.tencent.rfix:RFix-gradle-plugin:${requested.version}")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://tencent-tds-maven.pkg.coding.net/repository/shiply/repo/") }
        maven { setUrl("https://tencent-tds-maven.pkg.coding.net/repository/shiply/repo-snapshot/") }
        maven {
            setUrl("https://maven.cnb.cool/tencent-tds/shiply-public/-/packages//")
        }
    }
}

rootProject.name = "HotFix"
include(":app")
 