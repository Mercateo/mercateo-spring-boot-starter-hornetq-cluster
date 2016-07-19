# mercateo-spring-boot-starter-hornetq-cluster

Alternative / substitute to (but actually a wrapper of) the original starter ``org.springframework.boot:spring-boot-starter-hornetq`` providing a very easy way to connect to multiple Hornetq nodes, e. g. them being in a fail-over setup.

- creates a ``HornetQConnectionFactory`` by adding Netty ``TransportConfiguration``s for every given Hornetq authority (``host:port``)
- if configured, will use ``spring.hornetq.user`` and ``spring.hornetq.password``to create a  ``UserCredentialsConnectionFactoryAdapter`` wrapping previously created ``ConnectionFactory`` applying the given user credentials to every standard ``createConnection()`` call.
- no JNDI lookups involved

Versions will imply the versions of ``org.springframework.boot:spring-boot-starter-hornetq`` being wrapped.

### Build status

![Build Status](https://travis-ci.org/Mercateo/mercateo-spring-boot-starter-hornetq-cluster.svg)

### Use

Add the folling snippet to your ``pom.xml``:

```xml
<dependency>
    <groupId>com.mercateo</groupId>
    <artifactId>mercateo-spring-boot-starter-hornetq-cluster</artifactId>
    <version>1.3.5.RELEASE</version>
</dependency>
```

### Configure

```
spring.hornetq.authorities=node1:port1,node2:port2,...
spring.hornetq.mode=native
# optional
spring.hornetq.user=
spring.hornetq.password=
```
