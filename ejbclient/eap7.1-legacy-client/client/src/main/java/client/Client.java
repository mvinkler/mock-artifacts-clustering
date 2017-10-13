package client;

import java.util.Properties;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.HelloBeanRemote;

/**
 * @author mvinkler
 */
public class Client {

    public static void main(String[] args) throws NamingException {
        final Properties properties = new Properties();
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final InitialContext ctx = new InitialContext(properties);
        String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote?stateful";

        HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);


        while(true) {

            System.out.println("write \"exit\" to exit, pres \"ENTER\" to continue...");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();

            if (s.startsWith("exit")) {
                break;
            }

            try {
                System.out.println("=============");
                System.out.println(bean.hello());

            } catch (Exception e) {
                e.printStackTrace();
            }



        }

        ctx.close();
    }

}
