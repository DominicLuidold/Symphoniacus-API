package at.fhv.teamb.symphoniacus.rest.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Call to help with handling Jsons.
 *
 * @author Tobias Moser
 **/
public class JsonHelper {
    private static final Logger LOG = LogManager.getLogger(JsonHelper.class);

    /**
     * Helper function to converting a Java object to a Json object.
     * @param o which will be converted
     * @return Json representation of the given Object
     */
    public static String toJson(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return  mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            LOG.error(e);
        }
        return null;
    }
}
