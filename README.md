# Y.A.E.G.E.R 

[![Build Status](https://travis-ci.org/meronbrouwer/yaeger.svg?branch=master)](https://travis-ci.org/meronbrouwer/yaeger) 
[![Coverage Status](https://coveralls.io/repos/github/meronbrouwer/yaeger/badge.svg?branch=master)](https://coveralls.io/github/meronbrouwer/yaeger?branch=master) 
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=nl.meron%3Ayaeger&metric=alert_status)](https://sonarcloud.io/dashboard?id=nl.meron%3Ayaeger)


Y.A.E.G.E.R is Yet Another Education Game Engine Runtime. It's primary goal is to become a fully 
functional 2D game-engine that requires only a traditional Object Oriented style of programming.

### JDK 9+
YAEGER uses Google Guice for dependency injection, which uses reflection to create the required
instances. Since the module system from Java9 reflective access is limited, causing Runtime errors when using Google Guice.

To enable Google Guice to access al required Java classes, add the following VM options
to the run configuration:
```
--add-opens java.base/java.lang=com.google.guice
```

## API

The full java API of yaeger is available at: [https://meronbrouwer.github.io/yaeger/](https://meronbrouwer.github.io/yaeger/)

## Disclaimer

This application is in no way affiliated to the HAN University of Applied Sciences. 
