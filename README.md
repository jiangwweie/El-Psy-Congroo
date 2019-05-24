> ## すべてはシュタインズゲートの选択だ

#### *<u>2019.4.19</u>*

1. ##### 添加GitHub Oauth2.0简单授权流程

------

#### *<u>2019.4.20</u>*

1. ##### springmvc在controller接收参数一些注解的使用回顾

2. ##### 增加公用类

3. ##### springboot增加redis支持,并以自定义注解的方式提供redis缓存

------

#### <u>2019.5.22*</u>

1. ##### springboot集成pac4j，shiro，jwt，cas实现单点登录功能。

   1. 提供JWT接口供移动端单点登录授权
   2. cookie机制供web项目使用
2. ####  认证流程详解

   1. 浏览器**，**cas客户端**，处理业务逻辑(简称客户端)，**cas服务端**，负责登录验证，简称服务端；

   **TGT**:  cas服务端生成的每个用户唯一的ticket，TGT对象的ID就是cookie的值。(value)

   **TGC**:  cas服务端根据TGT生成的每个用户的cookie,其value是TGT。----------------(key)

   **ST**:    cas服务端根据每个应用，每个用户生成的一个ticket,验证一次就销毁

2. 浏览器发出请求：http://client.cas.com/user/1

3. 客户端收到请求，shiro拦截，发现session中没有保存用户信息，**重定向**到服务端

   https://server.cas.com/cas/login?service=http://client.cas.com/callback

4. 用户在浏览器填写用户名密码，发出登录请求：post：https://server.cas.com/cas/login?service=client.cas.com/callback

5. 服务端验证成功后，**重定向**shiro配置的回调路径：http://client.cas.com/callback?ticket=akvjsacxexample

   1. cas server端验证后，生成TGC(TGT的key)和TGT(关联用户信息)，并将TGC写入cookie中，同时生成了ST，将ticket返回在uri中。

6. shiro的callback收到浏览器的重定向请求，**去cas server验证ST（路径中的ticket）**，如果通过则返回用户的信息（**CAS Assertion**），然后客户端**重定向**回用户的最初访问路径**http://client.cas.com/user/1**。且将JESSIONID写入cookie，即为浏览器与客户端的sessionid

7. 此时浏览器存有CAS server的cookie(TGC)，即用户凭证，还有与客户端产生的sessionid。

8. 此时一次单点登录完毕，用户第二次访问客户端的资源，会携带Jessionid，因客户端session已经记录了用户的登录信息（**CAS Assertion**），不必再去访问CAS server了。

9. 如果浏览器此时再去访问另外一个客户端B，客户端b与浏览器无session，将浏览器**重定向**到CAS server，浏览器因存储了cas server的cookie，因此携带cookie（TGC）访问cas server的登录页，cas server 认为用户已经登录，直接验证通过无需再次登录，所以直接生成ST，并将浏览器**重定向**回客户端B，同6，7步，客户端验证ST，重定向到最初访问路径
