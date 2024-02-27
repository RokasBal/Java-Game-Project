package Inputs;

import Editor.EditorPanel;
import main.GamePanel;
import utilz.Collisions;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utilz.Constants.Directions.*;

/**
 * Klasė, naudojama gauti klaviatūros įvesti žaidime.
 * @author Rokas Baliutavičius, 5 grupė
 */

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;
    private int index = 0;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(true);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDown(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                gamePanel.getGame().getPlayer().setJump(true);
                break;
            case KeyEvent.VK_E:
                gamePanel.openEditor();
                break;
            case KeyEvent.VK_F:
                //Open door;
                if(Collisions.checkIfAtDoor(gamePanel)) gamePanel.getGame().getLevelManager().changeLevel();
                break;
            case KeyEvent.VK_L:
                gamePanel.getGame().getLevelManager().addLevel();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                gamePanel.getGame().getPlayer().setJump(false);
                //System.out.println("JUMP RELEASED");
                break;
        }
    }

}
