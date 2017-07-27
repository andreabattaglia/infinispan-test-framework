package org.jboss.infinispan.tests.embeddedserver.test1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Expression;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryBuilder;
import org.infinispan.query.dsl.SortOrder;
import org.jboss.infinispan.tests.embeddedserver.AbstractSimpleEmbeddedHotRodServerTest;
import org.junit.Test;

public class BasicQueryTest extends AbstractSimpleEmbeddedHotRodServerTest {
    /**
     * Logger for this class
     */
    private static final Logger LOGGER = Logger.getLogger(BasicQueryTest.class);

    private RemoteCache<Integer, MyEntity> testCache;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        registerType(MyEntity.class);
        testCache = getRemoteCacheManager().getCache("TESTCACHE", false);
        testCache.clear();
        queryFactory = Search.getQueryFactory(testCache);
    }

    @Override
    public void tearDown() throws Exception {
        testCache.clear();
        super.tearDown();
    }

    private void prefillCache(RemoteCache<Integer, MyEntity> testCache,
            int entitiesAmount) {
        MyEntity myEntity = null;
        for (int i = 0; i < entitiesAmount; i++) {
            myEntity = new MyEntity();
            myEntity.setId(i);
            myEntity.setMyRefId(Integer.toString(i));
            // myEntity.setMyRefId(UUID.randomUUID().toString());
            myEntity.setMyDate(new Date(RANDOM.nextLong()));
            myEntity.setMyCollection(
                    Arrays.asList(Integer.toString(i), myEntity.getMyRefId(),
                            DATE_FORMAT.format(myEntity.getMyDate())));
            testCache.put(i, myEntity);

        }

    }

    private void queryCache(RemoteCache<Integer, MyEntity> testCache,
            String uniqueValue) {
        queryFactory = Search.getQueryFactory(testCache);

        Query query = null;
        QueryBuilder<Query> qb = queryFactory.from(MyEntity.class)//
                // .setProjection("id")//
                .orderBy("id", SortOrder.ASC).having("guid").like(uniqueValue)//
                .toBuilder();

        query = qb.build();
        List<MyEntity> results = query.list();
        assertNotNull(results);
        LOGGER.info("Infinispan query resulset: " + results);
        MyEntity myEntity = results.get(0);
        assertNotNull(myEntity);
        assertEquals(6, myEntity.getId());
        LOGGER.info("The entity corresponding to the unique guid \""
                + uniqueValue + "\" is: " + myEntity);
    }

    private void queryCacheWithProjection(
            RemoteCache<Integer, MyEntity> testCache, String uniqueValue) {
        queryFactory = Search.getQueryFactory(testCache);

        Query query = null;
        QueryBuilder<Query> qb = queryFactory.from(MyEntity.class)//
                .select("id")//
                .orderBy("id", SortOrder.ASC)//
                .having("guid").eq(uniqueValue)//
                .toBuilder();

        query = qb.build();
        List<Object[]> results = query.list();
        assertNotNull(results);
        LOGGER.info("Infinispan query resulset: " + results);
        int myEntityId = (Integer) results.get(0)[0];
        assertNotNull(myEntityId);
        assertEquals(6, myEntityId);
        LOGGER.info("The entity id corresponding to the unique guid \""
                + uniqueValue + "\" is: " + myEntityId);
    }

    @Test
    public void test() {
        assertTrue(testCache != null);
        System.out.println("Retrieved cache by name: " + testCache.getName());
        prefillCache(testCache, 10);
        int testCacheSize = testCache.size();
        LOGGER.info(
                "After prefill action, the cache size is: " + testCacheSize);
        assertEquals(10, testCacheSize);
        System.out.println(testCache.getBulk());
        MyEntity myEntity6 = testCache.get(new Integer(6));
        assertNotNull(myEntity6);
        LOGGER.info("My Entity #6 is:\n" + myEntity6);
        queryCache(testCache, myEntity6.getMyRefId());
        queryCacheWithProjection(testCache, myEntity6.getMyRefId());
    }
}
