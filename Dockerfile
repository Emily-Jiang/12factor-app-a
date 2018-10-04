FROM open-liberty:microProfile2
MAINTAINER IBM Java engineering at IBM Cloud
ARG PACKAGE_FILE
COPY target/$PACKAGE_FILE /config/
RUN apt-get update \
    && apt-get install -y --no-install-recommends unzip curl\
    && unzip /config/$PACKAGE_FILE \
    && cp -r /wlp/usr/servers/defaultServer/* /config/ \
    && rm -rf /config/wlp \
    && rm -rf /config/$PACKAGE_FILE \
    && apt-get remove -y unzip

EXPOSE 9080 9443
