# Running unit and integration tests

The Failsafe Plugin is designed to run integration tests while the Surefire Plugin is designed to run unit tests.
We can use Surefire to run IT tests, but Failsafe Plugin provides additional functionality for running tests that
require further setup and cleanup, like maybe to start the rest service, or clean DB after tests run.

### How to skip tests

To skip all tests, surefire and failsafe:

```bash
mvn package -DskipTests
```

To skip IT tests:

```bash
mvn package -DskipITs
```

To skip also the compilation of tests:

```bash
mvn package -Dmaven.test.skip=true
```

# Organising the unit test and integration tests

I always start to analyse and compare the 2 notion by their definition, starting from the root.

Unit testing
: Unit testing is the process where you test the smallest functional unit of code.
so the unit test is a block of code that verify other small unit of code to validate developer's implemented algorithm.

Integration testing
: Integration testing is a form of software testing in which multiple parts of a software system are tested as a group.
so in general integration test evaluate the compliance of a component with functional requirements.

### Types:

- Mix unit and integration tests
- Separate unit and integration tests on package level
- Separate unit and integration tests on root folder level
- Integration tests are a separated module

I would prefer the last 2 types, as I think integration tests should verify the functional requirements of a component
and not a specific unit of code.