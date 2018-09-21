
@Copyright by SONG

医院门诊管理系统

医院门诊业务流程:
包括病人的挂号、取号、划价取药，诊疗卡的办理、充值、扣费，
医生的排班、接诊、开具处方、接诊患者统计，
科室、药品、医生以及其他登录账号的信息管理与统计

hospital
  1.需求分析
    1.1 需求分析说明书
  2.设计
    2.1 流程图
    2.2 数据字典
    2.3 mysql.sql
  3.Code



** Day 01 *************************************************
00.微服务.Maven.Spring Boot
01.安装配置 MySQL,建库,建表,初始化数据
   安装设置 IntelliJ IDEA

02.IDEA 中 Spring Initializr工具,快速的构建出一个基础的Spring Boot/Cloud工程

   从 start.spring.io 下载 Maven Project 模板
   MyEclipse 导入 Maven Project

03.pom.xml 中添加支持web的模块(依赖)
04.controller
05.启动 Application
06.Chrome: http://127.0.0.1:8080/hello

07.修改 application.properties,设置 Tomcat 端口 (默认 8080)
08.www.getpostman.com 官网下载,安装 Postman - 测试
09.前后端分离开发,与前端联调

跨域是指 不同域名之间相互访问。跨域，指的是浏览器不能执行其他网站的脚本。它是由浏览器的同源策略造成的，是浏览器对JavaScript施加的安全限制

也就是如果在A网站中，我们希望使用Ajax来获得B网站中的特定内容
如果A网站与B网站不在同一个域中，那么就出现了跨域访问问题。

什么是同一个域？
同一协议，同一ip，同一端口，三同中有一不同就产生了跨域。



** Day 02 *************************************************
01.pom.xml 中添加 mybatis 依赖
    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>1.3.1</version>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>6.0.6</version>
    </dependency>

02.修改 application.properties, 设置数据源
mybatis.type-aliases-package=com.hospital.registration.domain

spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/hospital?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root

03.domain.Admins
04.mapper.AdminsMapper
05.修改 Application 添加 Mapper 扫描
  @MapperScan("com.hospital.registration.mapper")

06.JUnit 测试 mybatis:AdminsTest + 断言

07.service.AdminsService

08.controller.AdminsController | CustomErrorType

09.启动 Application - Postman restful 测试

10.与前端联调
  H-ui + layer

11.配置前端服务器 Live-server:
  11.1 下载 nodejs: nodejs.org
  11.2 安装 nodejs
  11.3 安装前端服务器:npm --registry=https://registry.npm.taobao.org i -g live-server
  11.4 启动前端服务器:live-server



** Day 03 *************************************************
分布式会话:

适用场景:
为了使Web能适应大规模的访问,需要实现应用程序的集群部署
实现集群部署首先要解决session的统一，即需要实现session的共享机制，即分布式会话

分布式Session的实现方式:
基于resin/tomcat web容器本身的session复制机制
基于NFS共享文件系统
基于Cookie进行session共享
基于数据库的Session共享
基于分布式缓存的Session共享，如memcached，Redis，jbosscache
基于ZooKeeper的Session共享



基于Redis缓存的Session共享



REmote DIctionary Server(Redis) 是一个由Salvatore Sanfilippo写的key-value存储系统。

Redis是一个开源的使用ANSI C语言编写、遵守BSD协议、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。

它通常被称为数据结构服务器，因为值（value）可以是 字符串(String), 哈希(Map), 列表(list), 集合(sets) 和 有序集合(sorted sets)等类型。

Redis 与其他 key - value 缓存产品有以下三个特点：
Redis支持数据的持久化，可以将内存中的数据保存在磁盘中，重启的时候可以再次加载进行使用。
Redis不仅仅支持简单的key-value类型的数据，同时还提供list，set，zset，hash等数据结构的存储。
Redis支持数据的备份，即master-slave模式的数据备份

Redis 优势
性能极高 – Redis能读的速度是110000次/s,写的速度是81000次/s 。
丰富的数据类型 – Redis支持二进制案例的 Strings, Lists, Hashes, Sets 及 Ordered Sets 数据类型操作。
原子 – Redis的所有操作都是原子性的，意思就是要么成功执行要么失败完全不执行。单个操作是原子性的。多个操作也支持事务，即原子性，通过MULTI和EXEC指令包起来。
丰富的特性 – Redis还支持 publish/subscribe, 通知, key 过期等等特性。



