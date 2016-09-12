package com.loffa.ofstest.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class PSRHealthChecker extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        // Put in to silence warning on start up
        return Result.healthy();
    }
}
