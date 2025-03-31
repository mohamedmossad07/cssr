package org.exalt.cssr.config;

import com.aerospike.client.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.aerospike.config.AbstractAerospikeDataConfiguration;
import org.springframework.data.aerospike.repository.config.EnableAerospikeRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableAerospikeRepositories(basePackages = {"org.exalt.cssr"})
public class AerospikeConfiguration extends AbstractAerospikeDataConfiguration {
    @Value("${app.aerospike.host}")
    private String aerospikeHost;
    @Value("${app.aerospike.port}")
    private Integer aerospikePort;
    @Value("${app.aerospike.namespace}")
    private String aerospikeNamespace;

    @Override
    protected Collection<Host> getHosts() {
        return Collections.singleton(new Host(aerospikeHost, aerospikePort));
    }

    @Override
    protected String nameSpace() {
        return aerospikeNamespace;
    }
}