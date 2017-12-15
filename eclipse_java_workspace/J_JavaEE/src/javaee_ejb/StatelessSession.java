
package javaee_ejb;

import javax.ejb.Remote;

@Remote
public interface StatelessSession {
    public String hello();

}
