# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 忽略 Kotlin 的反射相关的警告
-dontwarn kotlin.reflect.**

# 保留指定的类和方法.
-keep class com.example.xh_service.** { *; }

# 忽略指定的类和方法
-ignorewarnings
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**


# 压缩代码
-optimizationpasses 5
-allowaccessmodification
-dontpreverify
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

# 移除日志输出
#-assumenosideeffects class android.util.Log {
#    public static int d(...);
#    public static int v(...);
#}
