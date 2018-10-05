FROM open-liberty:microProfile2
MAINTAINER IBM Java engineering at IBM Cloud

# The name of the server package file produced by the build
ARG PACKAGE_FILE

# Install unzip and curl
RUN apt-get update \
    && apt-get install -y --no-install-recommends unzip curl\
    && rm -rf /var/lib/apt/lists/* 

COPY target/$PACKAGE_FILE /config/

# Extract the server package and move files to the correct locations
RUN unzip /config/$PACKAGE_FILE \
    && cp -r /wlp/usr/servers/defaultServer/* /config/ \
    && rm -rf /config/wlp \
    && rm -rf /config/$PACKAGE_FILE

EXPOSE 9080 9443
