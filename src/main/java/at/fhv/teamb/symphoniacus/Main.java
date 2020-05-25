package at.fhv.teamb.symphoniacus;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main entry class for REST API.
 *
 * @author Valentin Goronjic
 */
//https://medium.com/@wakingrufus/minimal-java-rest-part-1-skeleton-w-grizzly-jersey-e596c36359c0

public class Main {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String BASE_URI = "http://0.0.0.0:9005/api/";

        HttpServer httpServer = GrizzlyHttpServerFactory
                .createHttpServer(
                        URI.create(BASE_URI),
                        new ResourceConfig().packages("at.fhv.teamb.symphoniacus.rest")
                );

        try {
            httpServer.start();
            LOG.info("Server running on {}", "http://0.0.0.0:9005/api/", BASE_URI);
            LOG.info("Symphoniacus API started.\nHit enter to stop it...");
            System.in.read();
        } catch (IOException e) {
            LOG.error("Error starting server: {}", e.getLocalizedMessage());
        }
    }
}
