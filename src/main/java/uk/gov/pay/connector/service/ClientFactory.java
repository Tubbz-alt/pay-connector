package uk.gov.pay.connector.service;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.client.proxy.ProxyConfiguration;
import io.dropwizard.setup.Environment;
import io.dropwizard.util.Duration;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientProperties;
import uk.gov.pay.connector.app.ConnectorConfiguration;
import uk.gov.pay.connector.app.OperationOverrides;
import uk.gov.pay.connector.filters.RestClientLoggingFilter;
import uk.gov.pay.connector.util.TrustStoreLoader;

import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.ws.rs.client.Client;

public class ClientFactory {
    private final Environment environment;
    private final ConnectorConfiguration conf;

    @Inject
    public ClientFactory(Environment environment, ConnectorConfiguration conf) {
        this.environment = environment;
        this.conf = conf;
    }

    public Client createWithDropwizardClient(String clientName, GatewayOperation operation) {
        JerseyClientConfiguration clientConfiguration = clientConfigurationWithOverrides(operation);
        JerseyClientBuilder defaultClientBuilder = new JerseyClientBuilder(environment)
                .using(new ApacheConnectorProvider())
                .using(clientConfiguration)
                .withProperty(ClientProperties.READ_TIMEOUT, getReadTimeoutInMillis(operation))
                .withProperty(ApacheClientProperties.CONNECTION_MANAGER, createConnectionManager());

        // optionally set proxy; see comment below why this has to be done
        if (conf.getCustomJerseyClient().isProxyEnabled()) {
            defaultClientBuilder
                .withProperty(ClientProperties.PROXY_URI, proxyUrl(clientConfiguration.getProxyConfiguration()));
        }

        Client client = defaultClientBuilder.build(clientName);
        client.register(RestClientLoggingFilter.class);
        return client;
    }

    private JerseyClientConfiguration clientConfigurationWithOverrides(GatewayOperation operation) {
        JerseyClientConfiguration clientConfiguration = conf.getClientConfiguration();

        if (getOverridesFor(operation) != null) {
            Duration timeout = getOverridesFor(operation).getTimeout();
            if (timeout != null) {
                clientConfiguration.setTimeout(timeout);
            }

            Duration connectionTimeout = getOverridesFor(operation).getConnectionTimeout();
            if (connectionTimeout != null) {
                clientConfiguration.setConnectionTimeout(connectionTimeout);
            }
        }

        return clientConfiguration;
    }

    private int getReadTimeoutInMillis(GatewayOperation operation) {
        OperationOverrides overrides = getOverridesFor(operation);
        if (overrides != null && overrides.getReadTimeout() != null) {
            return (int) overrides.getReadTimeout().toMilliseconds();
        }
        return (int) conf.getCustomJerseyClient().getReadTimeout().toMilliseconds();
    }

    private OperationOverrides getOverridesFor(GatewayOperation operation) {
        if (conf.getWorldpayConfig().getJerseyClientOverrides() != null) {
            return conf.getWorldpayConfig().getJerseyClientOverrides().getOverridesFor(operation);
        }
        return null;
    }

    private HttpClientConnectionManager createConnectionManager() {
        return new PoolingHttpClientConnectionManager(
            RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https",
                        new SSLConnectionSocketFactory(
                            SslConfigurator
                                .newInstance()
                                .trustStore(TrustStoreLoader.getTrustStore())
                                .createSSLContext(),
                            new String[] { "TLSv1.2" },
                            null,
                            (HostnameVerifier) null
                        )
                    )
                    .build(),
            new ManagedHttpClientConnectionFactory()
        );
    }

    /**
     * Constructs the proxy URL required by JerseyClient property ClientProperties.PROXY_URI
     * <p>
     * <b>NOTE:</b> The reason for doing this is, Dropwizard jersey client doesn't seem to work as per
     * http://www.dropwizard.io/0.9.2/docs/manual/configuration.html#proxy where just setting the proxy config in
     * client configuration is only needed. But after several test, that doesn't seem to work, but by setting the
     * native jersey proxy config as per this implementation seems to work
     * <p>
     * similar problem discussed in here -> https://groups.google.com/forum/#!topic/dropwizard-user/AbDSYfLB17M
     * </p>
     * </p>
     *
     * @param proxyConfig from config.yml
     * @return proxy server URL
     */
    private String proxyUrl(ProxyConfiguration proxyConfig) {
        return String.format("%s://%s:%s",
                proxyConfig.getScheme(),
                proxyConfig.getHost(),
                proxyConfig.getPort()
        );
    }

}

