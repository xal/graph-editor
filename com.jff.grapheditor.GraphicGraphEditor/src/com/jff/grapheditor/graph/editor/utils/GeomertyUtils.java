package com.jff.grapheditor.graph.editor.utils;

import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

public class GeomertyUtils {

	public static int[] rotate(int x, int y, float Angle, int xCenter, int yCenter) {
		double xRot = xCenter + Math.cos(Angle) * (x - xCenter)
				- Math.sin(Angle) * (y - yCenter);
		double yRot = yCenter + Math.sin(Angle) * (x - xCenter)
				+ Math.cos(Angle) * (y - yCenter);

		int[] result = new int[2];

		result[0] = (int) xRot;
		result[1] = (int) yRot;

		return result;
	}
	
	public static Arc2D makeArc(Point2D s, Point2D mid, Point2D e) {
		Point2D c = getCircleCenter(s, mid, e);
		double radius = c.distance(s);

		double sx = s.getX();
		double sy = s.getY();
		double cx = c.getX();
		double cy = c.getY();
		double midx = mid.getX();
		double midy = mid.getY();
		double ex = e.getX();
		double ey = e.getY();
		
		double startAngle = makeAnglePositive(Math.toDegrees(-Math.atan2(sy
				- cy, sx - cx)));
		double midAngle = makeAnglePositive(Math.toDegrees(-Math.atan2(midy
				- cy, midx - cx)));
		double endAngle = makeAnglePositive(Math.toDegrees(-Math.atan2(ey
				- cy, ex - cx)));

		// Now compute the phase-adjusted angles begining from startAngle,
		// moving positive and negative.
		double midDecreasing = getNearestAnglePhase(startAngle, midAngle, -1);
		double midIncreasing = getNearestAnglePhase(startAngle, midAngle, 1);
		double endDecreasing = getNearestAnglePhase(midDecreasing, endAngle, -1);
		double endIncreasing = getNearestAnglePhase(midIncreasing, endAngle, 1);

		// Each path from start -> mid -> end is technically, but one will wrap
		// around the entire
		// circle, which isn't what we want. Pick the one that with the smaller
		// angular change.
		double extent = 0;
		if (Math.abs(endDecreasing - startAngle) < Math.abs(endIncreasing
				- startAngle)) {
			extent = endDecreasing - startAngle;
		} else {
			extent = endIncreasing - startAngle;
		}

		return new Arc2D.Double(cx - radius, cy - radius, radius * 2,
				radius * 2, startAngle, extent, Arc2D.OPEN);
	}
	

	

	public static Point2D getCircleCenter(Point2D a, Point2D b, Point2D c) {
		double ax = a.getX();
		double ay = a.getY();
		double bx = b.getX();
		double by = b.getY();
		double cx = c.getX();
		double cy = c.getY();

		double A = bx - ax;
		double B = by - ay;
		double C = cx - ax;
		double D = cy - ay;

		double E = A * (ax + bx) + B * (ay + by);
		double F = C * (ax + cx) + D * (ay + cy);

		double G = 2 * (A * (cy - by) - B * (cx - bx));
		if (G == 0.0)
			return b; // a, b, c must be collinear

		double px = (D * E - B * F) / G;
		double py = (A * F - C * E) / G;
		return new Point2D.Double(px, py);
	}

	public static double makeAnglePositive(double angleDegrees) {
		double ret = angleDegrees;
		if (angleDegrees < 0) {
			ret = 360 + angleDegrees;
		}
		return ret;
	}

	public static double getNearestAnglePhase(double limitDegrees,
			double sourceDegrees, int dir) {
		double value = sourceDegrees;
		if (dir > 0) {
			while (value < limitDegrees) {
				value += 360.0;
			}
		} else if (dir < 0) {
			while (value > limitDegrees) {
				value -= 360.0;
			}
		}
		return value;
	}
}
