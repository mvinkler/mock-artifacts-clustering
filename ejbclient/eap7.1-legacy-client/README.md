### EAP 7.1 legacy (3.x) EJB client - reproducer for JBEAP-11449

1. git clone https://github.com/mvinkler/mock-artifacts-clustering.git
   cd mock-artifacts-clustering/ejbclient/eap7.1-legacy-client/

2. unzip 2 EAP distributions and run it with standalone-ha.xml profile:
  ${EAP71_HOME}/bin/standalone.sh -Djboss.node.name=node1 -c standalone-ha.xml
  ${EAP71_HOME}/bin/standalone.sh -Djboss.node.name=node2 -c standalone-ha.xml -Djboss.socket.binding.port-offset=100

3. build `server` project, deploy it to both servers

4. build and run `client` project using 
  mvn clean package -Pejbclient3x
  mvn exec:exec -Pejbclient3x
  
5. press ENTER few times in the terminal with the running client, invocations go to one of the two nodes (each time the same node)
6. kill the server which handled the invocations
7. press ENTER one more time - the client should get "Connection refused" and retry on the second node, it does not

