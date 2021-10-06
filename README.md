## MeliBack

- [User Guide](https://spring.io/projects/spring-boot)
---

## Requirements

You will need the following things properly installed on your computer.

* [Git](http://git-scm.com/)
* [Gradle](https://gradle.org)

## Installation

We use [Gradle](http://www.gradle.org), a cross-platform build automation tool that help with our full development flow.
If you prefer [install Gradle](http://www.gradle.org/installation) or use a [Gradle wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html) inside this project.

* `git clone https://github.com/juliozarate5/meliback.git` this repository
* change into the new directory `cd meliback`

## Build project

```bash
./gradlew clean build
./w spotlessApply clean build
```

## Run tests

```bash
./gradlew clean test
```