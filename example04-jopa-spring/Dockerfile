# Use the official Tomcat image from Docker Hub
FROM tomcat:jdk17

# Optional: Copy a sample WAR file to the webapps directory
# This line is useful if you want to copy a WAR file when building the image itself
# You can remove this if the WAR will be synced via docker-compose
COPY ./target/example-04-2.0.0.war /usr/local/tomcat/webapps/
