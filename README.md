# mercateo-spring-boot-starter-hornetq-cluster

Alternative / substitute to (but actually a wrapper of) the original starter ``org.springframework.boot:spring-boot-starter-hornetq`` providing a very easy way to connect to multiple Hornetq nodes, e. g. them being in a fail-over setup.

Versions will imply the versions of ``org.springframework.boot:spring-boot-starter-hornetq`` being wrapped.

### Build status

![Build Status](https://travis-ci.org/Mercateo/mercateo-spring-boot-starter-hornetq-cluster.svg)

### What it does

TODO

### Use it

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
