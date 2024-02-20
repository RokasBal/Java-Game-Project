package Inputs;

import Editor.EditorPanel;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;
    private EditorPanel editorPanel;
//    public MouseInputs(GamePanel gamePanel) {
//        this.gamePanel = gamePanel;
//    }

    public MouseInputs(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getX() <= 416 && e.getY() <= 416) {
            editorPanel.tileSelected(e.getX(), e.getY());
        }
        if(e.getY() >= 416) {
            editorPanel.drawTile(e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(e.getY() >= 416) {
            editorPanel.drawTile(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
