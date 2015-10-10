Collects market data from a betting exchange in a form of events, e.g. CREATE\_MARKET,
PLACE\_BET.

At the moment, UK Horse racing win/place markets can be collected only.

More on my betting exchange research at http://blog.danmachine.com/

**Compiling project and running all tests:**

1)Set bfUser and bfPassword environment properties, e.g. on Windows call 'set bfUser=john', on Unix call 'export bfUser=john'

2) call 'mvn clean install' - http://maven.apache.org/

**Compiling and skipping all tests:**

mvn -DskipTests clean install

**Importing project into Eclipse:**

Run mvn eclipse:eclipse - this generates Eclipse .classpath and .project files.

**Maven 2 repository:**
```
...
<dependency>
  <groupId>dk.betex</groupId>
  <artifactId>betex-event-collector</artifactId>
  <version>0.1</version>
</dependency>
...

...
  <repository>
   <id>dk-maven2-repo-releases</id>
   <name>dk-maven2 maven repository (releases)</name>
   <url>http://dk-maven2-repo.googlecode.com/svn/maven-repo/releases</url>
  </repository>

...
```