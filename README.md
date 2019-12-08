- 基于spring的学生管理案例
- 项目分两个部分，前端与后台是独立的两个项目
- 前端存放在spring_student_client目录，后台存放在spring_student_web目录
- 前端项目下载完成之后，搭建好nodejs环境以及安装cnpm，全局安装webpack以及webpack-cli后使用cnpm install安装第三方依赖模块。
- 最后使用 npm run dev 运行vue项目，也可以使用webpack命令对代码进行压缩打包

- 后台项目下载完成后，首先idea中集成maven，然后把项目做为一个maven工程引入idea中。
- 提前搭建数据库环境，等工程依赖下载完毕就可以使用mvn tomcat7:run 运行了。
- 等真正部署时只需要对maven工程运行mvn package命令，然后将target目录下的war包复制到tomcat的webapps目录,通过tomcat启动web服务器即可。

