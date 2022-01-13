# ocr
Tess4j文字识别

macos 部署
https://www.cnblogs.com/jxd283465/p/15783187.html

pom
````
     <!-- macos -->
        <dependency>
            <groupId>net.sourceforge.tess4j</groupId>
            <artifactId>tess4j</artifactId>
            <version>4.5.1</version>
            <scope>system</scope>
            <systemPath>${basedir}/src/main/resources/jar/tess4j-4.5.1.jar</systemPath>
        </dependency>
````

linux 部署
https://www.cnblogs.com/jxd283465/p/15796555.html

pom
````
        <!-- linux -->
        <dependency>
            <groupId>net.sourceforge.tess4j</groupId>
            <artifactId>tess4j</artifactId>
            <version>5.0.0</version>
        </dependency>
````
