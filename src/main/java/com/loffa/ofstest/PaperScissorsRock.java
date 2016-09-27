package com.loffa.ofstest;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.loffa.ofstest.api.GameResultService;
import com.loffa.ofstest.api.PersistenceService;
import com.loffa.ofstest.api.PlayerService;
import com.loffa.ofstest.auth.UserAuthenticator;
import com.loffa.ofstest.auth.UserAuthorizer;
import com.loffa.ofstest.core.User;
import com.loffa.ofstest.dao.GameDao;
import com.loffa.ofstest.dao.PlayerDao;
import com.loffa.ofstest.health.PSRHealthChecker;
import io.dropwizard.auth.AuthDynamicFeature;
import com.loffa.ofstest.resources.GameController;
import com.loffa.ofstest.resources.PlayerController;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.skife.jdbi.v2.DBI;

public class PaperScissorsRock extends Application<PlayerScissorsRockConfiguration> {



    public static void main(final String[] args) throws Exception {
        new PaperScissorsRock().run(args);
    }

    @Override
    public String getName() {
        return "ofstest";
    }

    @Override
    public void initialize(final Bootstrap<PlayerScissorsRockConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<PlayerScissorsRockConfiguration>());
        bootstrap.addBundle(new AssetsBundle());


    }

//    private final HibernateBundle<ServerConfiguration> hibernate = new HibernateBundle<>(Person.class) {
//        @Override
//        public DataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
//            return configuration.getDataSourceFactory();
//        }
//    };


    @Override
    public void run(final PlayerScissorsRockConfiguration configuration,
                    final Environment environment) {

        PSRHealthChecker psrHealth = new PSRHealthChecker();
        environment.healthChecks().register("psr-health", psrHealth);

        JodaModule jodaModule = new JodaModule();
        environment.getObjectMapper().registerModule(jodaModule);
        environment.getObjectMapper().configure(com.fasterxml.jackson.databind.SerializationFeature.
                WRITE_DATES_AS_TIMESTAMPS , false);

        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new UserAuthenticator())
                .setAuthorizer(new UserAuthorizer())
                .setRealm("Authorized player section")
                .buildAuthFilter()));

        final DBIFactory factory = new DBIFactory();

        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        final PlayerDao playerDao = jdbi.onDemand(PlayerDao.class);
        final GameDao gameDao = jdbi.onDemand(GameDao.class);
        //final PersonResource personResource = new PersonResource(personDAO);


        PersistenceService instance = new PersistenceService(environment.getObjectMapper());
        instance.loadDataFromJson();

        PlayerService playerService = new PlayerService(playerDao);
        environment.jersey().register(new PlayerController(playerService));
        environment.jersey().register(new GameController(new GameResultService(gameDao), playerService));
    }

}
