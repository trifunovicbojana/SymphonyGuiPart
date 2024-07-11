SymphonyTaskI

Prerequisites

Before you begin, ensure you have the following installed: Java Development Kit (JDK) 18 Apache Maven 3.9.1

Getting Started

To get a local copy up and running, follow these simple steps:

Clone the repository Download all dependecies using Maven

Running Tests To execute test use mvn command Default browser is chrome, default environment is https://symphony.is/ You can change it using -D e.g.: mvn test -Dtest=PartIRunner -Dbrowser=edge -Denvironment=https://testsymphony.is/

Folder Structure The project directory structure is organized as follows: src/main: Contains main source code. src/test: Contains test code, including Cucumber feature files, step definitions, and supporting classes. Reports: Contains execution report which is generetated after execution

Contact

For questions or support, feel free to reach out to Bojana via email at trifunovic.bojana@yahoo.com.
