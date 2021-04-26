package tests;

import org.junit.jupiter.api.Test;
import server.Chat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatTest {

    // Initialisierung, Update, Zugriff von Chat
    @Test
    void basicChat() {
        // Init
        Chat chat = new Chat();
        assertEquals(0, chat.nachrichten.size());

        //Update
        chat.nachrichten.add(new String[]{"Justus", "Das ist ein Test"});
        chat.nachrichten.add(new String[]{"Aurelle", "Willkommen!"});

        //Zugriff
        assertEquals("Justus", chat.nachrichten.get(0)[0]);
        assertEquals("Willkommen!", chat.nachrichten.get(1)[1]);
        assertEquals(2, chat.nachrichten.size());
    }
}
