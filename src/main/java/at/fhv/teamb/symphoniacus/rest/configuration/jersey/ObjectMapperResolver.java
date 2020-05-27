package at.fhv.teamb.symphoniacus.rest.configuration.jersey;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Custom JSON Mapper.
 * @author Valentin Goronjic
 */
@Provider
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {
    private final ObjectMapper mapper;

    /**
     * Constructs a new custom JSON Mapper.
     */
    public ObjectMapperResolver() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
