#!/bin/sh
set -e
WORK_DIR=$(cd "$(dirname $0)" && pwd)
cd "${WORK_DIR}"
# 1、打包
version=$(mvn -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive exec:exec -q)
artifactId=$(mvn -Dexec.executable='echo' -Dexec.args='${project.artifactId}' --non-recursive exec:exec -q)
mvn clean package -DskipTests

# 2、下载运维脚本 如果把运维脚本放在server/assembly/bin，则无需远程拉取运维脚本
cd target
wget https://codeload.github.com/seezoon/seezoon-standard/zip/refs/heads/master -O seezoon-standard-master.zip
unzip -o seezoon-standard-master.zip "seezoon-standard-master/tools/maintain_script/*"
mkdir -p ${artifactId}/bin && \cp -R seezoon-standard-master/tools/maintain_script/* ${artifactId}/bin
chmod -R +x ${artifactId}/bin
rm -rf seezoon-standard-master*

# 3、打成压缩包 参数p是保留权限，压缩解压都可以用
tar -pzcvf ${artifactId}.tar.gz ${artifactId}

# 4、制作镜像 & 推送 需要提前有docker仓库(group)
cd ${WORK_DIR}
group=734839030
docker build --build-arg MODULE_NAME=${artifactId} -t "${group}/${artifactId}":"${version}" .
# 删除被覆盖的，这里需要先停止相关容器，正常镜像机器不会启动容器
#docker images | grep '<none>.*<none>' | awk '{print $3 }' | xargs docker rmi
# docker push "${group}/${artifactId}":"${version}"

