package com.company;

import background.Background;
import line.DDALineDrawer;
import line.Line;
import line.LineDrawer;
import pixel.BufferedImagePixelDrawer;
import pixel.PixelDrawer;
import util.Function;
import util.RealPoint;
import util.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.ParseException;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private static DrawPanel dp;
    private ScreenPoint lastPosition = null;
    private  ScreenConverter sc = new ScreenConverter(-5, 5, 10, 10, 800, 800);
    private  LineDrawer ld = null;
    private Line currentNewLine = null;
    private FunctionDrawer fd = new FunctionDrawer();
    private IFunction function;

    void setFunction(IFunction function) {
        this.function = function;
    }

    DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        dp = this;
        sc.setScreenWidth(getWidth());
        sc.setScreenHeight(getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics gr = bi.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.dispose();

        Graphics2D graphics2D = (Graphics2D) bi.getGraphics();
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        ld = new DDALineDrawer(pd);
        new Background(sc, ld, graphics2D);

            drawFunction(function);

        g.drawImage(bi, 0, 0, null);
    }

    private void drawFunction(IFunction function) {
        if(function == null) {
            return;
        }
        fd.drawFunction(function, sc, ld);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint currentPosition = new ScreenPoint(e.getX(), e.getY());
        if (lastPosition != null) {
            ScreenPoint deltaScreen = new ScreenPoint(currentPosition.getX() - lastPosition.getX(), currentPosition.getY() - lastPosition.getY());
            RealPoint deltaReal = sc.s2r(deltaScreen);
            RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));
            RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(), deltaReal.getY() - zeroReal.getY());
            sc.setCornerX(sc.getCornerX() - vector.getX());
            sc.setCornerY(sc.getCornerY() - vector.getY());
            lastPosition = currentPosition;
        }
        if(currentNewLine != null){
            currentNewLine.setP2(sc.s2r(currentPosition));
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            lastPosition = new ScreenPoint(e.getX(), e.getY());
        } else if(e.getButton() == MouseEvent.BUTTON1) {
            currentNewLine = new Line(sc.s2r(new ScreenPoint(e.getX(), e.getY())), sc.s2r(new ScreenPoint(e.getX(), e.getY())));
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            lastPosition = null;
        } else if (e.getButton() == MouseEvent.BUTTON1){
            currentNewLine = null;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks < 0 ? 1.05 : 0.95;
        for (int i = 0; i < Math.abs(clicks); i++){
            scale *= coef;
        }
        sc.setRealWidth(sc.getRealWidth() * scale);
        sc.setRealHeight(sc.getRealHeight() * scale);
        repaint();
    }

    public static DrawPanel getDp() {
        return dp;
    }
}