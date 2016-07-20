# mercateo-spring-boot-starter-hornetq-cluster

Alternative to (actually wrapper of) the original starter ``org.springframework.boot:spring-boot-starter-hornetq`` providing a very easy way to connect to multiple Hornetq nodes, e. g. them being in a fail-over setup.

- Creates a ``HornetQConnectionFactory`` by adding Netty ``TransportConfiguration``s for every given Hornetq authority (``host:port``).
- If configured, will use ``spring.hornetq.user`` and ``spring.hornetq.password``to create a  ``UserCredentialsConnectionFactoryAdapter`` applying the given user credentials to every standard ``createConnection()`` call.
- No JNDI lookups involved.

Version will imply the version of ``org.springframework.boot:spring-boot-starter-hornetq`` being wrapped.

### Build status

![Build Status](https://travis-ci.org/Mercateo/mercateo-spring-boot-starter-hornetq-cluster.svg)

### Use

Include the folling snippet in your project's ``pom.xml``:

```xml
<dependency>
    <groupId>com.mercateo</groupId>
    <artifactId>mercateo-spring-boot-starter-hornetq-cluster</artifactId>
    <version>1.3.5.RELEASE</version>
</dependency>
```

### Configure

Be aware that these ports must be Netty ports (default 5445), not JNDI (default 1099).

```
spring.hornetq.authorities=node1:port1,node2:port2,...
spring.hornetq.mode=native
# optional
spring.hornetq.user=
spring.hornetq.password=
```