官网: https://redis.io/
中文: http://www.redis.cn/
Windows x64 下载: https://github.com/MSOpenTech/redis/releases

下载 Redis-x64-xxx.zip压缩包到 C 盘，解压后，将文件夹重新命名为 redis

启动服务器:
打开一个 cmd 窗口 使用cd命令切换目录到 C:\redis 运行 redis-server.exe redis.windows.conf 。
如果想方便的话，可以把 redis 的路径加到系统的环境变量里，这样就省得再输路径了，后面的那个 redis.windows.conf 可以省略，如果省略，会启用默认的。输入之后，会显示如下界面：

启动客户端:
这时候另启一个cmd窗口，原来的不要关闭，不然就无法访问服务端了。
切换到redis目录下运行 redis-cli.exe -h 127.0.0.1 -p 6379
设置键值对 set myKey SpringBoot
取出键值对 get myKey

查看 redis 是否启动？
$ redis-cli
以上命令将打开以下终端：
redis 127.0.0.1:6379>
127.0.0.1 是本机 IP ，6379 是 redis 服务端口。现在我们输入 PING 命令。
redis 127.0.0.1:6379> ping
PONG
以上说明我们已经成功安装了redis


Redis 的操作指南,请参考 Redis.txt

------------------------------------------
基于Spring Session + redis 的实现 HttpSession:

01.修改登录时间 + JUnit
02.引入maven依赖
    <dependency>
      <groupId>org.springframework.session</groupId>
      <artifactId>spring-session-data-redis</artifactId>
    </dependency>

03.配置application.properties
  spring.redis.host=127.0.0.1
  spring.redis.port=6379
  # server.session.timeout=3600

  # spring session使用存储类型
  spring.session.store-type=redis

spirngboot默认就是使用redis方式,如果不想用可以填none

04.在启动类 Application 加入 @EnableRedisHttpSession 注解
05.AdminsController注入 session
06.postman test api:
  http://127.0.0.1:8086/api/usersession


05.查看 session_id
  redis-cli keys *

------------------------------------------
session 跨域:
01.配置Spring CorsConfiguration来解决跨域资源共享问题
  config.CorsConfig

02.解决session跨域共享问题
  index.html | main.html
  # 在ajax的参数xhrFields里,添加 withCredentials: true
  xhrFields: {
      withCredentials: true
  }

  成功返回同样的sessionId



作业:验证码 + 修改密码 + 注销
SecurityImage
SecurityCode
GlobalController.getImageCode()



** Day 04 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - session跨域]
  - 切换至 bin 目录,cmd 执行:redis-server.exe redis.windows.conf
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome

在 Day03 工程的基础上,实现 Spring Boot 事务管理和Web应用的统一异常处理

1.修改 AdminsService 中的 changePassword() 演示事务

统一异常处理
2.新建 controller.GlobalExceptionHandler
3.启动该应用,访问:http://localhost:8080/global_save可以错误提示页面


返回JSON格式
在上述例子中，通过@ControllerAdvice统一定义不同Exception映射到不同错误处理页面。而当要实现RESTful API时，返回的错误是JSON格式的数据，而不是HTML页面，这时候也能轻松支持。

本质上，只需在@ExceptionHandler之后加入@ResponseBody，就能让处理函数return的内容转换为JSON格式。

下面以一个具体示例来实现返回JSON格式的异常处理。

创建统一的JSON返回对象，code：消息类型，message：消息内容，url：请求的url，data：请求返回的数据
public class ErrorInfo<T> {
    public static final Integer OK = 0;
    public static final Integer ERROR = 100;
    private Integer code;
    private String message;
    private String url;
    private T data;
    // 省略getter和setter
}

创建一个自定义异常，用来实验捕获该异常，并返回json
public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }

}
Controller中增加json映射，抛出MyException异常
@Controller
public class HelloController {
    @RequestMapping("/json")
    public String json() throws MyException {
        throw new MyException("发生错误2");
    }
}
为MyException异常创建对应的处理
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception {
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData("Some Data");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }
}
启动应用，访问：http://localhost:8080/json，可以得到如下返回内容：
{
    code: 100，
    data: "Some Data"，
    message: "发生错误2"，
    url: "http://localhost:8080/json"
}
至此，已完成在Spring Boot中创建统一的异常处理，实际实现还是依靠Spring MVC的注解


