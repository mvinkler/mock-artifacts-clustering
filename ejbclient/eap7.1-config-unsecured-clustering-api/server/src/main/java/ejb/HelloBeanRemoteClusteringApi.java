package ejb;

import javax.ejb.Remote;

/**
 * @author jmartisk
 * @since 7/3/13
 */
@Remote
public interface HelloBeanRemoteClusteringApi {

    public String hello();

    public String getNodeName();

    public int getCounterAndIncrement();

}


