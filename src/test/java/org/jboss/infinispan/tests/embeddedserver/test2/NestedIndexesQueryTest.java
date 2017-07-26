package org.jboss.infinispan.tests.embeddedserver.test2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryBuilder;
import org.jboss.infinispan.tests.embeddedserver.AbstractSimpleEmbeddedHotRodServerTest;
import org.junit.Test;

/**
 * The Class NestedIndexesQueryTest.
 * 
 * @author Andrea Battaglia (Red Hat)
 */
public class NestedIndexesQueryTest
        extends AbstractSimpleEmbeddedHotRodServerTest {

    /** Logger for this class. */
    private static final Logger LOGGER = Logger
            .getLogger(NestedIndexesQueryTest.class);

    /** The my cache. */
    private RemoteCache<Long, Grade> testCache;

    /**
     * @see org.jboss.infinispan.tests.embeddedserver.AbstractSimpleEmbeddedHotRodServerTest#setUp()
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        registerType(Grade.class);
        testCache = getRemoteCacheManager().getCache("TESTCACHE", false);
        testCache.clear();
        queryFactory = Search.getQueryFactory(testCache);
    }
    
    @Override
    public void tearDown() throws Exception {
        testCache.clear();
        super.tearDown();
    }

    /**
     * Test.
     */
    @Test
    public void test() {
        assertTrue(testCache != null);
        LOGGER.info("Retrieved cache by name: " + testCache.getName());
        testCache.putAll(getData());
        int myCacheSize = testCache.size();
        LOGGER.info("After prefill action, the cache size is: " + myCacheSize);
        assertEquals(2, myCacheSize);
        Grade grade1 = testCache.get(1L);
        LOGGER.info("Grade #1 is: " + grade1);

        Query query = null;
        QueryBuilder<Query> qb = queryFactory.from(Grade.class)
                // OK
                // .setProjection("ref").having("name").eq("Grade1")
                // OK
                // .having("name").eq("Grade1")
                // OK
                // .having("forwardsGrades.dealType.name").eq("DealType3")
                // OK
                // .setProjection("ref").having("forwardsGrades.dealType.name").eq("DealType3")

                .setProjection("forwardsGrades")
                .having("forwardsGrades.dealType.name").eq("DealType3")
                .toBuilder();
        query = qb.build();
        List<Object[]> results = query.list();
        LOGGER.info("Query result is: " + results);
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    private Map<Long, Grade> getData() {
        Map<Long, Grade> map = new HashMap<>();
        DealType dealType1 = createDealType(1, "DealType1", "DealType1");
        DealType dealType2 = createDealType(2, "DealType2", "DealType2");
        DealType dealType3 = createDealType(3, "DealType3", "DealType3");
        DealType dealType4 = createDealType(4, "DealType4", "DealType4");
        ForwardsGrade forwardsGrade1 = createForwardsGrade(1, "ForwardsGrade1",
                "ForwardsGrade1", dealType1);
        ForwardsGrade forwardsGrade2 = createForwardsGrade(2, "ForwardsGrade2",
                "ForwardsGrade2", dealType2);
        Grade grade1 = createGrade(1, "Grade1", "Grade1", forwardsGrade1,
                forwardsGrade2);
        map.put(grade1.getId(), grade1);
        ForwardsGrade forwardsGrade3 = createForwardsGrade(3, "ForwardsGrade3",
                "ForwardsGrade3", dealType3);
        ForwardsGrade forwardsGrade4 = createForwardsGrade(4, "ForwardsGrade4",
                "ForwardsGrade4", dealType4);
        Grade grade2 = createGrade(2, "Grade2", "Grade2", forwardsGrade3,
                forwardsGrade4);
        map.put(grade2.getId(), grade2);

        return map;
    }

    /**
     * Creates the grade.
     *
     * @param id
     *            the id
     * @param ref
     *            the ref
     * @param name
     *            the name
     * @param forwardsGrades
     *            the forwards grades
     * @return the grade
     */
    private Grade createGrade(long id, String ref, String name,
            ForwardsGrade... forwardsGrades) {
        Grade grade = new Grade();
        grade.setId(id);
        grade.setRef(ref);
        grade.setName(name);
        grade.setForwardsGrades(new HashSet<>(Arrays.asList(forwardsGrades)));
        return grade;
    }

    /**
     * Creates the forwards grade.
     *
     * @param id
     *            the id
     * @param ref
     *            the ref
     * @param name
     *            the name
     * @param dealType
     *            the deal type
     * @return the forwards grade
     */
    private ForwardsGrade createForwardsGrade(long id, String ref, String name,
            DealType dealType) {
        ForwardsGrade forwardsGrade = new ForwardsGrade();
        forwardsGrade.setId(id);
        forwardsGrade.setRef(ref);
        forwardsGrade.setName(name);
        forwardsGrade.setDealType(dealType);
        return forwardsGrade;
    }

    /**
     * Creates the deal type.
     *
     * @param id
     *            the id
     * @param ref
     *            the ref
     * @param name
     *            the name
     * @return the deal type
     */
    private DealType createDealType(long id, String ref, String name) {
        DealType dealType = new DealType();
        dealType.setId(id);
        dealType.setRef(ref);
        dealType.setName(name);
        return dealType;
    }

}