4.实现动态下拉菜单 main.html



** Day 05 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - session跨域]
  - 切换至 bin 目录,cmd 执行:redis-server.exe redis.windows.conf
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome


使用 AOP 统一处理 Web 请求日志
1.在 pom.xml 中引入 AOP 依赖
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>

2.新建 resources/log4j.properties
3.Web层日志切面类: controller.WebLogAspect
4.Chrome: http://127.0.0.1:8080/,查看日志输出

5.AOP切面的优先级
  @Order(i)
  在切入点前的操作，按order的值由小到大执行
  在切入点后的操作，按order的值由大到小执行



** Day 06 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - session跨域]
  - 切换至 bin 目录,cmd 执行:redis-server.exe redis.windows.conf
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome



restful 统计报表 + 柱状图:今天 昨天 本周 本月 本季度:

表 bookable 中的字段 bdate 存储类型是 DATETIME | TIMESTAMP,查询语句如下:
-- 查询今天的信息:
select * from bookable where to_days(bdate) = to_days(now());

-- 查询昨天的信息:
select * from bookable where to_days(now()) - to_days(bdate) = 1;

-- 查询近 7 天的信息:
select * from bookable where date_sub(curdate(), INTERVAL 7 DAY) <= date(bdate);

-- 查询本周的信息:
select * from bookable where YEARWEEK(date_format(bdate,'%Y-%m-%d')) = YEARWEEK(now());

-- 查询上周的信息:
select * from bookable where YEARWEEK(date_format(bdate,'%Y-%m-%d')) = YEARWEEK(now()) -1;

-- 查询近 30 天的信息:
select * from bookable where date_sub(curdate(), INTERVAL 30 DAY) <= date(bdate);

-- 查询本月的信息:
select * from bookable where date_format(bdate,'%Y%m') = date_format(curdate(),'%Y%m');

-- 查询上一月的信息:
SELECT * FROM bookable WHERE PERIOD_DIFF(date_format(now( ),'%Y%m' ), date_format(bdate, '%Y%m' ))=1;

-- 查询距离当前现在 6 个月的信息:
select * from bookable where bdate between date_sub(now(),interval 6 month) and now();

-- 查询本季度的信息:
select * from bookable where QUARTER(bdate)=QUARTER(now());

-- 查询上季度的信息:
select * from bookable where QUARTER(bdate)=QUARTER(DATE_SUB(now(),interval 1 QUARTER));

-- 查询本年的信息:
select * from bookable where YEAR(bdate) = YEAR(now());

-- 查询去年的信息:
select * from bookable where YEAR(bdate) = YEAR(date_sub(now(),interval 1 year));

str_to_date('2018-01-17 17:20:30', '%Y-%m-%d %H:%i:%s')

我的桌面:
1.修改 main.html: 我的桌面 url
2.修改 static/h-ui.admin/css/style.css: 新增.threed
3.新增 ticket/welcome.html
4.BooksConroller - BooksService - BooksMapper



** Day 07 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - session跨域]
  - 切换至 bin 目录,cmd 执行:redis-server.exe redis.windows.conf
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome

问题:
未登录可以访问 http://127.0.0.1:8080/ticket/welcome.html

在编写Web应用时，经常需要对页面做一些安全控制，比如：对于没有访问权限的用户需要转到登录表单页面。要实现访问控制的方法多种多样，可以通过Aop、拦截器实现，也可以通过框架实现（如：Apache Shiro、Spring Security）

Spring Security 是一个专门针对基于Spring的项目的安全框架,主要是利用了 AOP(Spring基础配置)来实现的。



JWT和Spring Security保护REST API

通常情况下，把API直接暴露出去是风险很大的，不说别的，直接被机器攻击就喝一壶的。那么一般来说，对API要划分出一定的权限级别，然后做一个用户的鉴权，依据鉴权结果给予用户开放对应的API。目前，比较主流的方案有几种:

用户名和密码鉴权，使用Session保存用户鉴权结果。
使用OAuth进行鉴权（其实OAuth也是一种基于Token的鉴权，只是没有规定Token的生成方式）
自行采用Token进行鉴权
第一种就不介绍了，由于依赖Session来维护状态，也不太适合移动时代，新的项目就不要采用了。第二种OAuth的方案和JWT都是基于Token的，但OAuth其实对于不做开放平台的公司有些过于复杂。我们主要介绍第三种：JWT。

