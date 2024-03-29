package main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;
import utilz.LoadImages;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;

    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        setFocusable(true);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 896);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void openEditor() {
        new Editor.Editor();
    }
    public void updateGame() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
