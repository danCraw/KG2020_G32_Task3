package line;

import util.ScreenPoint;

import java.awt.*;

public interface LineDrawer {
    void drawLine(int x1, int y1, int x2, int y2, Color color);

    void drawLine(ScreenPoint point1, ScreenPoint point2, Color color);

    default int swap(int first, int second) {
        return first;
    }
}
