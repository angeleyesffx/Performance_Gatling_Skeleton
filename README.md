# Gatling Test 

_In case of "connect to host localhost port 22: Connection refused"_


- Remove SSH with the command:
    ```
    sudo apt-get remove openssh-client openssh-server
    ```
- Install SSH again with the command:
   ```
   sudo apt-get install openssh-client openssh-server
   ```


_In case of "operation not permitted":_

- Executing the following command line:
   ```
   sudo chmod +x my_script.sh
   ```

##                                                                    Install JDK
## ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


***Step 1: The Download***


  - Visit the oracle-java11-installer page (https://launchpad.net/~linuxuprising/+archive/ubuntu/java/+packages), to find out which version you need;

  - Locate the package for Focal;

  - Visit the Downloads page (https://www.oracle.com/java/technologies/javase-downloads.html) and locate the version that matches.

  - Click the Linux Compressed Archive .tar.gz package for Linux. The Oracle license agreement will be presented with a screen asking the acceptance
    
  - Select the checkbox to accept the license agreement and press the Download button.The download will begin and you may need to log in to your Oracle account.

  - Transfer it to your server, on your local machine, upload the file to your server. The following command assumes you’ve saved the Oracle JDK file to your local machine’s Downloads folder:
     ```
     scp Downloads/jdk-11.0.7_linux-x64_bin.tar.gz sammy@your_server_ip:~
	
     ```

***Step 2: Installing***


   - Install the software-properties-common package:
      ```
      sudo apt install software-properties-common 
      ```

   - Import the signing key used to verify the software:
      ```
      sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EA8CACC073C3DB2A
      sudo add-apt-repository ppa:linuxuprising/java 
      ```

   - Create this directory and move the Oracle JDK archive there:
      ```
      sudo mkdir -p /var/cache/oracle-jdk11-installer-local/ 
      sudo cp jdk-11.0.7_linux-x64_bin.tar.gz /var/cache/oracle-jdk11-installer-local/ 
      ```

   - Install the package:
      ```
      sudo apt install oracle-java11-installer-local
      ```

   - Select which version of Java you want to use.
       *** If you have multiple java packages installed on your machine, decide which version to use as the default type by using the following command:
       ```
       update-alternatives --config java
       ```	 

   - Set the JAVA_HOME environment variable by executing the commands below:
		          

###                     Setting Environment Variables
###    -------------------------------------------------------------------

   - To setup the JAVA_HOME is used the file /etc/environment:
      ```                  
      sudo vi /etc/environment
      ```
   - Add the line at the end of the file and save it:
      ```
      JAVA_HOME="/usr/lib/jvm/java-9-oracle"
      ```    
   - Check if java is installed by executing the command java –version.



###                               Export Variables
###    -------------------------------------------------------------------

   - To use the export just open the terminal and use the commando:
      ```
	 export JAVA_HOME=/usr/lib/jvm/java-9-oracle
      ```    

***If you have multiple java packages installed on your machine, decide which version to use as the default type by using the following command:***
      ```
        update-alternatives --config java
      ```
   - Check the configuration is ok, as expected:
      ```
        echo $JAVA_HOME
      ```    



##                                                                              Install Scala
## ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


***Step 1: Installing***

  - Install Scala Version
     ```
     sudo apt-get install scala 
     ```
  - Check the installation:
     ```
     scala -version    
     ```

##                                                                            Install Gatling
## ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


***Step 1: The Download***

   - Download the Gatling zip file by executing the command:
      ```
      wget https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/3.5.1/gatling-charts-highcharts-bundle-3.5.1-bundle.zip` 
      ```

***Step 2: Installing***


   ***In this tutorial the /root directory will be used on the installation ***

   - Move de file to /root 
      ```
     sudo mv Downloads/gatling-charts-highcharts-bundle-3.5.1-bundle.zip /root 
     ```     
   - Unzip the file:
      ```
      sudo unzip gatling-charts-highcharts-bundle-3.5.1-bundle.zip 
      ```
   - Set the GATLING_HOME environment variable by executing the commands below:
		          


###                     Setting Environment Variables
###    -------------------------------------------------------------------

   - To setup the GATLING_HOME is used the file /etc/environment:
     ```
     sudo vi /etc/environment 
     ```
   - Add the line at the end of the file and save it:
     ```
     GATLING_HOME="/root/gatling-charts-highcharts-bundle-3.5.1"
     ```  



##                                                                                Quick Start Gatling 
## ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



### 		                     Recording Gatling Scripts
### ---------------------------------------------------------------------------------------------------------------------

   1. Go to the bin directory with the command 
      ```
      cd gatling-charts-highcharts-bundle-3.5.1/bin/
      ``` 
   2. Run the command ./recorder.sh. A recorder window will be automatically displayed.



### 		                   Executing Gatling Scripts
### ---------------------------------------------------------------------------------------------------------------------

   1. Go to the bin directory with the command 
      ```
      cd gatling-charts-highcharts-bundle-3.5.1/bin/
      ```   
   2. Run the command ./gatling.sh. Load testing information will be displayed on the terminal.

   3. Choose the script file you want to execute from there.


###                                           Code Examples
### ---------------------------------------------------------------------------------------------------------------------

        https://github.com/krizsan/gatling-examples

