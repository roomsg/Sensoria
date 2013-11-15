Sensoria
========

## What's in it for the end-user ?
It gives the user an overview of all the available sensors and their actual values in a single activity.
Other apps in the playstore only seem to show values for a (single) sensor at the time ?!

## What's in it for me (as a developer) ?
Android (playground) project to explore:
- Android development in general
- Junit test on Android
- Gradle and its Android plugin

### Scope
- Make it run on all Android platform versions
- Prefer that (compatibility) to fancy UI
- Decoupled design
- Low memory footprint (yeah, I know, probably does not makes a lot of sense these days...)

### Explore (possibilities of) other interesting languages / FWs / ...
- Groovy
- Dagger
- ...

### TODOs
- find out why suddenly the startup of the overview activity has becoming so terribly slow
- fix "missing" sensors (since android 4.3 ?)
- select one overview implementation as startup screen
- use TimerTask (..) iso plain sleeps for refreshing the sensor values
- extend (basic) implementation of the sensor details activity
- ...
