//package ejb;
//
//import org.wildfly.clustering.group.Group;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import javax.ejb.SessionContext;
//import javax.ejb.Stateful;
//import javax.ejb.TransactionAttribute;
//import javax.ejb.TransactionAttributeType;
//import java.io.Serializable;
//
//@Stateful
////@TransactionAttribute(TransactionAttributeType.MANDATORY)
//public class HelloBeanClusteringApi implements HelloBeanRemoteClusteringApi, Serializable {
//
//    @Resource
//    SessionContext ctx;
//
//    @Resource(lookup = "java:jboss/clustering/group/ee")
//    private Group channelGroup;
//
//
//    private int counter;
//
//    public HelloBeanClusteringApi() {
//    }
//
//    @PostConstruct
//    public void init() {
//        counter = 0;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.MANDATORY)
//    public String hello() {
//        System.out.println("");
//        final String node = System.getProperty("jboss.node.name");
//        String message = String.format("method hello() invoked by user %s on node %s, counter = %s", ctx.getCallerPrincipal().getName(), node, getCounterAndIncrement());
////        String message = String.format("method hello() invoked by user %s on node %s, counter = %s", ctx.getCallerPrincipal().getName(), getNodeName(), getCounterAndIncrement());
////        String message = String.format("method hello() invoked by user aaa");
//        System.out.println(message);
//        return message;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.MANDATORY)
//    public String getNodeName() {
//        System.out.println(channelGroup.getLocalNode().getName());
//        return channelGroup.getLocalNode().getName();
////        System.getProperty("jboss.node.name");
//
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.MANDATORY)
//    public int getCounterAndIncrement() {
//        System.out.println(++counter);
//        return counter;
//    }
//
//}
