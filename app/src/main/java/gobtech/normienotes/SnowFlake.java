package gobtech.normienotes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

/**
 * Created by Gabe K on 1/24/2016.
 */
class SnowFlake {
    private static final float ANGE_RANGE = 0.1f;
    private static final float HALF_ANGLE_RANGE = ANGE_RANGE / 2f;
    private static final float HALF_PI = (float) Math.PI / 2f;
    private static final float ANGLE_SEED = 25f;
    private static final float ANGLE_DIVISOR = 10000f;
    private static final float INCREMENT_LOWER = 1f;
    private static final float INCREMENT_UPPER = 4f;
    private static final float FLAKE_SIZE_LOWER = 7f;
    private static final float FLAKE_SIZE_UPPER = 20f;

    private final Random random;
    private final Point position;
    private float angle;
    private final float increment;
    private final float flakeSize;
    private final Paint paint;
    private int shape;

    public static SnowFlake create(int width, int height, Paint paint) {
        Random random = new Random();
        int x = 0;
        int y = 0;
        if (width != 0 && height != 0) {
            x = random.nextInt(width);
            y = random.nextInt(height);
        }
        Point position = new Point(x, y);
        float angle = 360;
        float increment = random.nextFloat() * 3f + INCREMENT_LOWER;
        float flakeSize = random.nextInt(20) + 70;
        return new SnowFlake(random, position, angle, increment, flakeSize, random.nextInt(3), paint);
    }

    SnowFlake(Random random, Point position, float angle, float increment, float flakeSize, int shape, Paint paint) {
        this.random = random;
        this.position = position;
        this.angle = random.nextInt(360);
        this.increment = increment;
        this.flakeSize = flakeSize;
        this.paint = paint;
        this.shape = shape;
    }

    private void move(int width, int height) {
//        double x = position.x + (increment * Math.cos(angle));
        //double y = position.y + (increment * Math.sin(angle));
        double x = position.x;
        double y = position.y + increment;

        //angle += random.nextInt(-ANGLE_SEED, ANGLE_SEED) / ANGLE_DIVISOR;

        position.set((int) x, (int) y);

        if (!isInside(width, height)) {
            reset(width);
        }
    }

    private boolean isInside(int width, int height) {
        int x = position.x;
        int y = position.y;
        return x >= -flakeSize - 1 && x + flakeSize <= width && y >= -flakeSize - 1 && y - flakeSize < height;
    }

    private void reset(int width) {
        position.x = random.nextInt(width);
        position.y = (int) (-flakeSize - 1);
        shape = random.nextInt(3);
        angle = random.nextInt(360);
    }

    public void draw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        move(width, height);

//        canvas.save();
//        canvas.rotate(90);
        canvas.save();
        canvas.rotate(angle, position.x + flakeSize / 2, position.y + flakeSize / 2);
        if (shape == 0) {
            canvas.drawRect(position.x, position.y, position.x + flakeSize, position.y + flakeSize / 3, paint);
        } else if (shape == 1) {
            canvas.drawRect(position.x, position.y + flakeSize / 3, position.x + flakeSize, position.y + 2 * flakeSize / 3, paint);
            canvas.drawRect(position.x + flakeSize / 3, position.y, position.x + 2 * flakeSize / 3, position.y + flakeSize, paint);
        } else {
            canvas.drawRect(position.x, position.y + flakeSize * .4f, position.x + flakeSize, position.y + flakeSize * .6f, paint);
            canvas.drawCircle(position.x + flakeSize / 2, position.y + flakeSize / 6, flakeSize / 8, paint);
            canvas.drawCircle(position.x + flakeSize / 2, position.y + 5 * flakeSize / 6, flakeSize / 8, paint);
        }
        canvas.restore();
//        canvas.drawRect(position.x, position.y, position.x + flakeSize, position.y + flakeSize, paint);
//        canvas.drawCircle(position.x + flakeSize / 2, position.y, flakeSize / 2, paint);
//        canvas.drawCircle(position.x, position.y + flakeSize / 2, flakeSize / 2, paint);
//        canvas.restore();
    }
}