package client;

import java.security.PrivilegedActionException;
import java.security.Provider;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.UUID;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.*;
import org.jboss.test.clusterbench.ejb.stateful.RemoteStatefulSB;
import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;

/**
 * @author mvinkler
 */
public class Client {

    public static void main(String[] args) throws NamingException, PrivilegedActionException {

        Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");


        AuthenticationConfiguration common = AuthenticationConfiguration.EMPTY
                .useProviders(() -> new Provider[] {new WildFlyElytronProvider()})
                .allowAllSaslMechanisms();
        AuthenticationContext authCtxEmpty = AuthenticationContext.empty();
        final AuthenticationContext authCtx = authCtxEmpty.with(MatchRule.ALL, common);
        AuthenticationContext.getContextManager().setThreadDefault(authCtx);

        Context context = new InitialContext(jndiProperties);

//        String beanName = "RemoteStatefulSBImpl";
//        String viewClassName = RemoteStatefulSB.class.getName();
//        String lookupString = "ejb:clusterbench-ee7/clusterbench-ee7-ejb//" + beanName + "!" + viewClassName + "?stateful";
//        System.out.println("lookup string: " + lookupString);

//        RemoteStatefulSB remoteStatefulSB = (RemoteStatefulSB) context.lookup(lookupString);

        //TODO: use StatefulEJBLocator or StatelessEJBLocator?
//        StatefulEJBLocator<RemoteStatefulSB> ejbLocator = StatefulEJBLocator.create(
//                RemoteStatefulSB.class,
//                new EJBIdentifier("clusterbench-ee7", "clusterbench-ee7-ejb", "RemoteStatefulSBImpl", ""),
//                new UUIDSessionID(UUID.randomUUID()),
//                new ClusterAffinity("ejb")
//        );

        StatelessEJBLocator<RemoteStatefulSB> ejbLocator = StatelessEJBLocator.create(
                RemoteStatefulSB.class,
                new EJBIdentifier("clusterbench-ee7", "clusterbench-ee7-ejb", "RemoteStatefulSBImpl", ""),
                new ClusterAffinity("ejb")
        );

        System.out.println("ejbLocator: " + ejbLocator.toString());

        RemoteStatefulSB remoteStatefulSB = EJBClient.createProxy(ejbLocator);

        System.out.println("remoteStatefulSB: " + remoteStatefulSB.toString());

        promptEnterKey(remoteStatefulSB);

        context.close();

    }

    private static void promptEnterKey(RemoteStatefulSB remoteStatefulSB) {

        while(true) {

            System.out.println("write \"exit\" to exit, pres \"ENTER\" to continue...");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();

            if (s.startsWith("exit")) {
                return;
            }

            System.out.println(remoteStatefulSB.getSerialAndIncrement());

        }
    }



}
