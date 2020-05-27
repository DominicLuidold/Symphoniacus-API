package at.fhv.teamb.symphoniacus.rest;

import java.io.IOException;
import java.net.URI;

import at.fhv.teamb.symphoniacus.rest.configuration.jersey.ObjectMapperResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main entry class for REST API.
 * Help from
 * https://medium.com/@wakingrufus/minimal-java-rest-part-1-skeleton-w-grizzly-jersey-e596c36359c0
 * @author Valentin Goronjic
 */
public class Main {

    private static final Logger LOG = LogManager.getLogger(Main.class);
    private static final String BASE_URI = "http://0.0.0.0:9005/api/";

    /**
     * Starts up the REST API.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        ResourceConfig rc = new ResourceConfig();
        rc.register(JacksonFeature.class);
        rc.register(ObjectMapperResolver.class);
        rc.packages("at.fhv.teamb.symphoniacus.rest");

        HttpServer httpServer = GrizzlyHttpServerFactory
            .createHttpServer(
                URI.create(BASE_URI),
                rc
            );

        try {
            httpServer.start();
            LOG.info("Server running on {}", BASE_URI);
            LOG.info("Symphoniacus API started.\nHit enter to stop it...");
            System.in.read();
        } catch (IOException e) {
            LOG.error("Error starting server: {}", e.getLocalizedMessage());
        }
    }
}
