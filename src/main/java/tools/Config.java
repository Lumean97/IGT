package tools;


public class Config {

    public static final Integer NUMBER_OF_CUSTOMERS = 40;



    public static final String PERSISTENCE_UNIT_NAME = PERSISTENCE_UNITS.OGM_CASSANDRA.name();
    public enum PERSISTENCE_UNITS {
        OGM_MYSQL, OGM_POSTGRESQL, OGM_MONGODB, OGM_NEO4J, OGM_INFINISPAN, OGM_CASSANDRA, OGM_REDIS,
    }

}
