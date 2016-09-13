package com.loffa.ofstest;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.loffa.ofstest.api.PersistenceService;
import com.loffa.ofstest.health.PSRHealthChecker;
import com.loffa.ofstest.resources.GameController;
import com.loffa.ofstest.resources.PlayerController;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumSet;

public class PaperScissorsRock extends Application<PlayerScissorsRockConfiguration> {



    public static void main(final String[] args) throws Exception {
        new PaperScissorsRock().run(args);
    }

    @Override
    public String getName() {
        return "osftest";
    }

    @Override
    public void initialize(final Bootstrap<PlayerScissorsRockConfiguration> bootstrap) {
        // TODO: application initialization

        //bootstrap.addBundle(new AssetsBundle());

        // load the persistence data if required.
        bootstrap.addBundle(new ViewBundle<PlayerScissorsRockConfiguration>());
        bootstrap.addBundle(new AssetsBundle());

        PersistenceService.getInstance().loadFromDefaultFile();
    }

    @Override
    public void run(final PlayerScissorsRockConfiguration configuration,
                    final Environment environment) {

//        FilterRegistration.Dynamic filter = environment.servlets().addFilter("crossOriginRequests", CrossOriginFilter.class);
//        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        PSRHealthChecker psrHealth = new PSRHealthChecker();
        environment.healthChecks().register("psr-health", psrHealth);

        environment.getObjectMapper().registerModule(new JodaModule());
        environment.jersey().register(new PlayerController());
        environment.jersey().register(new GameController());

    }

}
