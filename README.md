Sensoria
========

## Description
Android application that is mainly targetted to Android developpers...  
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
- Android development in general: app vs lib, explore API
- (Junit) test on Android
- Gradle and its Android plugin

### Personal Scope
- Make it run on all Android platform versions
- Prefer that (compatibility) to fancy UI
- Decoupled design
- Low memory footprint (yeah, I know, probably does not makes a lot of sense these days, just a habit of those early days)

### Explore (possibilities of) other interesting stuff..
- Groovy
- Dagger
- ...

### TODOs
- find out why suddenly the startup of the overview activities has becoming so _terribly_ slow
- use TimerTask (..) iso plain sleeps for refreshing the sensor values
- extend (basic) implementation of the sensor details activity
- split off lib (service) and app ?
- ...
