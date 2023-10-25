# SimpleBox4PFAS

SimpleBox4PFAS is a multimedia fate model specifically designed for use with chemicals - in particular with PFAS - that is relevant for regulatory purposes. The tool predicts the background concentrations of chemicals in air, water, sediment, and soil. For more information, please visit https://www.rivm.nl/en/soil-and-water/simplebox. 
SimpleBox4PFAS is developed in Java while for the GUI interface zk framework is used. 


# Using with eclipse IDE
You can directly import this Git repository into the Eclipse IDE. In Eclipse, go to File → Import → Git → Projects from Git (with smart import) and click Next. Select "Clone URI" and click Next. In the URI field, enter https://github.com/NovaMechanicsOpenSource/SimpleBox4PFAS and your user credentials. Click Next twice. After the project is cloned, make sure to select "Search for nested projects" and check the box for "SimpleBox4PFAS\SimpleBox4PFAS" to import it as an Eclipse project. Be sure to uncheck the other box (SimpleBox4PFAS). Click Finish.

To use Eclipse, you must have the ZK framework installed. You can find installation instructions for ZK Studio Essentials at https://www.zkoss.org/wiki/ZK_Studio_Essentials/Installation. You will also need a server to deploy the web application, such as WildFly. Instructions for configuring Eclipse with WildFly can be found at https://www.baeldung.com/eclipse-wildfly-configuration.

