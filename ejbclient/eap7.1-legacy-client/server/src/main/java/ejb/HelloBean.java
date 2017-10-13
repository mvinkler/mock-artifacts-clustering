package ejb;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.SecurityDomain;

@Stateful
//@SecurityDomain("other")
public class HelloBean implements HelloBeanRemote {

    @Resource
    SessionContext ctx;

    public HelloBean() {
    }

    @Override
    //@RolesAllowed("users")
    public String hello() {
        System.out.println("method hello() invoked by user " + ctx.getCallerPrincipal().getName() + " on node " + System.getProperty("jboss.node.name"));
        return "method hello() invoked by user " + ctx.getCallerPrincipal().getName() + " on node " + System.getProperty("jboss.node.name");
    }

}
