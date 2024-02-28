package Editor;

import java.awt.*;
import java.util.Objects;

/**
 * Klasė, vykdanti editoriaus ciklą.
 * @author Rokas Baliutavičius, 5 grupė
 */

public class Editor implements Runnable{
    private EditorPanel editorPanel;
    private EditorWindow editorWindow;
    private final int FPS_SET = 165;
    private Thread editorThread;
    private String osName = System.getProperty("os.name");
    public Editor() {
        editorPanel = new EditorPanel();
        editorWindow = new EditorWindow(editorPanel);
        startEditorLoop();
    }

    private void startEditorLoop() {
        editorThread = new Thread(this);
        editorThread.start();
    }

    public void run() {

        long previousTime = System.nanoTime();
        int frames = 0;
        double deltaF = 0;
        long lastCheck = System.currentTimeMillis();

        double timePerFrame = 1000000000.0 / FPS_SET;

        while(true){
            long currentTime = System.nanoTime();

            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaF >= 1) {
                editorPanel.repaint();
                if(Objects.equals(osName, "Linux")) Toolkit.getDefaultToolkit().sync();
                frames++;
                deltaF--;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
//                System.out.println(frames + " " + updates);
                frames = 0;
            }
        }
    }
}

