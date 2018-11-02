package uk.gov.pay.connector.applepay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.pay.connector.app.ApplePayConfig;
import uk.gov.pay.connector.app.ConnectorConfiguration;
import uk.gov.pay.connector.app.WorldpayConfig;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplePayDecrypterTest {
    
    @Mock
    private ConnectorConfiguration mockConfig;
    @Mock
    private WorldpayConfig mockWorldpayConfig;
    @Mock
    private ApplePayConfig mockApplePayConfig;
    private ObjectMapper objectMapper = new ObjectMapper();
    
    
    private final String encodedData = "0cWHZj3Py20Nxh8VrANfcXSFFaaNCJM9HpCPiUhb63GWi7+Aya0BmsKfELoK/Pp+1hvmJzO0DtkdYrtLQRnKRKUMX+KfEsQO3eKLEOaRm12qf1jgXG7HUE1r+BlrK9BC24QzyZkqYVbdTSbE8CTmDuiDSVjQi0fBwxo+MdjsWg+ap6RDlmSXVKXuGS1to5Ae/VDnwBBMuDNYJiJYSR9LWU7eO6HL6ke6+xjXcRhfxexeZ1y9XToTcDrC0M7xM3kAHkTyDV30m63MKdb7cpSV/7DVgj99AX9XrLlVJndAnBLI7jMsOFCTho86U0fJJ40XDklR8X5x43NKL+c2SimUNBMZkiZLygQSUrFD41cKb/7UIyB9c7Sk9UJmTM24FOeVt/RH2cIX+okRB6UzewVGZEFvV/PWbJqaOCWxISMjJc8HAkWa0Q1ARVKTzCS6ZgsPFZcao0Z3/j46kCxN/RYeYG7hfgrtaH8hqnvicac3khhFAU9RbjMZCmGdVzyuaxz/4SKGtDgN22y8sPsiWORi6NM+1As5nHWMgP7dO2ouI3wcuaxHADHfGm5aNQ==";
    
    private final String encodedEphemeralKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEl/XAbOgrSCupps/QbIxJ3u4QZ1PlbO5uGDD1zj/JGMoephYSEgw+63gHQHekx3T8duXN3CoYafUpuQlwOeK6/w==";
    
    private final String encodedPrivateKey = "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgjyo3fzxT7j+CFxC7I4B5iVee2FUyn2vfOSjcgp2/g6qhRANCAARdoBFEtnuapXFKw4DYWsW0yV4bavpdWKszkefi19AhlIRE3WSNWSn25W5tZNFjMWtLISBmqANyufx2xP19oRvy";
    
    private final String encodedPublicCertificate = "MIIEeTCCBCCgAwIBAgIIUkIKsiYxaKowCgYIKoZIzj0EAwIwgYExOzA5BgNVBAMMMlRlc3QgQXBwbGUgV29ybGR3aWRlIERldmVsb3BlcnMgUmVsYXRpb25zIENBIC0gRUNDMSAwHgYDVQQLDBdDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTETMBEGA1UECgwKQXBwbGUgSW5jLjELMAkGA1UEBhMCVVMwHhcNMTYwNzI1MTUyMDM4WhcNMTgwODI0MTUyMDM4WjCBlzErMCkGCgmSJomT8ixkAQEMG21lcmNoYW50LnJlZHRlYW0ud2Fyc2F3LmxibzExMC8GA1UEAwwoTWVyY2hhbnQgSUQ6IG1lcmNoYW50LnJlZHRlYW0ud2Fyc2F3LmxibzETMBEGA1UECwwKTVlUVThZNURRTTETMBEGA1UECgwKQXBwbGUgSW5jLjELMAkGA1UEBhMCVVMwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAARdoBFEtnuapXFKw4DYWsW0yV4bavpdWKszkefi19AhlIRE3WSNWSn25W5tZNFjMWtLISBmqANyufx2xP19oRvyo4ICaDCCAmQwTwYIKwYBBQUHAQEEQzBBMD8GCCsGAQUFBzABhjNodHRwOi8vb2NzcC11YXQuY29ycC5hcHBsZS5jb20vb2NzcDA0LXRlc3R3d2RyY2FlY2MwHQYDVR0OBBYEFAV7nS4mNDLy1gxvOAcCS1hOgY4lMAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgwFoAU1tbVWuX//cJ8NMND3r1odlw2qb4wggEdBgNVHSAEggEUMIIBEDCCAQwGCSqGSIb3Y2QFATCB/jCBwwYIKwYBBQUHAgIwgbYMgbNSZWxpYW5jZSBvbiB0aGlzIGNlcnRpZmljYXRlIGJ5IGFueSBwYXJ0eSBhc3N1bWVzIGFjY2VwdGFuY2Ugb2YgdGhlIHRoZW4gYXBwbGljYWJsZSBzdGFuZGFyZCB0ZXJtcyBhbmQgY29uZGl0aW9ucyBvZiB1c2UsIGNlcnRpZmljYXRlIHBvbGljeSBhbmQgY2VydGlmaWNhdGlvbiBwcmFjdGljZSBzdGF0ZW1lbnRzLjA2BggrBgEFBQcCARYqaHR0cDovL3d3dy5hcHBsZS5jb20vY2VydGlmaWNhdGVhdXRob3JpdHkvMEEGA1UdHwQ6MDgwNqA0oDKGMGh0dHA6Ly9jcmwtdWF0LmNvcnAuYXBwbGUuY29tL2FwcGxld3dkcmNhZWNjLmNybDAOBgNVHQ8BAf8EBAMCAygwTwYJKoZIhvdjZAYgBEIMQDU4MDJEMUM3NzRGMDk2MkY4MTEyNDhFNTM4REUzQkVGNjgwQzc5ODZCQjVCNERDRTBCNTYyNDlGMzdDQkI5NDMwCgYIKoZIzj0EAwIDRwAwRAIgTjtiYjk/BKp3V8Dg6mIlcm5FCOF06zub7Jsr6wCsvKACIH8U114DI5HnmfcNvwM4RXFDPToop4+jjMAPvidipKkg";
    
    private ApplePayDecrypter applePayDecrypter;
    
    private final byte[] data = Base64.decode(encodedData);

    private final byte[] ephemeralKey = Base64.decode(encodedEphemeralKey);

    @Before
    public void setUp() {
        when(mockConfig.getWorldpayConfig()).thenReturn(mockWorldpayConfig);
        when(mockWorldpayConfig.getApplePayConfig()).thenReturn(mockApplePayConfig);
        when(mockApplePayConfig.getPrivateKey()).thenReturn(Base64.decode(encodedPrivateKey));
        when(mockApplePayConfig.getPublicCertificate()).thenReturn(Base64.decode(encodedPublicCertificate));
        applePayDecrypter = new ApplePayDecrypter(mockConfig, objectMapper);
    }

    @Test
    public void shouldDecryptData_whenPrivateKeyAndPublicCertificateAreValid() {
        ApplePaymentData applePaymentData = applePayDecrypter.performDecryptOperation(
                data,
                ephemeralKey
        );
        assertThat(applePaymentData.getApplicationExpirationDate(), is("191031"));
        assertThat(applePaymentData.getApplicationPrimaryAccountNumber(), is("5186009300800808"));
        assertThat(applePaymentData.getCurrencyCode(), is("840"));
        assertThat(applePaymentData.getDeviceManufacturerIdentifier(), is("050110030273"));
        assertThat(applePaymentData.getPaymentDataType(), is("EMV"));
        assertThat(applePaymentData.getTransactionAmount(), is("500"));
        assertThat(applePaymentData.getPaymentData().get("emvData"), is("nyYIM2LtbOmXgn2fEBIBFKUAAAAAAAAAAAAAAAAAAACfNgIAA5UFAAAAAACfJwGAnzQDAQACnzcE0qE2H58CBgAAAAAFAJ8DBgAAAAAAAF8qAghAmgMWCAicAQCEB6AAAAAEEBBaCFGGAJMAgAgIXzQBAF8kAxkQMZ8aAghAggICgA=="));
    }

    @Test(expected = InvalidKeyException.class)
    public void shouldThrowException_whenPublicCertificateIsInvalid() {
        when(mockApplePayConfig.getPublicCertificate()).thenReturn("nope".getBytes());
        applePayDecrypter = new ApplePayDecrypter(mockConfig, objectMapper);
        applePayDecrypter.performDecryptOperation(
                data,
                ephemeralKey
        );
    }
    
    @Test(expected = InvalidKeyException.class)
    public void shouldThrowException_whenPrivateKeyIsInvalid() {
        when(mockApplePayConfig.getPrivateKey()).thenReturn("nope".getBytes());
        applePayDecrypter = new ApplePayDecrypter(mockConfig, objectMapper);
        applePayDecrypter.performDecryptOperation(
                data,
                ephemeralKey
        );
    }
    
    @Test(expected = InvalidKeyException.class)
    public void shouldThrowException_whenEphemeralKeyIsInvalid() {
        applePayDecrypter.performDecryptOperation(
                data,
                "nope".getBytes(UTF_8)
        );
    }
    
    @Test(expected = InvalidKeyException.class)
    public void shouldThrowException_whenDataIsInvalid() {
        applePayDecrypter.performDecryptOperation(
                "nope".getBytes(UTF_8),
                ephemeralKey
        );
    }
}
