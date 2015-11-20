package mik;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * Created by mikitjuk on 18.11.15.
 */
public class InMemoryData {
    private static HazelcastInstance instance = Hazelcast.newHazelcastInstance();

    public static HazelcastInstance getInstance(){
        return instance;
    }
}