什么是JWT？
JWT是 Json Web Token 的缩写。它是基于 RFC 7519 标准定义的一种可以安全传输的 小巧 和 自包含 的JSON对象。由于数据是使用数字签名的，所以是可信任的和安全的。JWT可以使用HMAC算法对secret进行加密或者使用RSA的公钥私钥对来进行签名。

JWT的工作流程
下面是一个JWT的工作流程图。模拟一下实际的流程是这样的（假设受保护的API在/protected中）

1.用户导航到登录页，输入用户名、密码，进行登录
2.服务器验证登录鉴权，如果用户合法，根据用户的信息和服务器的规则生成JWT Token
3.服务器将该token以json形式返回（不一定要json形式，这里说的是一种常见的做法）
4.用户得到token，存在localStorage、cookie或其它数据存储形式中。
5.以后用户请求/protected中的API时，在请求的header中加入 Authorization: Bearer xxxx(token)。此处注意token之前有一个7字符长度的 Bearer
6.服务器端对此token进行检验，如果合法就解析其中内容，根据其拥有的权限和自己的业务逻辑给出对应的响应结果。
7.用户取得结果

Spring Security是一个基于Spring的通用安全框架

如何利用Spring Security和JWT一起来完成API保护

简单的背景知识
如果你的系统有用户的概念的话，一般来说，你应该有一个用户表，最简单的用户表，应该有三列：Id，Username和Password，类似下表这种

ID	USERNAME	PASSWORD
10	wang	abcdefg
而且不是所有用户都是一种角色，比如网站管理员、供应商、财务等等，这些角色和网站的直接用户需要的权限可能是不一样的。那么我们就需要一个角色表：

ID	ROLE
10	USER
20	ADMIN
当然我们还需要一个可以将用户和角色关联起来建立映射关系的表。

USER_ID	ROLE_ID
10	10
20	20



01.pom.xml中新增依赖 spring-boot-starter-security
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
02.配置application.properties
  server.context-path=
  spring.jackson.serialization.indent_output=true
  logging.level.org.springframework.security=info

03.新增 AuthorityName + Authority + 修改 Admins

04.安全服务的用户: JwtUser + JwtUserFactory + JwtUserDetailsServiceImpl + JwtAuthenticationResponse
  需要实现UserDetails接口,用户实体即为Spring Security所使用的用户

  配置 application.properties 支持 mybatis 映射文件 xml
  mybatis.mapper-locations=classpath:mybatis/mapper/*.xml

05.让Spring控制的安全配置类:WebSecurityConfig

06.在 XxxController 加一个修饰符 @PreAuthorize("hasRole('ADMIN')") 表示这个资源只能被拥有 ADMIN 角色的用户访问
  /**
   * 在 @PreAuthorize 中可以利用内建的 SPEL 表达式：比如 'hasRole()' 来决定哪些用户有权访问。
   * 需注意的一点是 hasRole 表达式认为每个角色名字前都有一个前缀 'ROLE_'。所以这里的 'ADMIN' 其实在
   * 数据库中存储的是 'ROLE_ADMIN' 。这个 @PreAuthorize 可以修饰Controller也可修饰Controller中的方法。
   **/

07.除了 /api/users, /api/imagecode, /api/global_json 外
  访问抛异常: org.springframework.security.access.AccessDeniedException: Access is denied


集成 JWT 和 Spring Security
07.pom.xml中新增依赖 jjwt 依赖
  <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
  <dependency>
      <groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt</artifactId>
      <version>0.9.0</version>
  </dependency>

  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-mobile</artifactId>
  </dependency>

  <!-- https://mvnrepository.com/artifact/com.google.code.findbugs/findbugs -->
  <dependency>
      <groupId>com.google.code.findbugs</groupId>
      <artifactId>findbugs</artifactId>
      <version>3.0.1</version>
  </dependency>


08.application.properties 配置 JWT

09.新建一个filter: JwtAuthenticationTokenFilter
  JwtAuthenticationEntryPoint + JwtAuthenticationRequest + JwtTokenUtil

10.在 WebSecurityConfig 中注入这个filter, 并且配置到 HttpSecurity 中

