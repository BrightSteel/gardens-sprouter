package chat;

import com.sproutermc.sprouter.common.chat.ChatUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChatUtilTest {

    @Test
    public void testStripFormatting() {

        String colorCodedString = "&bBright_&#FF0000Steel";
        Assertions.assertEquals("Bright_Steel", ChatUtil.stripFormatting(colorCodedString));

        String longColorCodedString = "&bHey &l&cev&2eryone this is me talking. &rResetting now. &#FF3235Colors but now fails: &# bbbeee & b";
        Assertions.assertEquals(
                "Hey everyone this is me talking. Resetting now. Colors but now fails: &# bbbeee & b",
                ChatUtil.stripFormatting(longColorCodedString)
        );
    }
}
