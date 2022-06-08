package kr.ac.tukorea.ge.jsw01.s2016180039.slashit.scenes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.Random;

import kr.ac.tukorea.ge.jsw01.framework.game.RecycleBin;
import kr.ac.tukorea.ge.jsw01.framework.interfaces.Recyclable;
import kr.ac.tukorea.ge.jsw01.framework.objects.Sprite;

import kr.ac.tukorea.ge.jsw01.framework.res.BitmapPool;
import kr.ac.tukorea.ge.jsw01.framework.res.Metrics;
import kr.ac.tukorea.ge.jsw01.s2016180039.slashit.R;

public class Slime extends Sprite implements Recyclable {
    private static Random random = new Random();
    private int minimalSlash;
    private int slashNum;
    private Type slimeType;
    private float xSpeed, ySpeed;
    private float gravity;
    private int size = 0;
    private Paint paint = new Paint();
    private RectF rect = new RectF();
    private float rSpeed;

    private static int[] BITMAP_IDS = {
            R.mipmap.eye_blue, R.mipmap.eye_brown, R.mipmap.eye_white,
            R.mipmap.eye_lightgreen, R.mipmap.eye_skyblue, R.mipmap.eye_lightbrown,
            R.mipmap.eye_orange, R.mipmap.eye_purple, R.mipmap.eye_green
    };

    public enum Size {
        small, medium, big
    }

    public enum Type {
        normal, special_penalty, special_fast,
        COUNT, RANDOM
    }

    public Slime(int tSize, Type type, boolean dirRight) {
        super(-Metrics.height / tSize / 2, Metrics.height - random.nextInt(Metrics.height / 5) * (tSize / 4),
                Metrics.height / tSize / 2, Metrics.height / tSize / 2, BITMAP_IDS[random.nextInt(BITMAP_IDS.length)]);

        if (dirRight == false) {
            x = Metrics.width + Metrics.height / tSize / 2;
        }

        size = tSize;
    }

    private void init(Type type, boolean dirRight) {
        if (type == Type.RANDOM) {
            type = Type.values()[random.nextInt(Type.COUNT.ordinal())];
        }
        this.slimeType = type;

        gravity = Metrics.height / 150;

        paint.setColor(random.nextInt());

        rSpeed = random.nextInt(30);

        if (dirRight == true) {
            this.xSpeed = Metrics.width / 15 * size;
        } else {
            this.xSpeed = -(Metrics.width / 15 * size);
        }
        this.ySpeed = -(Metrics.height / 2 + random.nextInt(Metrics.height / 4 / size));
    }

    public static Slime get() {
        Slime slime = (Slime) RecycleBin.get(Slime.class);
        Size size = null;
        Type type = null;

        if(SlimeGen.GetStage() == 1) {
            size = Size.big;
            type = Type.normal;
        }
        else if(SlimeGen.GetStage() == 2) {
            size = Size.big;
            type = Type.normal;
        }
        else if(SlimeGen.GetStage() == 3) {
            size = Size.big;
            type = Type.normal;
        }
        else if(SlimeGen.GetStage() == 4) {
        }
        else if(SlimeGen.GetStage() == 5) {
        }

        boolean dirRight = random.nextBoolean();
        int tSize = 0;

        if (size == Size.big) {
            tSize = random.nextInt(3) + 5;
        } else if (size == Size.medium) {
            tSize = random.nextInt(3) + 9;
        } else if (size == Size.small) {
            tSize = random.nextInt(3) + 12;
        }

        if (slime == null) {
            slime = new Slime(tSize, type, dirRight);
        } else {
            if (dirRight == false) {
                slime.x = Metrics.width + Metrics.height / tSize / 2;
            }
            else{
                slime.x = -Metrics.height / tSize / 2;
            }
            slime.y = Metrics.height - random.nextInt(Metrics.height / 5) * (tSize / 4);
            slime.setDstRect(Metrics.height / tSize / 2, Metrics.height / tSize / 2);
            slime.bitmap = BitmapPool.get(BITMAP_IDS[random.nextInt(BITMAP_IDS.length)]);
            slime.size = tSize;
        }

        slime.init(type, dirRight);

        return slime;
    }

    public boolean onTouchEvent(MotionEvent event){
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }

        if(Math.abs(event.getX()- this.x) < Metrics.height / size / 3 &&
                Math.abs(event.getY()- this.y) < Metrics.height / size / 3) {
            MainScene.get().score.add(10);
            MainScene.get().remove(this);
        }

        return true;
    }

    @Override
    public void update(float frameTime) {
        x += xSpeed * frameTime;
        y += ySpeed * frameTime;
        ySpeed += gravity;
        rSpeed += rSpeed * frameTime;

        if (x > Metrics.width + Metrics.height / 10 || x < -Metrics.height / 10
                || y > Metrics.height + Metrics.height / 10){
           MainScene.get().remove(this);
           return;
        }

        setDstRectWithRadius();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();

        rect.set(x - Metrics.height / size / 2, y - Metrics.height / size / 2,
                x + Metrics.height / size / 2, y + Metrics.height / size / 2);
        canvas.rotate(rSpeed, rect.centerX(), rect.centerY());
        canvas.drawRoundRect(rect, Metrics.height / size / 8, Metrics.height / size / 8, paint);

        canvas.restore();
        super.draw(canvas);
    }

    @Override
    public void finish() {

    }
}