完成鉴权(登录),注册和更新token的功能
11.AuthenticationRestController + MethodProtectedRestController + UserRestController

12.更新初始化密码:AdminsTest.getPassword()
  任何应用考虑到安全,绝不能明文的方式保存密码。
  密码应该通过哈希算法进行加密。
  有很多标准的算法比如SHA或者MD5,结合salt(盐)是一个不错的选择。
  Spring Security 提供了BCryptPasswordEncoder类,
  实现Spring的PasswordEncoder接口使用BCrypt强哈希方法来加密密码。

  BCrypt强哈希方法:每次加密的结果都不一样。

  postmain test:http://127.0.0.1:8086/api/auth

13.前端:
  测试jwt: http://127.0.0.1:8080/jwt/

  重构代码: 登录 + 注销 + 修改密码 + 图表
  新增js: jwt-decode.min.js
  修改登录: index.html + hospital.js
  修改 CorsConfig: 注释 跨域session共享; 新增 addAllowedOrigin()
  修改注销: main.html



** Day 08 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - 二级缓存]
  - 切换至 bin 目录,cmd 执行:redis-server.exe redis.windows.conf
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome



tips:
 pom.xml中 注释 spring-session-data-redis
 Application 中注释 @EnableRedisHttpSession
 application.properties 中注释 spring.session.store-type=redis



问题:
1.图表查询 N 次
2.高并发.txt


将Redis作为二级缓存
1.pom.xml中增加redis的依赖
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
  </dependency>

2.application.properties中增加redis配置
  Spring Boot会在侦测到存在Redis的依赖并且Redis的配置是可用的情况下，使用RedisCacheManager初始化CacheManager

  spring.redis.pool.max-idle=8
  spring.redis.pool.min-idle=0
  spring.redis.pool.max-active=8
  spring.redis.pool.max-wait=-1

  logging.level.com.hospital.registration.mapper=debug

  采用yaml作为配置文件的格式。xml显得冗长，properties没有层级结构，yaml刚好弥补了这两者的缺点。
  这也是Spring Boot默认就支持yaml格式的原因

3.util:ApplicationContextHolder + RedisCache
4.映射器接口: @CacheNamespace(implementation = com.hospital.registration.util.RedisCache.class)
  映射文件 : <cache type="com.hospital.registration.util.RedisCache"/>
5.在Spring Boot主类 Application 中增加@EnableCaching注解开启缓存功能
6.JUnit

tips: 修改密码后需等缓存失效超时后,再重新登录



Mybatis的二级缓存原理:Mybatis的二级缓存可以自动地对数据库的查询做缓存，并且可以在更新数据时同时自动地更新缓存。

实现Mybatis的二级缓存很简单，只需要新建一个类实现org.apache.ibatis.cache.Cache接口即可。
该接口共有以下五个方法：

String getId()：mybatis缓存操作对象的标识符。一个mapper对应一个mybatis的缓存操作对象。
void putObject(Object key, Object value)：将查询结果塞入缓存。
Object getObject(Object key)：从缓存中获取被缓存的查询结果。
Object removeObject(Object key)：从缓存中删除对应的key、value。只有在回滚时触发。一般我们也可以不用实现，具体使用方式请参考：org.apache.ibatis.cache.decorators.TransactionalCache。
void clear()：发生更新时，清除缓存。
int getSize()：可选实现。返回缓存的数量。
ReadWriteLock getReadWriteLock()：可选实现。用于实现原子性的缓存操作



** Day 09 *************************************************
背景
在分布式系统中，有多个web app，这些web app可能分别部署在不同的物理服务器上，并且有各自的日志输出。当生产问题来临时，很多时候都需要去各个日志文件中查找可能的异常，相当耗费人力。日志存储多以文本文件形式存在，当有需求需要对日志进行分析挖掘时，这个处理起来也是诸多不便，而且效率低下。

为了方便对这些日志进行统一管理和分析，可以将日志统一输出到指定的数据库系统中，再由日志分析系统去管理。由于这里是mongodb的篇章，所以主观上以mongodb来做日志数据存储；客观上，一是因为它轻便、简单，与log4j整合方便，对系统的侵入性低。二是因为它与大型的关系型数据库相比有很多优势，比如查询快速、bson存储结构利于扩展、免费等。


NoSQL & MongoDB



