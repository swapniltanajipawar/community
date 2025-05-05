===

You need to use the java 17 in the path and in JAVA_HOME and JRE_HOME

====

delete old jar file from the target, open command prompt and run directly below command:
del /s /q C:\Users\swpawar\.m2\repository\org\springframework\boot



===

Step 2: Install Maven
Download Maven from here.

Extract the downloaded archive to a location on your PC, e.g., C:\Program Files\Apache\Maven.

Set the MAVEN_HOME and update PATH:

Open System Properties (Win + Pause → Advanced system settings → Environment Variables).

Under System Variables, click New and add:

Variable name: MAVEN_HOME

Variable value: C:\Program Files\Apache\Maven (or wherever you extracted Maven).

In the System Variables section, find the Path variable and click Edit. Add:

C:\Program Files\Apache\Maven\bin

Verify Installation:

Open a new Command Prompt and run:

mvn -v
You should see Maven’s version details.

Step 3: Retry Your Build
Now that Maven is installed and recognized, you can run:


mvnw clean package


=====

Download NSSM:

Visit the official NSSM website and download the appropriate version for your system (32-bit or 64-bit).

Extract the downloaded archive to a directory, e.g., C:\nssm.

Open Command Prompt as Administrator:

Press Win + X and select Command Prompt (Admin) or Windows PowerShell (Admin).

Install Your Spring Boot Application as a Service:

Navigate to the NSSM directory:


cd C:\nssm
Run the NSSM install command:


nssm install MySpringBootApp


Solution: Ensure NSSM Executable is Accessible
1. Locate nssm.exe
After extracting the NSSM archive, you'll find the nssm.exe file in one of the following directories, depending on your system architecture:

For 64-bit systems:


C:\Users\swpawar\Downloads\nssm-2.24\nssm-2.24\win64\nssm.exe
For 32-bit systems:


C:\Users\swpawar\Downloads\nssm-2.24\nssm-2.24\win32\nssm.exe
2. Add NSSM to System PATH
To run nssm from any command prompt, add its directory to the system's PATH environment variable.

Steps:
Copy nssm.exe to a Permanent Location:

Create a directory, e.g., C:\nssm.

Copy the appropriate nssm.exe (from win64 or win32) into C:\nssm.

Add C:\nssm to System PATH:

Press Win + R, type sysdm.cpl, and press Enter.

In the System Properties window, go to the Advanced tab and click on Environment Variables.

Under System variables, find and select the Path variable, then click Edit.

Click New and add C:\nssm.

Click OK on all open dialogs to apply the changes.

Verify NSSM is Recognized:

Open a new Command Prompt window.

Type nssm and press Enter.

If set up correctly, you'll see NSSM's usage information.

3. Install Your Spring Boot Application as a Service
With NSSM accessible, you can now install your Spring Boot application as a Windows service.

Steps:
Open Command Prompt as Administrator:

Press Win + X and select Command Prompt (Admin) or Windows PowerShell (Admin).

Install the Service:

Navigate to your application's directory:


cd C:\Users\swpawar\Downloads\community\community
Run the NSSM install command:


nssm install MySpringBootApp
Configure the Service:

In the NSSM GUI that appears:

Application Path: Browse to your Java executable, e.g., C:\Program Files\Java\jdk-17\bin\java.exe.

Startup Directory: Set to the directory containing your JAR file, e.g., C:\Users\swpawar\Downloads\community\community.

Arguments: Enter -jar community-0.0.1-SNAPSHOT.jar.

Install and Start the Service:

Click Install service.

Start the service using:


nssm start MySpringBootApp
Your Spring Boot application should now run as a Windows service and remain active even after closing the Command Prompt.

