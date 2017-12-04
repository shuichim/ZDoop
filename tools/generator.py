#!/usr/bin/env python3

from sys import argv

def header():
    ret = "version: '2'\n"
    ret += "services:\n"
    ret += "  zookeeper:\n"
    ret += "    image: zookeeper\n"
    ret += "    ports:\n"
    ret += "      - 2181:2181\n"
    return ret

def buildcontext():
    ret = "    build:\n"
    ret += "      context: ../\n"
    return ret

def volumes():
    ret = "    volumes:\n"
    ret += "      - ../build:/usr/local/build\n"
    return ret

def master():
    ret = "  master:\n"
    ret += buildcontext()
    ret += volumes()
    ret += "    depends_on:\n"
    ret += "      - \"zookeeper\"\n"
    ret += "    command: java -cp build/libs/ZDoop-all.jar org.iit.zserver.ZMaster m1\n"
    return ret

def worker(id):
    name = "worker-"+str(id)
    ret = "  "+name+":\n"
    ret += buildcontext()
    ret += volumes()
    ret += "    depends_on:\n"
    ret += "      - \"master\"\n"
    ret += "    command: java -cp build/libs/ZDoop-all.jar org.iit.zworker.ZWorker w"+str(id)+"\n"
    return ret

def client(workersize):
    ret = "  client:\n"
    ret += buildcontext()
    ret += "    volumes:\n"
    ret += "      - ../build:/usr/local/build\n"
    ret += "      - ../build/classes/java/main:/usr/local/bin\n"
    ret += "    depends_on:\n"
    for i in range(workersize):
        name = "worker-"+str(i+1)
        ret += "      - \""+name+"\"\n"
    ret += "    command: java -cp build/libs/ZDoop-all.jar org.iit.zclient.Client -f build/resources/main/1mb.txt"
    return ret

script, workersize = argv

if __name__ == "__main__":
    filename = "./tools/docker-compose-"+workersize+".yml"
    with open(filename, 'w') as f:
        f.write(header())
        f.write(master())
        for i in range(int(workersize)):
            f.write(worker(i+1))
        f.write(client(int(workersize)))
