# mercateo-spring-boot-starter-hornetq-cluster

![Build Status](https://travis-ci.org/Mercateo/mercateo-spring-boot-starter-hornetq-cluster.svg)


Alternative to (actually wrapper of) the original starter ``org.springframework.boot:spring-boot-starter-hornetq`` providing a very easy way to connect to multiple Hornetq nodes, e. g. them being in a fail-over setup.

- Creates a ``HornetQConnectionFactory`` by adding Netty ``TransportConfiguration``s for every given Hornetq authority (``host:port``).
- If configured, will use ``spring.hornetq.user`` and ``spring.hornetq.password``to create a  ``UserCredentialsConnectionFactoryAdapter`` applying the given user credentials to every standard ``createConnection()`` call.
- No JNDI lookups involved.
- Optional property ``spring.hornetq.windowSize`` can be used to set the size of the message buffer for consumer flow control. Value must be -1 (to disable flow control), 0 (to not buffer any messages) or greater than 0 (to set the maximum size of the buffer in Bytes).

### Use

Include the following snippet in your project's ``pom.xml``:

```xml
<dependency>
    <groupId>com.mercateo</groupId>
    <artifactId>mercateo-spring-boot-starter-hornetq-cluster</artifactId>
    <version>1.0.1</version>
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
spring.hornetq.windowSize=
```
