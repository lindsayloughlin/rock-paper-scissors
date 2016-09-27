package com.loffa.ofstest;

import com.fasterxml.jackson.annotation.JsonProperty;
import freemarker.template.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.activation.DataSource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.skife.jdbi.v2.DBI;

/**
 * Created by lloughlin on 22/09/2016.
 */
public class ServerConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }
}