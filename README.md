# Api-Factory
[![Build Status](https://travis-ci.org/sbtqa/api-factory.svg?branch=master)](https://travis-ci.org/sbtqa/api-factory) [![GitHub release](https://img.shields.io/github/release/sbtqa/api-factory.svg?style=flat-square)](https://github.com/sbtqa/api-factory/releases) [![Maven Central](https://img.shields.io/maven-central/v/ru.sbtqa.tag/api-factory.svg)](https://mvnrepository.com/artifact/ru.sbtqa.tag/api-factory) [![Gitter](https://img.shields.io/gitter/room/nwjs/nw.js.svg)](https://gitter.im/sbtqa-tag/Lobby)

Api-Factory - opensource java framework для автоматизации функционального тестирования средствами API.

### О Api-Factory

Api-Factory позволяет писать автотесты на человекочитаемом языке, тем самым понижая входной порог для разработчиков тестов, и повышая их читаемость неподготовленными пользователями. Api-factory использует framework [Cucumber-JVM](https://github.com/cucumber/cucumber-jvm), но, в отличие от чистого использования, в котором довольно большую часть архитектуры занимают шаги(stepdefs), здесь акцент сделан на то, чтобы избавиться от необходимости писать их самому и сократить количество самописных шагов(stepdefs), сосредоточившись на описании кода методов API с использованием паттерна [PageObject](https://martinfowler.com/bliki/PageObject.html).
В Api-Factory уже реализовано много стандартных шагов(steps), которых хватит чтобы начать разрабатывать автоматизированные тесты.

Api-Factory кроссплатформенный фреймворк, поддерживающий такие протоколы как [REST](https://ru.wikipedia.org/wiki/REST), [SOAP](https://ru.wikipedia.org/wiki/SOAP).

### Требования
Для работы api-factory нужно:
1. Java 7 или выше

### Контакты
Нашли ошибку или появились вопросы? [Проверьте](https://github.com/sbtqa/api-factory/issues), нет ли уже созданных задач. Если нет, то создайте [новую](https://github.com/sbtqa/api-factory/issues/new)! Также свои вопросы можно задать в нашем чате в [gitter](https://gitter.im/sbtqa-tag/Lobby)

### Лицензия
Api-Factory выпущен под лицензией Apache 2.0. [Подробности](https://github.com/sbtqa/api-factory/blob/master/LICENSE).
