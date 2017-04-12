package ejb;

import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.SecurityDomain;

/**
 * @author jmartisk
 * @since 7/3/13
 */
@Stateful
public class HelloBean implements HelloBeanRemote {

    private AtomicLong counter = new AtomicLong(0);

    @Resource
    SessionContext ctx;

    public HelloBean() {
    }

    @Override
    public String hello() {
        final long newValue = counter.getAndAdd(1);
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("method hello() invoked by user " + ctx.getCallerPrincipal().getName() +
                ", counter = " + newValue);
        return System.getProperty("jboss.node.name") + ", " + newValue;
    }

}