NoSQL:Not Only SQL (不只是SQL)

数据存储方案:
应用程序存储和检索数据有以下三种方案
文件系统直接存储
关系型数据库
NoSQL 数据库（是对非关系型数据库的统称）

最重要的差别是 NoSQL 不使用 SQL 作为查询语言。
数据存储可以不需要固定的表格模式（行和列），避免使用SQL的JOIN操作，有更高的性能及水平可扩展性的特征。
NoSQL 在 ACID（原子性、一致性、隔离性、持久性） 的支持方面没有传统关系型数据完整。

文档数据库   MongoDB / CouchDB
键／值数据库 redis   / Cassandra
列数据库     Hbase   / Cassandra
图数据库     Neo4J



MongoDB 基于文档存储模型，数据对象以BSON（二进制 JSON）格式被存储在集合的文档中，而不是关系数据库的行和列中。

集合
使用集合将数据编组，是一组用途相同的文档，类似表的概念，但集合不受模式的限制，在其中的文档格式可以不同。

文档
文档表示单个实体数据，类似一条记录（行）；与行的差别：行的数据是扁平的，每一列只有一个值，而文档中可以包含子文档，提供的数据模型与应用程序的会更加一致。


一个文档 Demo:
{
  name:'X Fimaly'
  address: ['NY','LA']
  person: [{'name':'Jack'},{'name':'Rose'}]
}



安装 MongoDB
官网:https://www.mongodb.com/

下载社区版:mongodb-win32-x86_64-3.4.9-signed.msi

设置环境变量:
把安装目录 mongodb/bin 添加到系统 path 中
...;D:\Program Files\MongoDB\Server\3.4\bin

cmd:
  mongo --help
  mongo --version

  tips:出错 缺少 api-ms-win-crt-runtime-xxx.dll 则安装 vc_redist.x64.exe

创建一个存放数据的目录如：D:/Oracle/MongoDB/data
从命令行执行 mongod --dbpath D:/Oracle/MongoDB/data 启动服务器 [不能关闭]
从命令行执行 mongo 启动交互窗口（mongoDB shell）



MongoDB 使用:
数据库:
启动 mongo shell  [相当于 mongo 客户端]

显示数据库
>show dbs

切换数据库（若不存在则创建数据库）
>use employee [相当于 mongo 的一个数据库]

显示当前使用的数据库
>db

删除当前数据库
  db.dropDatabase()



Collection(集合):
显示所有集合
>show collections

创建一个集合
db.createCollection('emps') [相当于一张表 emps]

删除一个集合
  db.emps.drop()



MongoDB CRUD:
插入一个文档
db.collection.insertOne()
db.emps.insertOne({name:'SMITH',age:27})

插入多个文档
db.collection.insertMany()
db.emps.insertMany([{name:'SCOTT',age:26},{name:'KING',age:24,phone:['155','186']}])

查询（检索文档）
db.emps.find()

name 是 KING
db.emps.find({name:'KING'})

age 大于 25
db.emps.find({age:{$gt:25}})

age 小于 25 且 name 是 KING
db.emps.find({age:{$lt:25},name:'KING'})

电话号码为 186
db.emps.find({phone:'186'})



更新一个文档
db.collection.updateOne()
更新多个文档
db.collection.updateMany()

db.emps.updateOne(
	{name:'SCOTT'},	// 更新的条件
	{$set:{age:19}}	// 新的数据
)

// update 时新增字段
db.emps.updateOne(
	{name:'SMITH'},
  {$set:{phoneabc:'186'}}
)



删除一个文档
db.collection.deleteOne()
删除多个文档
db.collection.deleteMany()

db.emps.deleteOne({name:'SCOTT'})
db.emps.deleteMany({age:{$lt:30}})


--------------------------------------------------------
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - 二级缓存]
  - 切换至 bin 目录,cmd 执行: redis-server.exe redis.windows.conf

  start MongoDB [日志服务器]
  - cmd 执行: mongod --dbpath D:/Oracle/MongoDB/data
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome




使用logback实现http请求日志导入mongodb

spring boot自带logback作为其日志新系统，但是在实际工作中，常常需要对日志进行管理或分析，
如果只是单纯的将日志导入文本文件，则在查询时操作过于繁琐，
如果将其导入mysql等关系型数据库进行存储，又太影响系统性能，同时由于Mysql其结构化的信息存储结构，导致在存储时不够灵活。
因此，在此考虑将springboot系统中产出的日志(logback) 存入mongodb中

