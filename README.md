# Yaeger 

[![Java CI with Maven](https://github.com/han-yaeger/yaeger/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/han-yaeger/yaeger/actions?query=workflow%3A%22Java+CI+with+Maven%22)
[![codebeat badge](https://codebeat.co/badges/e5806ed2-598a-4597-b85b-3940650927e3)](https://codebeat.co/projects/github-com-han-yaeger-yaeger-master)
[![CodeFactor](https://www.codefactor.io/repository/github/han-yaeger/yaeger/badge)](https://www.codefactor.io/repository/github/han-yaeger/yaeger)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=han-yaeger_yaeger&metric=alert_status)](https://sonarcloud.io/dashboard?id=han-yaeger_yaeger)

Yaeger (Yet Another Education Game Engine Runtime) is a fully functional 2D game-engine that 
requires only a traditional Object Oriented style of programming. It is based on JavaFX and 
requires Java 12 or above to work.

## Versioning
Because Yaeger will be used in an educational context, versioning will be based on school years.
Thus version 2020.2021 will be used during the school year that start in September 2020 and ends in July 2021.

### Breaking changes
It is likely that the API will break between different versions. This is partially intended, since it is to
be used in an educational context and there is no shame in preventing students from using previous iterations.

## Documentation
* API: [https://han-yaeger.github.io/yaeger/](https://han-yaeger.github.io/yaeger/)
* Tutorial: [Yaeger tutorial](docs/tutorial.md)
* Architecture: [Yaeger's architecture](docs/architecture.md)

## Modern Java, but an API with only traditional Object Orientation?
Yaeger is to be used in a course that is part of the first year at the HAN University of applied sciences. Students
just learned to master Object Orientation in the traditional sense and therefore the API of Yaeger is only targeted 
towards that use case. Fancy functional aspects (such as Streams, Lambda's) are used with Yaeger itself, but are not 
exposed through it's API.

## Contributions
Contributing to Yaeger is encouraged and we would love to review your Pull Requests. Either
pick up one of the Issues or implement a feature you've been missing. Ensure that you feature does not require modern 
Java features to be exposed through the API. 
