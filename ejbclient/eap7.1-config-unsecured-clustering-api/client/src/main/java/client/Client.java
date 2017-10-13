package client;

import java.security.PrivilegedActionException;
import java.security.Security;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.*;

import org.jboss.ejb.client.Affinity;
import org.jboss.ejb.client.ClusterAffinity;
import org.jboss.ejb.client.EJBClient;
import org.jboss.ejb.client.NodeAffinity;
import org.wildfly.naming.client.WildFlyInitialContextFactory;
import org.wildfly.security.WildFlyElytronProvider;

import ejb.HelloBeanRemote;

public class Client {

    public static void main(String[] args) throws NamingException, PrivilegedActionException, InterruptedException {

        InitialContext ctx = new InitialContext(getCtxProperties());

        final UserTransaction tx = (UserTransaction) ctx.lookup("txn:UserTransaction");


        String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote?stateful";
        HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
//        EJBClient.setStrongAffinity(bean, new ClusterAffinity("ejb"));
        EJBClient.setStrongAffinity(bean, new NodeAffinity("node2"));
        System.out.println("Lookup successful: remoteStatefulSB: " + bean.toString());
        // FIXME this is needed to get clustering working in DR16 with stateful beans...
        // it's not needed for stateless beans or if not invoking a cluster
//        EJBClient.setStrongAffinity(bean, new ClusterAffinity("ejb"));


        try {

            for(int i = 0; i<10; i++) {
                tx.begin();
//                System.out.println(bean.hello());
//                System.out.println(bean.getCounterAndIncrement());
                System.out.println(bean.hello());
                //tady fail serveru
                TimeUnit.SECONDS.sleep(10);

                tx.commit();


                tx.begin();
                //tady musi byt 2
//                System.out.println(bean.getCounterAndIncrement());
                System.out.println(bean.hello());
                tx.commit();

                TimeUnit.SECONDS.sleep(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
//        props.put(Context.PROVIDER_URL, "remote+http://127.0.0.1:8080,remote+http://127.0.0.1:8080");

        //TOTO MUSI BYT DEFINOVANE!!! Pokud chceme pouzivat UserTransaction!!! Nestaci pouzit wildfly-config.xml
        props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        return props;
    }

}
