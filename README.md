## AutoFormFiller Application
AutoFormFiller is a Java-based web application that uses Vaadin components and OpenAI GPT-3 to automatically fill out forms based on user prompts.

## Features
Automatically fill out forms based on user prompts and GPT-3 responses
Supports various Vaadin components, including TextField, ComboBox, Checkbox, DatePicker, and Grid
Easily customizable and extendable
## Prerequisites
Java Development Kit (JDK) 8 or higher
Maven 3.6 or higher
Vaadin 14 or higher
OpenAI GPT-3 API key
## Setup For Testing This Component
Clone the repository or download the source code.
In the project directory, set the GPT_API_KEY environment variable to your OpenAI GPT-3 API key. For example:
```
export GPT_API_KEY=<your-api-key>
```

or, in Windows:

```
$Env:GPT_API_KEY="<your-api-key>"
```
Build and run the application using Maven:
```
mvn clean install
mvn spring-boot:run
```
Open your web browser and navigate to http://localhost:8080 to view the test forms.

## Usage
Click the "Process" button to automatically fill out the form with the data based on the provided user prompt.

You can customize the user prompt and GPT-3 instructions in the CustomerOrdersView.java file.
Customization

You can extend and modify the AutoFormFiller class to support additional Vaadin components or to implement your own GPT service provider by implementing the AiServiceProvider interface.

License
This project is released under the MIT License.
## Add-on architecture
![server-side-addon](https://user-images.githubusercontent.com/991105/211870086-75544597-847d-4d21-82fa-341411753558.svg)

## Alternative add-on templates

If you wish to build and publish an add-on or extension in [Vaadin Directory](https://vaadin.com/directory), Vaadin provides the following three template projects:
 1. **(this repo)** [vaadin/addon-template](https://github.com/vaadin/addon-template): Create a composite component. This Java-only template is the easiest when extending Vaadin Java components.
 2. [vaadin/client-server-addon-template](https://github.com/vaadin/client-server-addon-template): Build a standalone, client-server TypeScript-Java component. This template provides you with a [Lit-based](https://github.com/lit/lit/) example to start with.
 3. [vaadin/npm-addon-template](https://github.com/vaadin/npm-addon-template): Wrap a web component from [npmjs.com](https://npmjs.com/) as a Vaadin Java component.


## Development instructions

### Important Files 
* TheAddon.java: this is the addon-on component class. You can add more classes if you wish, including other Components.
* TestView.java: A View class that let's you test the component you are building. This and other classes in the test folder will not be packaged during the build. You can add more test view classes in this package.
* assembly/: this folder includes configuration for packaging the project into a JAR so that it works well with other Vaadin projects and the Vaadin Directory. There is usually no need to modify these files, unless you need to add JAR manifest entries.

If you are using static resources such as images, JS (e.g. templates) and CSS files the correct location for them is under the `/src/main/resources/META-INF/resources/frontend` directory and is described here [Resource Cheat Sheet](https://vaadin.com/docs/v14/flow/importing-dependencies/tutorial-ways-of-importing.html#resource-cheat-sheet)in more details. 

### Deployment

Starting the test/demo server:
```
mvn jetty:run
```

This deploys demo at http://localhost:8080
 
### Integration test

To run Integration Tests, execute `mvn verify -Pit,production`.

## Publishing to Vaadin Directory

You should change the `organisation.name` property in `pom.xml` to your own name/organization.

```
    <organization>
        <name>###author###</name>
    </organization>
```

You can create the zip package needed for [Vaadin Directory](https://vaadin.com/directory/) using

```
mvn versions:set -DnewVersion=1.0.0 # You cannot publish snapshot versions 
mvn install -Pdirectory
```

The package is created as `target/{project-name}-1.0.0.zip`

For more information or to upload the package, visit https://vaadin.com/directory/my-components?uploadNewComponent

## Hints:

To Debug with Maven Jetty:

```
export MAVEN_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000"
mvn jetty:run -DGPT_API_KEY=<your-api-key>
```
