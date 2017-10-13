package client;

import java.security.PrivilegedActionException;
import java.security.Provider;
import java.security.Security;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.wildfly.naming.client.WildFlyInitialContextFactory;

import ejb.HelloBeanRemote;

import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;

/**
 * @author jmartisk
 */
public class ClientJmartisk {

    public static void main(String[] args)
            throws NamingException, PrivilegedActionException, InterruptedException, SystemException,
            NotSupportedException {

//        AuthenticationConfiguration common = AuthenticationConfiguration.EMPTY
//                .useProviders(() -> new Provider[] {new WildFlyElytronProvider()})
//                .allowAllSaslMechanisms();
//        AuthenticationContext authCtxEmpty = AuthenticationContext.empty();
//        final AuthenticationContext authCtx = authCtxEmpty.with(MatchRule.ALL, common);
//
//        AuthenticationContext.getContextManager().setThreadDefault(authCtx);

        Security.addProvider(new WildFlyElytronProvider());  // FIXME this is a workaround for JBEAP-10167 and should not be needed
        InitialContext ctx = new InitialContext(getCtxProperties());
        final UserTransaction tx = (UserTransaction)ctx.lookup("txn:UserTransaction");
        tx.begin();
        String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote";
        HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
        System.out.println(bean.hello());
        ctx.close();
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, "remote+http://127.0.0.1:8080");
        props.put(Context.SECURITY_PRINCIPAL, "joe");
        props.put(Context.SECURITY_CREDENTIALS, "joeIsAwesome2013!");
        return props;
    }

}