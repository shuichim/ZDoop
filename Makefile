.PHONY: build
build:
	./gradlew shadowJar

run: build
	docker-compose down
	docker-compose up || true

eclipse:
	./gradlew eclipse

master:
	java -cp build/libs/ZDoop-all.jar org.iit.zserver.ZMaster m1 &

workers:
	java -cp build/libs/ZDoop-all.jar org.iit.zworker.ZWorker w1 &
	java -cp build/libs/ZDoop-all.jar org.iit.zworker.ZWorker w2 &
	java -cp build/libs/ZDoop-all.jar org.iit.zworker.ZWorker w3 &
	java -cp build/libs/ZDoop-all.jar org.iit.zworker.ZWorker w4 &
	java -cp build/libs/ZDoop-all.jar org.iit.zworker.ZWorker w5 &
	java -cp build/libs/ZDoop-all.jar org.iit.zworker.ZWorker w6 &
	java -cp build/libs/ZDoop-all.jar org.iit.zworker.ZWorker w7 &
	java -cp build/libs/ZDoop-all.jar org.iit.zworker.ZWorker w8 &

client:
	java -cp build/libs/ZDoop-all.jar org.iit.zclient.Client -f build/resources/main/1mb.txt
