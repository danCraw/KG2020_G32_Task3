package com.company;

import line.LineDrawer;
import util.RealPoint;
import util.ScreenPoint;

import java.awt.*;

public class FunctionDrawer {

    public void drawFunction(IFunction function, ScreenConverter sc, LineDrawer lineDrawer) {
        double step = sc.getRealWidth() / sc.getScreenWidth();
        for (double x1 = sc.getCornerX(); x1 < sc.getRealWidth() + sc.getCornerX(); x1 += step) {
            RealPoint rp1 = new RealPoint(x1, function.compute(x1));
            RealPoint rp2 = new RealPoint(x1 + step, function.compute(x1 + step));

            if (isInsideScreen(sc, rp1) && (isInsideScreen(sc, rp2))) {
                ScreenPoint p1, p2;
                p1 = sc.r2s(rp1);
                p2 = sc.r2s(rp2);
                lineDrawer.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY(), Color.red);
            }
        }
    }

    private boolean crossSide(ScreenConverter sc, RealPoint rp2, RealPoint rp1) {
        return (Math.abs(rp2.getY()) > Math.abs(sc.getCornerY()) + sc.getRealHeight()) && Math.abs(rp1.getY()) < Math.abs(sc.getCornerY()) + sc.getRealHeight();
    }

    private ScreenPoint getCrossSidePoint(ScreenConverter sc, RealPoint rp2, RealPoint rp1) {

        double x1 = rp1.getX();
        double y1 = rp1.getY();
        double x2 = rp2.getX();
        double y2 = rp2.getY();
        double y;

        if (rp1.getY() < rp2.getY()) {
            y = sc.getCornerY();
        } else {
            y = sc.getCornerY() - sc.getRealHeight();
        }

        double x = (((y - y1) * (x2 - x1)));

        RealPoint rp = new RealPoint(x, y);

        return sc.r2s(rp);
    }

    private boolean isInsideScreen(ScreenConverter sc, RealPoint rp) {
        return (sc.getCornerX() < rp.getX() && rp.getX() < sc.getCornerX() + sc.getRealWidth())
                && (sc.getCornerY() > rp.getY() && rp.getY() > sc.getCornerY() - sc.getRealHeight());
    }
}
