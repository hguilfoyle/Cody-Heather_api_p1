# Project 1
For Project 1, you will be refactor a console-based student management application into a full-fledged web application. The server-side will be written using Java 8, persist data into a MongoDB (hosted on an EC2), and expose API endpoints using Java Servlets. The client-side of the application will be written using HTML, CSS, and JavaScript.


Presentation date: Friday, August 27th, 2021


## User Stories

As a faculty member I can:
- [ ] add new classes to the registration catalog
- [ ] change the registration details for a class (e.g. change open/close time, professor, description, etc.)
- [ ] remove a class from the registration catalog (this should unregister all registered students)

As a student, I can:
- [ ] register a new account with the system (must be secured with a password)
- [ ] login with my existing credentials
- [ ] view classes available for registration
- [ ] register for an open and available class
- [ ] cancel my registration for a class (if within window)
- [ ] view the classes that I have registered for

## Minimum Features
- [ ] Basic validation (e.g. no registration for classes outside of registration window, etc.) 
- [ ] Unit tests for all business-logic classes
- [ ] All exceptions are properly caught and handled
- [ ] Proper use of OOP principles
- [ ] Documentation (all classes and methods have basic documentation)
- [ ] Referential integrity (e.g. if a class is removed from the catalog, no students should be registered for it)
- [ ] Logging messages and exceptions to a file using a Java logging framework (e.g. Log4J2, SLF4J, etc.)
- [ ] Sensitive endpoints should be inaccessible to unauthenticated/unauthorized users
- [ ] All API endpoints must respond with an appropriate HTTP status code
- [ ] Server-side errors do not expose a stack trace to end-users
- [ ] Both the API and UI have CI/CD pipeline (using AWS CodePipeline, CodeBuild, and Elastic Beanstalk) for automated building and deployment of the app


## Tech Stack
- [ ] Java 8
- [ ] Apache Maven
- [ ] Java Servlets
- [ ] Apache Tomcat
- [ ] MongoDB
- [ ] HTML
- [ ] CSS
- [ ] JavaScript
- [ ] Git SCM (hosted on GitHub)
- [ ] AWS CodePipeline
- [ ] AWS CodeBuild
- [ ] AWS Elastic Beanstalk


## Init Instructions
- Choose a P0 (yours or your partner's) to base your P1 off of
- Create a new repository within this organization for the Java app (naming convention: `person1_person2_api_p1` - for example: `wezley_kyle_api_p1`)
- Create a new repository within this organization for the Java app (naming convention: `person1_person2_ui_p1` - for example: `wezley_kyle_ui_p1`)


## Presentation
- [ ] finalized version of application must be pushed to personal repository within this organization by the presentation date
- [ ] A brief PowerPoint presentation to introduce the team and the project
- [ ] ~10 minute live demonstration of the implemented features
