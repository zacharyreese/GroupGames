
# Group Games (Kahoot Against Humanity)
Distributed Web Systems Design [CSCI-5436-A]

# Before you begin
Download the JBoss Wildfly Server: [here](http://wildfly.org/downloads/) or [direct download v14.0.1](http://download.jboss.org/wildfly/14.0.1.Final/servlet/wildfly-servlet-14.0.1.Final.zip)
Unzip the contents to a permanent installation directory. (Remember this location. We'll use it later)

# IntelliJ Idea *Ultimate* Setup
**NOTE:** IntelliJ Idea ***Ultimate*** is required. JBoss servers are a pro feature and are not included in the community edition

#### Cloning Project from Github
 1. File -> New -> Project from Version Control (Git)
 2. Enter the following URL and press clone:
     https://github.com/jposton96a/GroupGames.git
 3. Follow default prompts until prompted to verify added libraries. Make sure to uncheck the "lib" folder. (We'll have to manually add these later)
 4. Accept defaults for rest of project creation
 5. Once project is created, import each library individually from the lib folder (right click -> add as library)
 
#### Setup IntelliJ environment
 1. Run -> Edit Configurations
 2. Click the + in the top left -> JBoss Local Server
 3. Give the run configuration a name like "JBoss Local vX.X.X"
 3. Next to Application Server, press Configure
 4. Set the JBoss home to where Wildfly was extracted earlier
 5. If you see a warning for "No artifacts configured" press Fix. Otherwise press Ok & navigate to File -> Project Structure
 6. On the left side, select Facets -> Web
 7. Note the warning message at the bottom stating that "web" facet resources are not included in the artifacts.
 8. Press Create Artifact on the warning message. Press Ok
 
 You should now be ready to start the server. Run/Debug using the Run menu at the top. If your browser loads the index file, you're good to go!
 
