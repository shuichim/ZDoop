From openjdk:jre-alpine

COPY ./build /usr/local/build
WORKDIR /usr/local

CMD ["java", "-cp", "build/lib/FLX-master-worker-0.1-all.jar", "master.main.Main"]
