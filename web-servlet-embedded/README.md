```text
我在运行main()方法时，出现

错误: 无法初始化主类 Main

原因: java.lang.NoClassDefFoundError: org/apache/catalina/WebResourceRoot

麻烦大家看看什么原因？
```

```text
热心读者：

1，问题产生的原因：廖大佬用的eclipse，我们用的IDEA，我们在IDEA中，maven配置<scope>provided</scope>，就告诉了IDEA程序会在运行的时候提供这个依赖，但是实际上却并没有提供这个依赖。

2，解决方法：

去掉<scope>provided</scope>，改<scope>complie</scope>，然后reimport就可以了。
```

```text
作者：

那是idea的问题，如果你把provided改成compile，生成的war包会很大，因为把tomcat打包进去了

解决方案
打开idea的Run/Debug Configurations:

选择Application - Main

右侧Configuration：Use classpath of module

钩上☑︎Include dependencies with "Provided" scope
```
