package net.binout.cdi11.veto;

import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.util.Set;

/**
 * User: binout
 * Date: 29/06/13
 */
@RunWith(Arquillian.class)
public class VetoedTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(NotManagedByCDI.class, ManagedByCDI.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private BeanManager beanManager;

    @Test
    public void bean_found_in_container()  {
        Set<Bean<?>> beans = beanManager.getBeans(ManagedByCDI.class);
        Assert.assertTrue(beans.size() == 1);
    }

    @Test
    public void vetoed_bean_not_found_in_container()  {
        Set<Bean<?>> beans = beanManager.getBeans(NotManagedByCDI.class);
        Assert.assertTrue(beans.size() == 0);
    }
}
