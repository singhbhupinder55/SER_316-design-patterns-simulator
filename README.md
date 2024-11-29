# Design Patterns Simulator

This repository contains the basic setup for the **Design Patterns Simulator** project, created as part of the SER316 coursework at Arizona State University. The project aims to implement and demonstrate various design patterns in Java.

## Current Setup
The following components are included in this submission:
- **Basic Program**: A simple program that outputs "Hello, Silicon Valley Simulator!" as a placeholder for future functionality.
- **Gradle Build System**: The project uses Gradle for building and dependency management.
- **Static Analysis Tools**:
  - **Checkstyle**: Ensures consistent code style and quality.
  - **SpotBugs**: Identifies potential bugs in the code.
- **Testing Framework**: JUnit is included, and a basic test case is implemented.
- **GitHub Actions**: Continuous integration is set up to ensure the project builds and tests successfully with every commit.

## Planned Design Patterns
This project will implement several design patterns, including but not limited to:
1. **Singleton**: Ensures a class has only one instance and provides a global point of access.
2. **Factory**: Simplifies object creation by abstracting the instantiation process.
3. **Observer**: Establishes a one-to-many dependency where multiple objects are notified of changes in one object.
4. **Strategy**: Defines a family of interchangeable algorithms, allowing dynamic behavior changes at runtime.

## How to Build and Run
To work with this project, follow the instructions below:

1. Clone the repository:
   ```bash
   git clone <repository-link>
   cd siliconvalleysimulator-DesignPatternsSimulator


## ScreenShots of SpotBugs Report

Here is a screenshot of the SpotBugs report after running the analysis:
![SpotBugs Report 1](images/sb1.png)
![SpotBugs Report 2](images/sb2.png)
![SpotBugs Report 3](images/sb3.png)


## Reason for Unresolved Issues in SpotBugs:

- **Checkstyle (%n vs \n)**: This warning suggests using %n for platform-independent newlines, but changing to %n would require significant refactoring and could lead to platform-specific issues, so it was left unresolved.

- **SpotBugs (Exposing Array)**: The warning about returning an array from a static method relates to design decisions in our project, where returning the array is necessary for the intended functionality. Refactoring this would complicate the architecture unnecessarily, so it was not fixed.
- 
- Both issues do not impact core functionality, and resolving them would introduce unnecessary complexity.
 

## ScreenShots of CheckStyle Report
Here is a screenshot of the Checkstyle report:
![CheckStyle Report](images/checkstyle.png)

## Resolution of Checkstyle Warnings:
 - All Checkstyle warnings have been successfully resolved. The audit shows no errors in any of the 23 files analyzed. This indicates that the code adheres to the specified coding standards, and no further issues were found during the check.

## ScreenShots of Jacoco Report
Here is a screenshot of the Jacoco report:
![Jacoco Report](images/jacocoReport.png)

## JaCoCo Report Summary:
- The JaCoCo report indicates the following test coverage:
-Overall Test Coverage: 83% (351 out of 2,101 instructions covered).
- Branch Coverage: 86% (28 out of 206 branches covered).
- Simulation Package: 94% coverage.
- Events Package: 100% coverage, showing excellent testing for this module.
- Helper and Singleton Packages: Low coverage in the helper package, with 0% coverage.


## ScreenShots of Junit Report
Here is a screenshot of the Junit report:
![Junit Report1](images/Junit1.png)
![Junit Report2](images/Junit2.png)

## Junit Report Summary:

- The JUnit reports show that all tests in the project have successfully passed with a 100% success rate.

- The first report includes a summary of 102 tests, all passing with no failures or ignored tests. It shows individual test packages, including patterns.factoryTest, patterns.singletonTest, and simulationTest, each with 100% success.

- The second report provides a detailed breakdown of the test classes, showing that each test class (e.g., BuildingFactoryTest, SimulationControllerTest, StartupTest, etc.) executed 100% successfully with no failures or ignored tests. The tests were completed in a total duration of 0.242 seconds.

- These reports confirm that the tests have been properly executed, covering all the critical components of the project with a perfect pass rate.








