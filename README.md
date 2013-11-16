Sensoria
========

## Description
Have you ever been wondering (as end user or Android dvelopper) about the following questions:
- which (type of) sensors are available at your device ?
- which are there values and how do they (possibly) correlate ?
- in case there are multiple sensors available for a given type:
-- which sensor is the default ?
-- what are the differences between the different sensors ?
Other apps in the playstore are not really helping (AFAIK), since they only seem to show values for a (single) sensor at the time ?!

## What's in it for me (as a developer) ?
Android (playground) project to explore:
- Android development in general: app vs lib, explore API
- (Junit) test on Android
- Gradle as a build system and its Android plugin

### Scope
- Make it run on all Android platform versions
- Prefer that (compatibility) to fancy UI
- Decoupled design
- Low memory footprint (yeah, I know, probably does not makes a lot of sense these days...)

### Explore (possibilities of) other interesting stuff..
- Groovy
- Dagger
- ...

### TODOs
- find out why suddenly the startup of the overview activities has becoming so terribly slow
- fix "missing" sensors (since android 4.3 ?)
- select one overview implementation as startup screen
- use TimerTask (..) iso plain sleeps for refreshing the sensor values
- extend (basic) implementation of the sensor details activity
- ...
