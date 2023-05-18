# chat-circle

一个聊天的平台，网站提供多种类型的聊天室，用户可以自己浏览并选择自己想要进入的聊天室进行多人聊天，也可以在多人聊天中发现志同道合的伙伴，加为好友进行单独聊天。平台提供手机/邮箱注册，个人中心设置信息，自定义头像，昵称等用于展示自己，为聊天室伙伴发现和你更多相似。平台还处于开发阶段，后续功能会不断更新。

# 前后端分离项目

服务为前后端分离项目

- 前端使用vue3+ts+less+element-puls+axios
- 后端使用springboot框架+mybatis-plus+netty+springcloud
- 数据库使用mysql+redis+fastDFS

## hhz-web

* * *

前端服务

该模板应该可以帮助您开始在 Vite 中使用 Vue 3 进行开发。

## [](https://github.com/hhGitt/chat-circle/blob/master/hhz-web/README.md#recommended-ide-setup)推荐的 IDE 设置

[VSCode](https://code.visualstudio.com/) \+ [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar)（并禁用 Vetur）+ [TypeScript Vue 插件（Volar）](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin)。

## [](https://github.com/hhGitt/chat-circle/blob/master/hhz-web/README.md#type-support-for-vue-imports-in-ts)`.vue`TS 中对导入的类型支持

默认情况下，TypeScript 无法处理`.vue`导入的类型信息，因此我们将`tsc`CLI替换`vue-tsc`为进行类型检查。在编辑器中，我们需要[TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin)来让 TypeScript 语言服务识别`.vue`类型。

如果您觉得独立的 TypeScript 插件不够快，Volar 还实现了性能更高的[接管模式。](https://github.com/johnsoncodehk/volar/discussions/471#discussioncomment-1361669)您可以通过以下步骤启用它：

1.  禁用内置的 TypeScript 扩展
    1.  `Extensions: Show Built-in Extensions`从 VSCode 的命令面板运行
    2.  查找`TypeScript and JavaScript Language Features`，右键单击并选择`Disable (Workspace)`
2.  `Developer: Reload Window`通过从命令面板运行重新加载 VSCode 窗口。

## [](https://github.com/hhGitt/chat-circle/blob/master/hhz-web/README.md#customize-configuration)自定义配置

参见[Vite 配置参考](https://vitejs.dev/config/)。

## 如何运行

```
# 安装项目依赖
npm install

# 启动开发模式
npm run dev

# 用于构建项目的生产版本
npm run build
```

## hhz-server

* * *

后端项目，尝试着用分布式架构。推荐IDEA运行

使用swagger提供api文档，访问http://localhost:10010(gateway端口)查看

## 注册中心

使用阿里的Nacos，提供了服务注册、发现和配置管理的功能，详细配置请看[Nacos官网](https://nacos.io/zh-cn/docs/v2/quickstart/quick-start.html),推荐[docker运行](https://www.docker.com/)

注意：在每一个服务的yml配置文件中需要将`nacos.discovery.server-addr`改为自己的nacos服务地址

## feign-api

使用Fegin一个基于Java的声明式HTTP客户端库，提供了一种方便的方式来与RESTful API进行交互，用于服务之间相互请求需要的接口。

## gateway

用于管理和控制对后端服务的访问。在客户端和后端服务之间建立一个统一的入口，实现了路由转发，用户token认证过滤一些非法请求，使用spring-cloud-loadbalancer实现负载均衡

## commons-api

该包统一管理了多个服务中的dto，entity，vo，enums类，自定义了一些异常类，ResultData统一接口返回格式，HttpStatus请求状态码，redisUtils用于操作redis数据库，jwtUtilsl封装加密用户信息，FastDFSUtils用于上传图片文件到自己的文件FastDFS分布式文件存储系统中。

docker一键搭建fastDFS

```
docker run -d --restart=always --privileged=true --net=host --name=fastdfs -e IP=47.95.234.255 -e WEB_PORT=80 -v ${HOME}/fastdfs:/var/local/fdfs registry.cn-beijing.aliyuncs.com/tianzuo/fastdfs
```

yml配置文件中需要将fdfs.tracker-list: 添加自己的fastDFS地址

## user-service

实现登录注册功能，使用阿里短信服务，提供验证码发送，spring-boot-starter-mail提供发送验证码邮件服务，验证码设置有效期保存到redis中，提供用户信息查看接口，好友相关信息

阿里短信服务：需要注册[阿里短信服务](https://dysms.console.aliyun.com/overview) 获取accessKeyId和accessKeySecret

邮箱设置：spring.mail.host(配置smtp服务主机地址),spring.mail.username(发送者邮箱),spring.mail.password(申请到的授权码)需要到对应邮箱去获取

## chat-service

提供聊天室相关信息，netty实现相关NIO长连接实现在线聊天

## SQL

* * *

数据使用的Mysql，项目使用8.0版本