# Springfox To Springdoc

With the start of the usage of swagger 3 springdoc started to take place of springfox. Together with this change, moving projects 
from springfox to springdoc become a neccessity for most of the projects that used springfox previously. This moving process can 
be a little tricky for the developers since springdoc annotations and their parameters have lots of differences from springfox. 
Although changing these annotions manually according to the documentations is a valid way to complete this process, for the 
large projects  with large amount of classes and lots of different annotations usage, this process can take so much time and effort. 
Since the usage of most of the annotations and its paramaters also changed, automatically replacing all annotations is also 
not a healty way since this type of changes requires lots of refactoring and could easily create compilation problems inside
the code. The exact aim of this project is automatically moving project from springfox to springdoc by changing all annotations,
annotation parameters and import statements without changing code. 

springfox-to-springdoc has a very simple and gradual working principle. It parses java codes by using 
[JavaParser](https://github.com/javaparser/javaparser) then traverse AST for migration.
 
## Annotations Coverage

We are supported most used annotations and its properties. Contributions are welcome :)

 | Annotation | Supported Properties |
 |:-------------|:-------------|
 | [@Api](https://www.javadoc.io/doc/io.swagger/swagger-annotations/1.5.20/io/swagger/annotations/Api.html) | value, tags |
 | [@ApiIgnore](http://springfox.github.io/springfox/javadoc/2.9.2/index.html?springfox/documentation/annotations/ApiIgnore.html) | Complete |
 | [@ApiModel](https://www.javadoc.io/doc/io.swagger/swagger-annotations/1.5.20/io/swagger/annotations/ApiModel.html) | value |
 | [@ApiModelProperty](https://www.javadoc.io/doc/io.swagger/swagger-annotations/1.5.20/io/swagger/annotations/ApiModelProperty.html) | value |
 | [@ApiOperation](https://www.javadoc.io/doc/io.swagger/swagger-annotations/1.5.20/io/swagger/annotations/ApiOperation.html) | value, notes, response, responseContainer |
 | [@ApiParam](https://www.javadoc.io/doc/io.swagger/swagger-annotations/1.5.20/io/swagger/annotations/ApiParam.html) | value |
 | [@ApiResponse](https://www.javadoc.io/doc/io.swagger/swagger-annotations/1.5.20/io/swagger/annotations/ApiResponse.html) | code, message, response, responseContainer |
 | [@ApiResponses](https://www.javadoc.io/doc/io.swagger/swagger-annotations/1.5.20/io/swagger/annotations/ApiResponses.html) | Complete |
 
## Usage

Help:

```shell script
java -jar springfox-to-springdoc.jar --help 
```

In-place migration:

```shell script
java -jar springfox-to-springdoc.jar --in-place $PROJECT_DIR/src/main/java
```

Write migrated sources into another directory

```shell script
java -jar springfox-to-springdoc.jar $PROJECT_DIR/src/main/java --out=/another/directory
```

Print migrated code into console

```shell script
java -jar springfox-to-springdoc.jar $PROJECT_DIR/src/main/java 
``` 

## License

springfox-to-springdoc licensed under terms of [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0).