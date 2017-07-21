package com.netflix.blitz4j;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class LoggingConfigurationTest {
    @Test
    public void updateToDifferentConfigurationTriggersRefresh() {
        LoggingConfiguration config = new LoggingConfiguration();
        config.configure(new Properties());
        
        Properties newProps = new Properties();
        newProps.setProperty("log4j.logger.foo", "INFO");
        newProps.setProperty("log4j.logger.bar", "INFO");

        Assert.assertEquals(0, config.getRefreshCount());
        
        config.reconfigure(newProps);
        Assert.assertEquals(1, config.getRefreshCount());
        Assert.assertEquals(newProps, config.getOverrideProperties());
        
        config.reconfigure(newProps);
        Assert.assertEquals(1, config.getRefreshCount());
        Assert.assertEquals(newProps, config.getOverrideProperties());
    }
    
    @Test
    public void updateWithSameConfigurationDoesNotTriggerRegresh() {
        Properties props = new Properties();
        props.setProperty("log4j.logger.foo", "INFO");
        props.setProperty("log4j.logger.bar", "INFO");
        
        LoggingConfiguration config = new LoggingConfiguration();
        config.configure(props);     
        
        Assert.assertEquals(0, config.getRefreshCount());
        
        config.reconfigure(props);
        Assert.assertEquals(0, config.getRefreshCount());
    }
    
    @Test
    public void updateWithSameConfigurationAndExistingOverridesDoesNotTriggerRegresh() {
        Properties props = new Properties();
        props.setProperty("log4j.logger.foo", "INFO");
        
        LoggingConfiguration config = new LoggingConfiguration();
        config.configure(props);     
        
        Assert.assertEquals(0, config.getRefreshCount());
        
        config.setProperty(null, "log4j.logger.bar", "INFO", false);
        
        Assert.assertEquals(1, config.getRefreshCount());
        
        Properties fullProperties = new Properties();
        fullProperties.setProperty("log4j.logger.foo", "INFO");
        fullProperties.setProperty("log4j.logger.bar", "INFO");
        config.reconfigure(fullProperties);     
        
        Assert.assertEquals(1, config.getRefreshCount());
    }
}
