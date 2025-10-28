# 流程

1. `./gradlew build`
2. `./gradlew backup` 这会生成RFix文件夹
3. 拷贝并命名 ./app/debug/app-release.apk 到 ./app/RFix/old.apk
4. `./gradlew clean`
5. 修改MainActivity.kt 的内容并保存
6. `./gradlew build`
7. 拷贝并命名 ./app/debug/app-release.apk 到 ./app/RFix/new.apk
8. Shiply控制台，参考文档...
9. 运行apk测试
