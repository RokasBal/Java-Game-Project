package Inputs;

import Editor.EditorPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Klasė, naudojama gauti klaviatūros įvesti editoriuje.
 * @author Rokas Baliutavičius, 5 grupė
 */

public class EditorInputs implements KeyListener {

    private EditorPanel editorPanel;

    public EditorInputs(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_S:
                System.out.println("Saving to JSON");
                try {
                    editorPanel.saveToJSON();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case KeyEvent.VK_1:
                editorPanel.selectedLayer = 0;
                break;
            case KeyEvent.VK_2:
                editorPanel.selectedLayer = 1;
                break;
            case KeyEvent.VK_3:
                editorPanel.selectedLayer = 2;
                break;
            case KeyEvent.VK_L:
                editorPanel.loadData();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
