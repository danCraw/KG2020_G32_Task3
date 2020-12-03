package line;

import util.RealPoint;

public class Line {
    private RealPoint P1;
    private RealPoint P2;

    public Line(RealPoint point1, RealPoint point2) {
        this.P1 = point1;
        this.P2 = point2;
    }

    public Line(double x1, double y1, double x2, double y2) {
        P1 = new RealPoint(x1, y1);
        P2 = new RealPoint(x2, y2);
    }

    public RealPoint getP1() {
        return P1;
    }

    public void setP1(RealPoint p1) {
        this.P1 = p1;
    }

    public RealPoint getP2() {
        return P2;
    }

    public void setP2(RealPoint p2) {
        this.P2 = p2;
    }

}
