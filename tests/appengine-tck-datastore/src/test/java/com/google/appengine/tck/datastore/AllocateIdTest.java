package com.google.appengine.tck.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService.KeyRangeState;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.KeyRange;
import com.google.appengine.api.datastore.Query;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * datastore allocating test.
 *
 * @author hchen@google.com (Hannah Chen)
 */
@RunWith(Arquillian.class)
public class AllocateIdTest extends DatastoreTestBase {
    private String pKind = "parent";
    private String cKind = "child";
    private long allocateNum = 5;

    @Test
    public void testAllocateParent() {
        KeyRange range = service.allocateIds(pKind, allocateNum);
        check(pKind, range);
    }

    @Test
    public void testAllocateChild() {
        Entity parent = new Entity(pKind);
        parent.setProperty("name", "parent" + new Date());
        Key pKey = service.put(parent);
        KeyRange range = service.allocateIds(pKey, cKind, allocateNum);
        check(cKind, range);
        Entity child = new Entity(range.getStart());
        child.setProperty("name", "second" + new Date());
        Key ckey = service.put(child);
        // child with allocated key should have correct parent.
        assertEquals(pKey, ckey.getParent());
    }

    @Test
    public void testAllocateRange() {
        Query query = new Query(cKind);
        for (Entity readRec : service.prepare(query).asIterable()) {
            service.delete(readRec.getKey());
        }
        Entity entity = new Entity(cKind);
        entity.setProperty("name", "exist" + new Date());
        Key key = service.put(entity);
        // entities with keys inside range already exist
        KeyRange keyRange = new KeyRange(null, cKind, key.getId(), key.getId());
        assertEquals(KeyRangeState.COLLISION, service.allocateIdRange(keyRange));
        // entities with keys inside range is empty.
        keyRange = new KeyRange(null, cKind, key.getId() + 1, key.getId() + allocateNum);
        KeyRangeState retStatus = service.allocateIdRange(keyRange);
        assertTrue((retStatus == KeyRangeState.CONTENTION) || (retStatus == KeyRangeState.EMPTY));
        // request again with same range
        assertEquals(KeyRangeState.CONTENTION, service.allocateIdRange(keyRange));
        // entities with keys inside range already exist
        entity = new Entity(KeyFactory.createKey(cKind, key.getId() + 1));
        entity.setProperty("name", "exist" + new Date());
        service.put(entity);
        assertEquals(KeyRangeState.COLLISION, service.allocateIdRange(keyRange));
    }

    private void check(String kind, KeyRange range) {
        Entity entity = new Entity(kind);
        entity.setProperty("name", "first" + new Date());
        Key key = service.put(entity);
        // allocated key should not be re-used.
        assertFalse(key.equals(range.getStart()));
        assertFalse(key.equals(range.getEnd()));
        assertEquals(allocateNum, range.getSize());
        for (Object aRange : range) {
            assertFalse(key.equals(aRange));
        }
    }
}
