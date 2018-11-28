package tests;

import com.groupgames.web.core.Card;
import com.groupgames.web.core.CardManager;
import com.groupgames.web.core.Host;
import com.groupgames.web.core.Player;
import com.groupgames.web.game.GameAction;
import com.groupgames.web.game.GameLobby;
import com.groupgames.web.game.StateManager;
import com.groupgames.web.game.view.JsonView;
import com.groupgames.web.states.lobby.PlayerJoinState;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.websocket.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class KaHTest {
    Session session;
    /*@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(GameLobby.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }*/

    @org.junit.Test
    //Test for adding a user to a lobby
    public void addUserTest() {
        GameLobby lobby = new GameLobby("1234");
        assertNotNull(lobby.addUser("Zac", "1234"));
    }

    @org.junit.Test
    //Test to see if each user is inizialized with a websocket
    public void registerWebsocketTest() {
        Player p = new Player("Zac", "1234");
        GameLobby lobby = new GameLobby("1234");
        lobby.addUser("Zac", "1234");
        assertEquals(false, lobby.registerWebsocket(null, session));
    }

    @org.junit.Test
    //Test to see if SQL server returns correct amount of white cards
    public void getWhiteCardsTest() {
        CardManager cardManager = new CardManager();
        ArrayList<Card> cardList = cardManager.getWhiteCards(5);
        assertEquals(5, cardList.size());
    }

    @org.junit.Test
    //Test to get all black cards
    public void getAllBlackCardsTest() {
        CardManager cardManager = new CardManager();
        ArrayList<Card> cardList = cardManager.getAllBlackCards();
        assertEquals(16, cardList.size());
    }

    @org.junit.Test
    //Test to see if JSON data parsing works correctly
    public void parseJSONTest() {
        HashMap<String, Object> JSONData = new HashMap<>();
        JSONData.put("method", "refresh");
        JsonView json = new JsonView(JSONData);
        String refreshCommand = json.toString();
        assertEquals("{\"method\":\"refresh\"}", refreshCommand);
    }

    /*@org.junit.Test
    public void gameActionTest() {
        GameLobby lobby = new GameLobby("1234");
        lobby.addUser("zac", "1234");
        StateManager manager = null;
        Map<String, Object> context = null;
        PlayerJoinState p = new PlayerJoinState(manager, context);
        GameAction playerAction = new GameAction("{\\\"method\\\":\\\"refresh\\\"}");
        //TODO hook method
    }*/

    @org.junit.Test
    public void hostObjectTest() {
        Host h = new Host("12412412", "1234");
        assertEquals("1234", h.getGameCode());
    }

    @org.junit.Test
    public void playerObjectTest() {
        Player player = new Player("zac", "1234");
        assertEquals("zac", player.getUsername());
    }
}
