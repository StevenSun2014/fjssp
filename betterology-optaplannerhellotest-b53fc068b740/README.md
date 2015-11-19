# Optaplanner Hello 5 Times #

This project as written to be the exact mimimum necessary to get OptaPlanner up and running.

### Appearances of Tight Coupling Makes Learning OptaPlanner Challenging, At Least To a Developer Like Me ###

Sometimes when a new technology comes along, it can be a real head scratcher to figure out the basic stuff. That's when a "See Dick Run" level of code is the easiest way to learn it. Something with just the basic semantics to just see how some technology works. The slightest extra details throw us off and confuse us - such as for example, extra UI, persistence, misc infrastructure.

I found the examples that come with OptaPlanner to be brilliant, helpful, and necessary to understand what the possibilities of this technology can and does address. OptaPlanner example code is excellent and helpful. But at first, it might seem like OptaPlanner code is tightly coupled with the persistence and UI layers. That made it really hard for me to figure out where OptaPlanner starts, and the UI and persistence layers stop.

### OptaPlanner Basics ###

* OptaPlanner is a unique project with many unusual paradigms.
* Also: [Learn OptaPlanner From It's Own Examples](https://bitbucket.org/tutorials/markdowndemo)
* More: [Author's Blog about this project](http://appwriter.com/new-easier-optaplanner-example-project)

### What is this project? ###

* The exact minimum amount of code necessary to demonstrate how OptaPlanner code works
* Only 270 lines of code, even including Eclipse .project and .classpath and pom.xml
* Written in Java
* Uses a drools file for the OptaPlanner rules
* Has no persistence, UI, or related infrastructure to confuse you
* Does not even have tests - one main() method
* Excercises a single PlanningVariable, in a single PlanningEntity

### Watch What Happens! ###

* Download code
* Run Maven
* Run HelloApp.main()
* Change up the rules, even PlanningVariables and PlanningEntities - watch what happens!

### What does this planner do? ###

* Sets the single PlanningVariable to 5
* Gives you a place to experiment with the drools file
* Gives you a place to start your own project without someone else's UI or database

### Requirements ###

* Java
* Maven

### Extras ###

* This project will import directly as an Eclipse (Luna) project