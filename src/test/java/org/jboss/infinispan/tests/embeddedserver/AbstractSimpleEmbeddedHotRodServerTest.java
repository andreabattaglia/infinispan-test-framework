package org.jboss.infinispan.tests.embeddedserver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.apache.log4j.Logger;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.protostream.annotations.ProtoSchemaBuilderException;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class AbstractSimpleEmbeddedHotRodServerTest {
    /**
     * Logger for this class
     */
    private static final Logger LOGGER = Logger
            .getLogger(AbstractSimpleEmbeddedHotRodServerTest.class);

    protected final static Random RANDOM = new Random();
    protected final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat();

    private RemoteCacheManager remoteCacheManager;
    private ConfigurationBuilder configurationBuilder;
    protected QueryFactory<Query> queryFactory;

    @BeforeClass
    public static void beforeClass() throws Exception {
        SimpleEmbeddedHotRodServer.getInstance().startup();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        SimpleEmbeddedHotRodServer.getInstance().shutdown();
    }

    @Before
    public void setUp() throws Exception {

        configurationBuilder = new ConfigurationBuilder();
        configurationBuilder
                .addServers(System.getProperty("datagrid.host", "localhost")
                        + ":" + System.getProperty("datagrid.port", "11422"))
                // .nearCache().maxEntries(-1).mode(NearCacheMode.EAGER)
                .marshaller(ProtoStreamMarshaller.class).maxRetries(100);

        Configuration config = configurationBuilder.build();
        remoteCacheManager = new RemoteCacheManager(config);
        remoteCacheManager.start();

    }

    @After
    public void tearDown() throws Exception {
        remoteCacheManager.stop();
    }

    protected RemoteCacheManager getRemoteCacheManager() {
        return remoteCacheManager;
    }

    protected void registerType(Class<?> type) {
        configureProtobufMarshaller(type);
    }

    protected void configureProtobufMarshaller(Class<?> indexableClass) {

        SerializationContext serCtx = ProtoStreamMarshaller
                .getSerializationContext(remoteCacheManager);

        // generate and register a Protobuf schema and marshallers based
        // on Note class and the referenced classes (User class)
        ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
        String generatedSchema;
        try {
            /*
             * generate the 'history.proto' schema file based on the annotations
             * on inexableClass class and register it with the
             * SerializationContext of the client
             */
            String metadataClassName = indexableClass.getSimpleName()
                    + ".proto";
            generatedSchema = protoSchemaBuilder.fileName(metadataClassName)//
                    .packageName(indexableClass.getPackage().getName())//
                    .addClass(indexableClass)//
                    .build(serCtx);

            LOGGER.debug("The proto schema for entity "
                    + indexableClass.getSimpleName() + "(" + metadataClassName
                    + ")" + "\n\n" + generatedSchema + "\n\n");

            // register the schemas with the server too
            RemoteCache<String, String> metadataCache = remoteCacheManager
                    .getCache(
                            ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
            metadataCache.putIfAbsent(metadataClassName, generatedSchema);

            String errors = metadataCache
                    .get(ProtobufMetadataManagerConstants.ERRORS_KEY_SUFFIX);
            if (errors != null) {
                throw new IllegalStateException(
                        "Some Protobuf schema files contain errors:\n"
                                + errors);
            }

        } catch (ProtoSchemaBuilderException | IOException e) {
            throw new RuntimeException(
                    "An error occurred initializing HotRod protobuf marshaller:",
                    e);
        }
    }
}
