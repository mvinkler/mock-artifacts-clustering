package client;

import java.security.PrivilegedActionException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.HelloBeanRemote;
import org.jboss.ejb.client.EJBClient;

/**
 * @author jmartisk
 */
public class Client {

    public static void main(String[] args) throws NamingException, PrivilegedActionException {
            try {
                InitialContext ctx = new InitialContext(getCtxProperties());
                String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote?stateful";
                HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
                EJBClient.setInvocationTimeout(bean, 5, TimeUnit.SECONDS);
                System.out.println(bean.hello());
                ctx.close();
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        return props;
    }

}
