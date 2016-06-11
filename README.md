Sensoria
========

## Build
This project is using the gradle wrapper, so no need to install/download any gradle version, the wrapper takes care of it all.
Just type: "./gradlew(.bat) clean build"

## Description
This is an Android application that is mainly targetted to Android developpers...  
Have you ever been wondering about the following:
- which (type of) sensors are available at my device ?
- which are their values and how do they (possibly) correlate ?

Even more, in case there are multiple sensors available for a given type:
- which sensor is the default ?
- what are the differences between the values of the different sensors ?

"Sensoria" tries to give you an answer to all those questions.  
Other apps in the playstore are not really helping (AFAIK), since they only seem to show values for a (single) sensor at the time ?!

## Personal objectives
Android (playground) project to explore:
- Android development in general: explore Android API + tools, app vs lib/service, ...
- JUnit (..) tests
- Gradle and its Android plugin

### Personal Scope
- Make it run on all Android platform versions
- Prefer that (compatibility) to fancy UI
- Decoupled design
- Low memory footprint (yeah, I know, probably does not makes a lot of sense these days, just a habit of those early days)

### Explore (possibilities of) other interesting stuff..
- Dagger
- ...

### TODOs
- extend basic implementation of the sensor details activity
- ...
