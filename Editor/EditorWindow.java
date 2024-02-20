package Editor;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class EditorWindow {
    private JFrame jframe;

    public EditorWindow(EditorPanel editorPanel) {
        jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jframe.add(editorPanel);
        jframe.pack();
        //jframe.setResizable(false);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }

}
