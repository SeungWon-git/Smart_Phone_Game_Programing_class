package kr.ac.tukorea.ge.jsw01.s2016180039.slashit.scenes;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

import kr.ac.tukorea.ge.jsw01.framework.interfaces.Recyclable;
import kr.ac.tukorea.ge.jsw01.framework.objects.Sprite;
import kr.ac.tukorea.ge.jsw01.framework.res.Metrics;

public class Blob extends Sprite implements Recyclable {
    private Paint paint = new Paint();
    private float xSpeed, ySpeed;
    private final float gravity = Metrics.height / 150;

    public Blob(float x, float y, float r, Random random, int paintColor) {
        super(x, y, r, r, 0);

        paint.setColor(paintColor);

        xSpeed = random.nextInt((int)r * 2);
        ySpeed = -(Metrics.height / 3 + random.nextInt((int)r *2));
    }

    @Override
    public void update(float frameTime) {
        x += xSpeed * frameTime;
        y += ySpeed * frameTime;
        ySpeed += gravity;

        setDstRectWithRadius();

        if (x > Metrics.width + Metrics.height / 10 || x < -Metrics.height / 10
                || y > Metrics.height + Metrics.height / 10){
            MainScene.get().remove(this);
            return;
        }
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawCircle(x, y, radius, paint);
    }

    @Override
    public void finish() {

    }
}