1.pom.xml 引入依赖
  https://mvnrepository.com 搜索最新的 jar 包
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
    <version>1.5.8.RELEASE</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-core -->
  <dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-core</artifactId>
    <version>1.2.3</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
  <dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.3</version>
  </dependency>

  <!-- AOP 依赖 -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
    <version>1.5.7.RELEASE</version>
  </dependency>

2.添加实体类: logback.MyLog.java
3.添加数据访问接口: LogRepository.java
4.Appender 类: MongoDBAppender.java

5.切面中使用mongodb logger:
  logger取名为MONGODB
  通过getBasicDBObject函数从HttpServletRequest和JoinPoint对象中获取请求信息，并组装成BasicDBObject
  getHeadersInfo函数从HttpServletRequest中获取header信息
  通过logger.info()，输出BasicDBObject对象的信息到mongodb

6.resources/logback.xml - 更新 <appender name="MONGODB" />
            application.yml 配置spring boot的文件配置标签

            spring:
              data:
                mongodb:
                  uri: mongodb://127.0.0.1:27017/logs

7.controller

8.start Application
  Chrome: http://127.0.0.1:8080/mongo | greeting

9.cmd - mongo 进入客户端
  >use logs
  >db.myLog.find()
  >db.myLog.find({_class:"com.hospital.registration.logback.MyLog"})



** Day 10 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - 二级缓存]
  - 切换至 bin 目录,cmd 执行: redis-server.exe redis.windows.conf

  start MongoDB [日志服务器]
  - cmd 执行: mongod --dbpath D:/Oracle/MongoDB/data
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome

需求:
  完成 账号管理 模块
  管理员列表 | 挂号收银员列表 | 划价发药员列表

                    本次课需完成模块
诊疗卡业务
  办卡充值
挂号业务
  取预约号
  现场挂号
门诊业务
  就医诊断
  就诊统计
药房业务
  划价发药
科室管理
  科室列表
医生管理
  医生列表
排班管理
  医生坐诊排班
药品管理
  药品列表
  药品类型列表
  库存统计
  销售统计
账号管理               *
  管理员列表           *
  挂号收银员列表        *
  划价发药员列表        *



发布:
  修改 webfrontend/js/hospital.js 中的 IP
  关闭防火墙


** Day 11 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - 二级缓存]
  - 切换至 bin 目录,cmd 执行: redis-server.exe redis.windows.conf

  start MongoDB [日志服务器]
  - cmd 执行: mongod --dbpath D:/Oracle/MongoDB/data
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome

需求:
  完成 科室管理 | 医生管理 模块
  科室列表 | 医生列表

                    本次课需完成模块
诊疗卡业务
  办卡充值
挂号业务
  取预约号
  现场挂号
门诊业务
  就医诊断
  就诊统计
药房业务
  划价发药
科室管理
  科室列表             **
医生管理
  医生列表             **
排班管理
  医生坐诊排班
药品管理
  药品列表
  药品类型列表
  库存统计
  销售统计
账号管理               *
  管理员列表           *
  挂号收银员列表        *
  划价发药员列表        *



** Day 12 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - 二级缓存]
  - 切换至 bin 目录,cmd 执行: redis-server.exe redis.windows.conf

  start MongoDB [日志服务器]
  - cmd 执行: mongod --dbpath D:/Oracle/MongoDB/data
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome


需求:
  完成 排班管理 模块 (比较复杂)
  医生坐诊排班

                    本次课需完成模块
诊疗卡业务
  办卡充值
挂号业务
  取预约号
  现场挂号
门诊业务
  就医诊断
  就诊统计
药房业务
  划价发药
科室管理
  科室列表             **
医生管理
  医生列表             **
排班管理
  医生坐诊排班          ***
药品管理
  药品列表
  药品类型列表
  库存统计
  销售统计
账号管理               *
  管理员列表           *
  挂号收银员列表        *
  划价发药员列表        *



** Day 13 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - 二级缓存]
  - 切换至 bin 目录,cmd 执行: redis-server.exe redis.windows.conf

  start MongoDB [日志服务器]
  - cmd 执行: mongod --dbpath D:/Oracle/MongoDB/data
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome

