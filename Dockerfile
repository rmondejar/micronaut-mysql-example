FROM adoptopenjdk/openjdk11
COPY . /opt/build
WORKDIR /opt/build
RUN ./gradlew assemble -Dorg.gradle.daemon=false

FROM adoptopenjdk/openjdk11:jre
EXPOSE 5000

COPY --from=0 /opt/build/build/libs/*-all.jar mn-data-mysql.jar
COPY ./ci/run.sh /root/run.sh
RUN chmod +x /root/run.sh

CMD /root/run.sh
