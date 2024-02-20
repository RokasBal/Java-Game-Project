package Inputs;

import Editor.EditorPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class EditorInputs implements KeyListener {

    private EditorPanel editorPanel;

    public EditorInputs(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_P:
                System.out.println("Saving to JSON");
                try {
                    editorPanel.saveToJSON();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case KeyEvent.VK_O:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
