package background;

import com.company.ScreenConverter;
import line.Line;
import line.LineDrawer;
import util.RealPoint;
import util.ScreenPoint;

import java.awt.*;
import java.text.DecimalFormat;


public class Background {

    private ScreenConverter sc;

    public Background(ScreenConverter sc, LineDrawer ld, Graphics2D graphics) {
        this.sc = sc;
        drawCoordinateAxis(ld);
        drawGrid(ld);
        drawValues(graphics);
    }

    private void drawGrid(LineDrawer ld) {
        double step = sc.getRealHeight() / 10;
        for (double i = step; i <= sc.getRealWidth() + Math.abs(sc.getCornerX()); i += step) {
            ScreenPoint p1 = sc.r2s(new RealPoint(i, sc.getRealHeight() + sc.getCornerY()));
            ScreenPoint p2 = sc.r2s(new RealPoint(i, -(sc.getRealHeight() - sc.getCornerY())));
            ld.drawLine(p1, p2, Color.gray);
            p1 = sc.r2s(new RealPoint(-i, sc.getRealHeight() + sc.getCornerY()));
            p2 = sc.r2s(new RealPoint(-i, -(sc.getRealHeight() - sc.getCornerY())));
            ld.drawLine(p1, p2, Color.gray);
        }

        for (double i = step; i <= sc.getRealHeight() + Math.abs(sc.getCornerY()); i += step) {
            ScreenPoint p1 = sc.r2s(new RealPoint(sc.getRealWidth() + sc.getCornerX(), i));
            ScreenPoint p2 = sc.r2s(new RealPoint(-(sc.getRealWidth() - sc.getCornerX()), i));
            ld.drawLine(p1, p2, Color.gray);
            p1 = sc.r2s(new RealPoint(sc.getRealWidth() + sc.getCornerX(), -i));
            p2 = sc.r2s(new RealPoint(-(sc.getRealWidth() - sc.getCornerX()), -i));
            ld.drawLine(p1, p2, Color.gray);
        }
    }

    private void drawValues(Graphics2D g) {
        g.setColor(Color.BLACK);
        double step = sc.getRealHeight() / 10;

        for (double x = 0; x <= sc.getRealWidth() + Math.abs(sc.getCornerX()); x += step) {
            ScreenPoint point = sc.r2s(new RealPoint(x, 0));
            ScreenPoint oppositePoint = sc.r2s(new RealPoint(-x, 0));
            if (step >= 1) {
                g.drawString(String.valueOf((int) x), point.getX(), point.getY());
                g.drawString(String.valueOf((int) -x), oppositePoint.getX(), oppositePoint.getY());
            } else {
                String pattern = "#.##";
                DecimalFormat f = new DecimalFormat(pattern);
                String value = f.format(x).equals("0") ? "0.000001" : f.format(x);
                g.drawString(value, point.getX(), point.getY());
                g.drawString(value, oppositePoint.getX(), oppositePoint.getY());
            }
        }

        step = sc.getRealHeight() / 10;
        for (double y = 0; y <= sc.getRealHeight() + Math.abs(sc.getCornerY()); y += step) {
            ScreenPoint point = sc.r2s(new RealPoint(0, y));
            ScreenPoint oppositePoint = sc.r2s(new RealPoint(0, -y));
            if (step >= 1) {
                g.drawString(String.valueOf((int) y), point.getX(), point.getY());
                g.drawString(String.valueOf((int) -y), oppositePoint.getX(), oppositePoint.getY());
            } else {
                String pattern = "#.##";
                DecimalFormat f = new DecimalFormat(pattern);
                String value = f.format(y).equals("0") ? "0.000001" : f.format(y);
                g.drawString(value, point.getX(), point.getY());
                g.drawString(value, oppositePoint.getX(), oppositePoint.getY());
            }
        }
    }

    private void drawCoordinateAxis(LineDrawer ld) {
        Line xAxis = new Line(sc.getCornerX(), 0, sc.getRealWidth() + sc.getCornerX(), 0);
        Line yAxis = new Line(0, -sc.getRealHeight() + sc.getCornerY(), 0, sc.getRealHeight() + sc.getCornerY());
        ld.drawLine(sc.r2s(xAxis.getP1()), sc.r2s(xAxis.getP2()), Color.red);
        ld.drawLine(sc.r2s(yAxis.getP1()), sc.r2s(yAxis.getP2()), Color.red);
    }
}
