package io.lazyegg.boot.plugins.generator.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.File;

/**
 * TemplateInfo
 * vm模板文件信息
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Data
@RequiredArgsConstructor
@Builder
public class TemplateFileInfo {

    @NonNull
    private File templateFile;

    /**
     * adapter app client  ...
     */


//    public String packagePath() {
//        String fileName = file.getName().replaceAll("\\.vm", "");
//        String[] a = {variable.getPackagePrefix(), variable.getModuleName(), layerPackageName, variable.getEntityClassName() + fileName};
//        return String.join("." , a);
//    }

//    public String getFilePath() {
//        String[] a = {variable.getPackagePrefix(), variable.getModuleName(), layerPackageName};
//        return String.join(File.separator , a).replaceAll("\\.", File.separator);
//    }

//    public String getTemplateResourcePath() {
//        return String.join(File.separator, "template" , file.getName());
//    }

//    /**
//     * 包路径转 文件路径
//     *
//     * @return
//     */
//    public String convertFilePath() {
//        String packagePath = packagePath();
//        int i = packagePath.lastIndexOf(".java" );
//        String prefix = packagePath.substring(0, i);
//        String filePathPerfix = prefix.replaceAll("\\." , File.separator);
//        String suffix = packagePath.substring(i);
//        return String.format("%s%s" , filePathPerfix, suffix);
//    }


}

