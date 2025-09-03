# Bajaj Finserv Health - Qualifier Submission

**Candidate:** Mayur TS

## Project Status

This Spring Boot application has been built to fulfill all the requirements of the qualifier task.

- The application compiles and packages into an executable JAR file successfully.
- On startup, it is designed to perform the full sequence:
  1.  Generate the webhook via POST request.
  2.  Submit the final SQL query to the returned webhook URL using the provided JWT.

## Server Issue Encountered

During development and testing, the initial API endpoint (`https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA`) was consistently returning a **`503 Service Temporarily Unavailable`** error.

This indicates that the application is functioning correctly, but the remote server was offline at the time of submission. I have included the final, working code and the compiled JAR file as requested.

**Terminal Output Showing the Error:**

        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:569)
        at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:91)
        at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:53)
        at org.springframework.boot.loader.launch.JarLauncher.main(JarLauncher.java:58)
21:28:24.926 [main] ERROR c.b.w.service.WebhookService - Failed to generate webhook
