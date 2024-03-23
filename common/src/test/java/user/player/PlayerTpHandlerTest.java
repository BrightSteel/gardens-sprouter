package user.player;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.cache.SimpleCache;
import com.sproutermc.sprouter.common.database.cache.PlayerEntryCache;
import com.sproutermc.sprouter.common.server.SprouterServer;
import com.sproutermc.sprouter.common.user.player.PlayerTpHandler;
import com.sproutermc.sprouter.common.user.player.PlayerTpRequest;
import com.sproutermc.sprouter.common.world.SprouterLocation;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.*;

public class PlayerTpHandlerTest {

    static MockSprouterPlayer sender = new MockSprouterPlayer(UUID.randomUUID(), "p1");
    static MockSprouterPlayer recipient = new MockSprouterPlayer(UUID.randomUUID(), "p2");
    static SprouterLocation mockDestination1 = new SprouterLocation(0, 0, 0, 0, 0, null);
    static SprouterLocation mockDestination2 = new SprouterLocation(500, 200, 1000, 0, 0, null);

    @BeforeAll
    public static void setUp() {
        GardensSprouter.setPlayerTpCache(new SimpleCache<>(2));
        GardensSprouter.setPlayerEntryCache(Mockito.mock(PlayerEntryCache.class));
        GardensSprouter.setSprouterServer(Mockito.mock(SprouterServer.class));
    }

    @Test
    public void testTpRequest() {
        mockTpRequest();
        // test
        PlayerTpHandler.sendTeleportRequest(sender, recipient, PlayerTpRequest.Direction.TO);
        assertReceivedMessage(sender, "Sent teleport request to p2");
        assertReceivedMessage(recipient, "p1 would like to teleport to you");

        PlayerTpHandler.acceptTeleportRequest(recipient);
        assertReceivedMessage(recipient, "Accepted teleport request");
        assertReceivedMessage(sender, "p2 accepted your teleport request");
        Assertions.assertEquals(sender.getLocation(), mockDestination2);
        Assertions.assertEquals(sender.getLocation(), recipient.getLocation());
    }

    @Test
    public void testTpHereRequest() {
        mockTpRequest();
        // test
        PlayerTpHandler.sendTeleportRequest(sender, recipient, PlayerTpRequest.Direction.HERE);
        assertReceivedMessage(sender, "Sent teleport request to p2");
        assertReceivedMessage(recipient, "p1 would like you to teleport to them");

        PlayerTpHandler.acceptTeleportRequest(recipient);
        assertReceivedMessage(recipient, "Accepted teleport request");
        assertReceivedMessage(sender, "p2 accepted your teleport request");
        Assertions.assertEquals(sender.getLocation(), mockDestination1);
        Assertions.assertEquals(sender.getLocation(), recipient.getLocation());
    }

    private void mockTpRequest() {
        sender.setCurrentLocation(mockDestination1);
        recipient.setCurrentLocation(mockDestination2);

        Mockito.when(GardensSprouter.getSprouterServer().getPlayer(sender.getUniqueId())).thenReturn(sender); // mock player online
    }

    private void assertReceivedMessage(MockSprouterPlayer player, String message) {
        Assertions.assertTrue(player.receivedMessage(message));
    }
}
