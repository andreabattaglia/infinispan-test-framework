package org.jboss.infinispan.tests.embeddedserver;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.server.hotrod.HotRodServer;
import org.infinispan.server.hotrod.configuration.HotRodServerConfiguration;
import org.infinispan.server.hotrod.configuration.HotRodServerConfigurationBuilder;

public class SimpleEmbeddedHotRodServer {
    /**
     * Logger for this class
     */
    private static final Logger LOGGER = Logger
            .getLogger(AbstractSimpleEmbeddedHotRodServerTest.class);

    private static SimpleEmbeddedHotRodServer instance;
    // private org.infinispan.configuration.cache.ConfigurationBuilder
    // embeddedBuilder;
    private HotRodServerConfiguration build;
    private DefaultCacheManager defaultCacheManager;
    private HotRodServer server;

    // static{
    // MDC.put("COMPONENT", "[SERVER]");
    // }

    public static synchronized SimpleEmbeddedHotRodServer getInstance() {
        if (instance == null)
            instance = new SimpleEmbeddedHotRodServer();
        return instance;
    }

    private SimpleEmbeddedHotRodServer() {
        /*
         * JDG EMBEDDED
         */
        /*
         * Use the following for XML configuration InputStream is =
         * SimpleEmbeddedHotRodServer.class.getResourceAsStream(
         * "/infinispan.xml"); DefaultCacheManager defaultCacheManager = new
         * DefaultCacheManager(is);
         */
        // embeddedBuilder = new
        // org.infinispan.configuration.cache.ConfigurationBuilder();
        // embeddedBuilder.dataContainer()
        // .keyEquivalence(new AnyServerEquivalence())
        // .valueEquivalence(new AnyServerEquivalence()).compatibility()
        // .enable();
        // embeddedBuilder.indexing().enable();
        /*
         * HOTROD EMBEDDED
         */
        build = new HotRodServerConfigurationBuilder().port(11422).build();
    }

    public void startup() {
        // defaultCacheManager = new
        // DefaultCacheManager(embeddedBuilder.build());

        try (InputStream is = SimpleEmbeddedHotRodServer.class
                .getResourceAsStream(getConfigFileLocation());) {
            defaultCacheManager = new DefaultCacheManager(is);
        } catch (IOException e) {
            LOGGER.error("Infinispan Server configuration file ("
                    + getConfigFileLocation() + ") not found. Exiting.");
            System.exit(-1);
        }
        server = new HotRodServer();
        server.start(build, defaultCacheManager);
    }

    private String getConfigFileLocation() {
        return "/infinispan.xml";
    }

    public void shutdown() {
        server.stop();
        defaultCacheManager.stop();
    }
}