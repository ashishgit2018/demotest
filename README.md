# demotest - This Project is to Automate the Rest, DB and UI tests.

The project comprises of 5 modules
1. test-rest - contains the java steps to perform rest operations
2. test-db - contains the java steps to perform DB operations
3. test-ui - contains the java steps to perform UI/Selenium operations
4. test-core - contains the java core steps to be used across all other projects
5. test-auto - this is the module where you put all your cucumber feature files, the config files and other configurations

/test-auto/src/main/resources/<env>/config - put all your properties file here
/test-auto/src/main/resources/<env>/jmeter - put JMeter JMX file here
/test-auto/src/main/resources/<env>/json - put the .json files used for rest API testing here

/test-auto/logs/ - Logs generated during run time
/test-auto/output/ - Screenshots in HTML format generated during UI tests execution

/test-auto/target/jmeter-reports/ - The generated JMeter HTML report location
/test-auto/taret/cucumber-reports/- The generated Cucumber Json and HTML reports

Commands:
Clean compile the Parent project
/test-main/ - mvn clean install -DskipTests

Run tests Sequentialy
/test-auto/ - mvn install -PTestRunner [-D<options>]
Options:-
-Denv=qa
-DbrowserName=Chrome
-DbrowserVersion=latest
-DOSPlatform=Windows 10
-DdevicePlatformName=Android
-DdevidePlatformVersion=7.1
-DdeviceAppiumVersion=1.8.1
-DdeviceName=Android GoogleAPI Emulator
-DcucumberRunner=RunCucumberIT
-DcucumberTags=@ui

Run tests Parallely
/test-auto/ - mvn install -PParallelTestRunner [-D<options>]
Options:-
-Denv=qa
-DbrowserName=Chrome
-DbrowserVersion=latest
-DOSPlatform=Windows 10
-DrunOnSauce=true
-DdevicePlatformName=Android
-DdevidePlatformVersion=7.1
-DdeviceAppiumVersion=1.8.1
-DdeviceName=Android GoogleAPI Emulator
-DcucumberRunner=RunCucumberIT
-DcucumberTags=@ui
-DforkCount=5
