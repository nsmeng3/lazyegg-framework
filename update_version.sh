#!/bin/bash

MVN_EXEC=./mvnw        # mvnw 文件路径
MVN_PARAMS='versions:set -DnewVersion='   # Maven 版本管理插件参数

# 获取当前项目版本号
CURRENT_VERSION=$(${MVN_EXEC} help:evaluate -Dexpression=project.version -q -DforceStdout)

# 打印当前版本号
echo "Current version is ${CURRENT_VERSION}"

# 获取用户输入的新版本号
read -p "Enter the new version number (only digits, letters, periods, and dashes are allowed): " NEW_VERSION

# 验证新版本号的格式是否正确
if [[ ! ${NEW_VERSION} =~ ^[[:alnum:].-]+$ ]]; then
    echo "Invalid version number! Only digits, letters, periods, and dashes are allowed."
    exit 1
fi

# 使用 Maven Wrapper 命令更新版本号
${MVN_EXEC} ${MVN_PARAMS}${NEW_VERSION}

# 提交修改
${MVN_EXEC} versions:commit

# 打印新版本号
NEW_VERSION=$(${MVN_EXEC} help:evaluate -Dexpression=project.version -q -DforceStdout)
echo "Updated version to ${NEW_VERSION}"
