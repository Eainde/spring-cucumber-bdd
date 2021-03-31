# Behaviour Driven Development(BDD) using Spring and Cucumber

In their quest for delivering high-quality defect-free software under restricted timelines, Agile and DevOps teams
started adopting Test-First Development (TFD) methodologies. In contrast to the traditional development methodologies
like waterfall model, here automated tests are created first, and they serve as the basis for coding. Behavior Driven
Development (BDD) is the most evolved of the TFD methodologies that are currently used.

Behavior Driven Development (BDD) methodology is a refinement of TDD(Test Driven Development) and ATDD(Acceptance Test
Driven Development).

### Cucumber

Cucumber is an open source tool which aids BDD implementation in the following ways:

**Executable Specifications :** Allows you to write examples in a business-readable, domain-specific language which
serves as acceptance tests and specifications at the same time.

**Automated Tests :** Supports integration with automation testing tools like selenium to automate the testing of
executable specifications.

**Living Documentation :** At any point in time , it lets you know which examples are implemented and working and which
ones aren't.

### Features of Cucumber

* At the core of Cucumber is the human-readable non-technical language called Gherkin which is used to write tests.

* It allows the acceptance tests to be hierarchically constructed as features (user stories) -> scenarios (behaviors) ->
  steps (examples).

* Depending on the platform used to develop applications and automate tests, Cucumber supports implementation in at
  least 12 technologies like Ruby, Java, JavaScript, Python, .NET, PHP, etc.