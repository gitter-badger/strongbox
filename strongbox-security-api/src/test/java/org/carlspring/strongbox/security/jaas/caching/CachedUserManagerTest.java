package org.carlspring.strongbox.security.jaas.caching;

import org.carlspring.strongbox.security.jaas.Credentials;
import org.carlspring.strongbox.security.jaas.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;

/**
 * @author mtodorov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/strongbox-*-context.xml", "classpath*:/META-INF/spring/strongbox-*-context.xml"})
public class CachedUserManagerTest
{


    @Test
    public void testCredentialsManagement()
            throws Exception
    {
        CachedUserManager manager = new CachedUserManager();
        manager.setCredentialsLifetime(5000l);
        manager.setCredentialExpiredCheckInterval(500l);
        manager.startMonitor();

        manager.addUser(new User("user1", new Credentials("password")));
        manager.addUser(new User("user2", new Credentials("password")));
        manager.addUser(new User("user3", new Credentials("password")));
        manager.addUser(new User("user4", new Credentials("password")));
        manager.addUser(new User("user5", new Credentials("password")));

        Thread.sleep(3000l);

        manager.validCredentials("user3", "password");
        manager.validCredentials("user5", "password");

        Thread.sleep(4000);

        assertEquals("Failed to expire users from cache!", 2, manager.getSize());
    }

}
