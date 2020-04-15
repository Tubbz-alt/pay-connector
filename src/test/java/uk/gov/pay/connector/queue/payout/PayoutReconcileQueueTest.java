package uk.gov.pay.connector.queue.payout;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.pay.connector.app.ConnectorConfiguration;
import uk.gov.pay.connector.app.SqsConfig;
import uk.gov.pay.connector.app.config.PayoutReconcileProcessConfig;
import uk.gov.pay.connector.queue.QueueException;
import uk.gov.pay.connector.queue.QueueMessage;
import uk.gov.pay.connector.queue.sqs.SqsQueueService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PayoutReconcileQueueTest {

    @Mock
    private SqsQueueService sqsQueueService;
    @Mock
    private ConnectorConfiguration connectorConfiguration;
    private PayoutReconcileQueue payoutReconcileQueue;

    @Before
    public void setUp() {
        PayoutReconcileProcessConfig payoutReconcileProcessConfig = mock(PayoutReconcileProcessConfig.class);
        SqsConfig sqsConfig = mock(SqsConfig.class);
        when(sqsConfig.getPayoutReconcileQueueUrl()).thenReturn("");
        when(connectorConfiguration.getSqsConfig()).thenReturn(sqsConfig);
        when(connectorConfiguration.getPayoutReconcileProcessConfig()).thenReturn(payoutReconcileProcessConfig);

        ObjectMapper objectMapper = new ObjectMapper();
        payoutReconcileQueue = new PayoutReconcileQueue(sqsQueueService, connectorConfiguration, objectMapper);
    }

    @Test
    public void shouldParsePayoutFromQueueGivenWellFormattedJSON() throws QueueException {
        String validJsonMessage = "{ \"gateway_payout_id\": \"payout-id\", \"connect_account_id\": \"connect-accnt-id\"}";
        SendMessageResult messageResult = mock(SendMessageResult.class);

        List<QueueMessage> messages = Arrays.asList(
                QueueMessage.of(messageResult, validJsonMessage)
        );
        when(sqsQueueService.receiveMessages(anyString(), anyString())).thenReturn(messages);

        List<PayoutReconcileMessage> payoutReconcileMessages = payoutReconcileQueue.retrievePayoutMessages();

        assertNotNull(payoutReconcileMessages);
        assertEquals("payout-id", payoutReconcileMessages.get(0).getGatewayPayoutId());
        assertEquals("connect-accnt-id", payoutReconcileMessages.get(0).getConnectAccountId());
    }

    @Test
    public void shouldSendValidSerialisedPayoutToQueue() throws QueueException, JsonProcessingException {
        Payout payout = new Payout("payout-id", "connect-accnt-id");
        when(sqsQueueService.sendMessage(anyString(), anyString())).thenReturn(mock(QueueMessage.class));

        payoutReconcileQueue.sendPayout(payout);

        verify(sqsQueueService).sendMessage(connectorConfiguration.getSqsConfig().getPayoutReconcileQueueUrl(),
                "{\"gateway_payout_id\":\"payout-id\",\"connect_account_id\":\"connect-accnt-id\"}");
    }
}
