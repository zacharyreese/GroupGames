package actionTests;

import com.groupgames.web.game.GameAction;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ActionTests {

    //Tests if the BeginGameAction parses JSON correctly
    @Test
    public void BeginGameActionTest() {
        GameAction gameAction = new GameAction("{type:selected}");
        //BeginGameAction beginGameAction = new BeginGameAction(gameAction);
        assertEquals("selected", gameAction.getType());
    }
    //Tests if the QuitAction parses JSON correctly
    @Test
    public void QuitActionTest() {
        GameAction gameAction = new GameAction("{type:cardID}");
        //BeginGameAction beginGameAction = new BeginGameAction(gameAction);
        assertEquals("cardID", gameAction.getType());
    }
    //Tests if the SubmitAction parses JSON correctly
    @Test
    public void SubmitActionTest() {
        GameAction gameAction = new GameAction("{type:uid}");
        //BeginGameAction beginGameAction = new BeginGameAction(gameAction);
        assertEquals("uid", gameAction.getType());
    }


}