需求:
  完成 药品管理 | 诊疗卡业务 模块
  药品类型列表 | 药品列表 | 办卡充值

                    本次课需完成模块
诊疗卡业务
  办卡充值           ****
挂号业务
  取预约号
  现场挂号
门诊业务
  就医诊断
  就诊统计
药房业务
  划价发药
科室管理
  科室列表             **
医生管理
  医生列表             **
排班管理
  医生坐诊排班          ***
药品管理
  药品列表             ****
  药品类型列表          ****
  库存统计
  销售统计
账号管理               *
  管理员列表           *
  挂号收银员列表        *
  划价发药员列表        *


** Day 14 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - 二级缓存]
  - 切换至 bin 目录,cmd 执行: redis-server.exe redis.windows.conf

  start MongoDB [日志服务器]
  - cmd 执行: mongod --dbpath D:/Oracle/MongoDB/data
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome


需求:
  完成 挂号业务 模块
  取预约号 | 现场挂号

                    本次课需完成模块
诊疗卡业务
  办卡充值           ****
挂号业务
  取预约号           *****
  现场挂号           *****
门诊业务
  就医诊断
  就诊统计
药房业务
  划价发药
科室管理
  科室列表             **
医生管理
  医生列表             **
排班管理
  医生坐诊排班          ***
药品管理
  药品列表             ****
  药品类型列表          ****
  库存统计
  销售统计
账号管理               *
  管理员列表           *
  挂号收银员列表        *
  划价发药员列表        *


** Day 15 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - 二级缓存]
  - 切换至 bin 目录,cmd 执行: redis-server.exe redis.windows.conf

  start MongoDB [日志服务器]
  - cmd 执行: mongod --dbpath D:/Oracle/MongoDB/data
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome

需求:
  完成 门诊业务 模块
  就医诊断

                    本次课需完成模块
诊疗卡业务
  办卡充值           ****
挂号业务
  取预约号           *****
  现场挂号           *****
门诊业务
  就医诊断           ******
  就诊统计
药房业务
  划价发药
科室管理
  科室列表             **
医生管理
  医生列表             **
排班管理
  医生坐诊排班          ***
药品管理
  药品列表             ****
  药品类型列表          ****
  库存统计
  销售统计
账号管理               *
  管理员列表           *
  挂号收银员列表        *
  划价发药员列表        *


** Day 16 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - 二级缓存]
  - 切换至 bin 目录,cmd 执行: redis-server.exe redis.windows.conf

  start MongoDB [日志服务器]
  - cmd 执行: mongod --dbpath D:/Oracle/MongoDB/data
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome

需求:
  完成 门诊业务 | 药房业务 模块
  就诊统计 | 划价发药

                    本次课需完成模块
诊疗卡业务
  办卡充值           ****
挂号业务
  取预约号           *****
  现场挂号           *****
门诊业务
  就医诊断           ******
  就诊统计           *******
药房业务
  划价发药           *******
科室管理
  科室列表             **
医生管理
  医生列表             **
排班管理
  医生坐诊排班          ***
药品管理
  药品列表             ****
  药品类型列表          ****
  库存统计
  销售统计
账号管理               *
  管理员列表           *
  挂号收银员列表        *
  划价发药员列表        *


** Day 17 *************************************************
准备:
1.start mysql [数据库服务器]
2.start redis [redis服务器 - 二级缓存]
  - 切换至 bin 目录,cmd 执行: redis-server.exe redis.windows.conf

  start MongoDB [日志服务器]
  - cmd 执行: mongod --dbpath D:/Oracle/MongoDB/data
3.start idea  [云端/后端服务器]
  - run Application
4.start live-server [前端服务器]
5.Chrome

需求:
  完成 药品管理 模块
  库存统计 | 销售统计

                    本次课需完成模块
诊疗卡业务
  办卡充值           ****
挂号业务
  取预约号           *****
  现场挂号           *****
门诊业务
  就医诊断           ******
  就诊统计           *******
药房业务
  划价发药           *******
科室管理
  科室列表             **
医生管理
  医生列表             **
排班管理
  医生坐诊排班          ***
药品管理
  药品列表             ****
  药品类型列表          ****
  库存统计             ********
  销售统计             ********
账号管理               *
  管理员列表           *
  挂号收银员列表        *
  划价发药员列表        *