package user.player;

import com.sproutermc.sprouter.common.chat.ChatUtil;
import com.sproutermc.sprouter.common.state.SprouterGameMode;
import com.sproutermc.sprouter.common.user.player.SprouterPlayer;
import com.sproutermc.sprouter.common.world.SprouterLocation;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class MockSprouterPlayer extends SprouterPlayer {

    private final List<String> receivedMessages = new ArrayList<>();
    private SprouterLocation currentLocation;

    public MockSprouterPlayer(UUID uuid, String username) {
        super(uuid, username);
    }

    @Override
    public String getDisplayName() {
        return getUsername();
    }

    @Override
    public void sendMessage(String message) {
        String plainMessage = ChatUtil.stripFormatting(message);
        receivedMessages.add(plainMessage);
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public boolean teleport(SprouterLocation location) {
        currentLocation = location;
        return false;
    }

    @Override
    public void setGameMode(SprouterGameMode gameMode) {

    }

    @Override
    public boolean isFlyAllowed() {
        return false;
    }

    @Override
    public void setFlyAllowed(boolean allowed) {

    }

    @Override
    public void setTabListName(String name) {

    }

    @Override
    public SprouterLocation getLocation() {
        return currentLocation;
    }

    public boolean receivedMessage(String message) {
        Optional<String> m = receivedMessages
                .stream()
                .filter(s -> s.contains(message))
                .findFirst();
        return m.isPresent();
    }

    public void setCurrentLocation(SprouterLocation location) {
        this.currentLocation = location;
    }
}
