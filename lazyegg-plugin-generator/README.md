# 代码生成插件

## 增加代码模板

1. origin中增加原始代码文件
2. 执行测试方法
   `io.lazyegg.boot.plugins.generator.app.excutor.VmTemplateGenCmdExeTest#testExecute`
3. mvn clean install

## 指定模板层级

- 在[LayerType.java](src%2Fmain%2Fjava%2Fio%2Flazyegg%2Fboot%2Fplugins%2Fgenerator%2Fdomain%2FLayerType.java)
  代码中增加枚举值。如：要将`DbService.java.vm` 文件放在`infrastructure层`,则需要将`DbService.java`
  追加到Infrastructure枚举常量的字符串数组中
