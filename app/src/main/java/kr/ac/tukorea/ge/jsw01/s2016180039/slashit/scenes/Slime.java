package kr.ac.tukorea.ge.jsw01.s2016180039.slashit.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.Random;

import kr.ac.tukorea.ge.jsw01.framework.game.RecycleBin;
import kr.ac.tukorea.ge.jsw01.framework.interfaces.Recyclable;
import kr.ac.tukorea.ge.jsw01.framework.objects.Sprite;

import kr.ac.tukorea.ge.jsw01.framework.res.BitmapPool;
import kr.ac.tukorea.ge.jsw01.framework.res.Metrics;
import kr.ac.tukorea.ge.jsw01.framework.res.Sound;
import kr.ac.tukorea.ge.jsw01.s2016180039.slashit.R;

public class Slime extends Sprite implements Recyclable {
    private static Random random = new Random();
    private int minimalSlash;
    private int slashNum;
    private Type slimeType;
    private float xSpeed, ySpeed;
    private final float gravity = Metrics.height / 150;;
    private float fSize;
    private Paint paint = new Paint();
    private RectF rect = new RectF();
    private float rSpeed;
    private boolean isCircle;

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

    public Slime() {
    }

    private void init(float size, Type type) {
        boolean dirRight = random.nextBoolean();

        if (dirRight == false) {
            x = Metrics.width + Metrics.height / size / 2;
        }
        else{
            x = -Metrics.height / size / 2;
        }
        y = Metrics.height - random.nextInt(Metrics.height / 5) * (size / 4);

        bitmap = BitmapPool.get(BITMAP_IDS[random.nextInt(BITMAP_IDS.length)]);
        fSize = size;

        if (type == Type.RANDOM) {
            type = Type.values()[random.nextInt(Type.COUNT.ordinal())];
        }

        slimeType = type;

        if(slimeType == Type.special_penalty) {
            minimalSlash = random.nextInt(2) + 1;
        } else {
            minimalSlash = 0;
        }

        slashNum = 0;

        paint.setColor(random.nextInt());

        rSpeed = random.nextInt(30);

        if (dirRight == true) {
            xSpeed = Metrics.width / 15 * size;
        } else {
            xSpeed = -(Metrics.width / 15 * size);
        }
        ySpeed = -(Metrics.height / 2 + random.nextInt((int)(Metrics.height / 4 / size)));

        isCircle = random.nextBoolean();
    }

    public static Slime get() {
        Slime slime = (Slime) RecycleBin.get(Slime.class);
        Size size = null;
        Type type = null;
        float tSize = 0;
        int rnd_size, rnd_type;

        if (slime == null) {
            slime = new Slime();
        }

        if(MainScene.stage == 1) {
            rnd_size = random.nextInt(10);

            if(rnd_size < 6) {
                size = Size.big;
            }
            else{
                size = Size.medium;
            }
            type = Type.normal;
        }
        else if(MainScene.stage == 2) {
            rnd_size = random.nextInt(10);

            if(rnd_size < 2) {
                size = Size.big;
            }
            else if(rnd_size < 6) {
                size = Size.medium;
            }
            else{
                size = Size.small;
            }
            type = Type.normal;
        }
        else if(MainScene.stage == 3) {
            rnd_size = random.nextInt(10);
            rnd_type = random.nextInt(10);

            if(rnd_size < 2) {
                size = Size.big;
            }
            else if(rnd_size < 6) {
                size = Size.medium;
            }
            else{
                size = Size.small;
            }

            if(rnd_type < 4) {
                type = Type.special_penalty;
            }
            else{
                type = Type.normal;
            }
        }
        else if(MainScene.stage == 4) {
            rnd_size = random.nextInt(10);
            rnd_type = random.nextInt(10);

            if(rnd_size < 2) {
                size = Size.big;
            }
            else if(rnd_size < 6) {
                size = Size.medium;
            }
            else{
                size = Size.small;
            }

            if(rnd_type < 4) {
                type = Type.special_fast;
            }
            else{
                type = Type.normal;
            }
        }
        else if(MainScene.stage == 5) {
            rnd_size = random.nextInt(10);

            if(rnd_size < 1) {
                size = Size.big;
            }
            else if(rnd_size < 4) {
                size = Size.medium;
            }
            else {
                size = Size.small;
            }

            type = Type.RANDOM;
        }

        if (size == Size.big) {
            tSize = random.nextInt(3) + 5;
        } else if (size == Size.medium) {
            tSize = random.nextInt(3) + 9;
        } else if (size == Size.small) {
            tSize = random.nextInt(3) + 12;
        }

        slime.init(tSize, type);

        Sound.playEffect(MainScene.SFX_SPAWN_IDS[random.nextInt(MainScene.SFX_SPAWN_IDS.length)]);

        return slime;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (Math.abs(event.getX() - this.x) < Metrics.height / fSize / 3 &&
                Math.abs(event.getY() - this.y) < Metrics.height / fSize / 3) {
            MainScene.get().score.add(3 * (slashNum + 1));
            GenBlob();
            Divide();
            Sound.playEffect(MainScene.SFX_HIT_IDS[random.nextInt(MainScene.SFX_HIT_IDS.length)]);
        }

        return true;
    }

    public void GenBlob(){
        Blob blob;
        int num = random.nextInt(5) + 5;

        for(int n = 0; n < num; n++){
            blob = new Blob(x, y, Metrics.height / fSize / 5 + random.nextInt((int)(Metrics.height / fSize / 5)),
                    random, paint.getColor());
            MainScene.get().add(MainScene.Layer.blob.ordinal(), blob);
        }
    }

    public void Divide(){
        float beforeSize = fSize;
        float beforeX = x;
        float beforeY = y;
        float beforeXSpeed = xSpeed;
        int beforeSlshNum = slashNum;
        int beforePaintNum = paint.getColor();
        int setMinimalSlash = minimalSlash;
        float fastDivSpeed = 1;
        boolean beforeIsCircle = isCircle;

        if(minimalSlash <= beforeSlshNum + 1){
            setMinimalSlash = 0;
        }

        if(slimeType == Type.special_fast){
            fastDivSpeed = 2f;
        }

        if(beforeSize * 1.5f > 35f) {
            MainScene.get().score.add(30);
            MainScene.get().remove(this);
            Sound.playEffect(MainScene.SFX_DEATH_IDS[random.nextInt(MainScene.SFX_DEATH_IDS.length)]);
            return;
        }

        init(beforeSize * 1.5f, slimeType);
        x = beforeX;
        y = beforeY;
        xSpeed = beforeXSpeed * 0.6f * fastDivSpeed;
        ySpeed *= 0.7f;
        slashNum = beforeSlshNum + 1;
        paint.setColor(beforePaintNum);
        minimalSlash = setMinimalSlash;
        isCircle = beforeIsCircle;

        Slime dSlime = new Slime();
        dSlime.init(beforeSize * 1.5f, slimeType);
        dSlime.x = beforeX;
        dSlime.y = beforeY;
        dSlime.xSpeed = -beforeXSpeed * 0.6f * fastDivSpeed;
        dSlime.ySpeed *= 0.7f;
        dSlime.slashNum = beforeSlshNum + 1;
        dSlime.paint.setColor(beforePaintNum);
        dSlime.minimalSlash = setMinimalSlash;
        dSlime.isCircle = beforeIsCircle;
        MainScene.get().add(MainScene.Layer.slime.ordinal(), dSlime);
    }

    @Override
    public void update(float frameTime) {
        x += xSpeed * frameTime;
        y += ySpeed * frameTime;
        ySpeed += gravity;
        rSpeed += rSpeed * frameTime;

        if (x > Metrics.width + Metrics.height / 10 || x < -Metrics.height / 10
                || y > Metrics.height + Metrics.height / 10) {

            if(minimalSlash > slashNum){
                MainScene.get().score.add(-(minimalSlash - slashNum) * (minimalSlash - slashNum) * 5);
                if(MainScene.get().score.get() < 0){
                    MainScene.get().score.set(0);
                }
            }
            MainScene.get().remove(this);
            return;
        }

        setDstRect(Metrics.height / fSize / 2, Metrics.height / fSize / 2);
    }

    @Override
    public void draw(Canvas canvas) {
        float r = Metrics.height / fSize / 8;

        if(isCircle){
            r = Metrics.height / fSize / 2;
        }

        if(minimalSlash > slashNum){
            float r_ = r + Metrics.height / fSize / 8;

            Paint tempPaint = new Paint();
            tempPaint.setColor(Color.BLACK);

            canvas.save();

            rect.set(x - Metrics.height / fSize / 2 - Metrics.height / fSize / 8, y - Metrics.height / fSize / 2 - Metrics.height / fSize / 8,
                    x + Metrics.height / fSize / 2 + Metrics.height / fSize / 8, y + Metrics.height / fSize / 2 + Metrics.height / fSize / 8);
            canvas.rotate(rSpeed, rect.centerX(), rect.centerY());
            canvas.drawRoundRect(rect, r_, r_, tempPaint);

            canvas.restore();
        }

        if(slimeType == Type.special_fast){
            float r_ = r - Metrics.height / fSize / 8;

            Paint tempPaint = new Paint();
            tempPaint.setColor(Color.YELLOW);

            canvas.save();

            rect.set(x - Metrics.height / fSize / 2 + Metrics.height / fSize / 8, y - Metrics.height / fSize / 2 + Metrics.height / fSize / 8,
                    x + Metrics.height / fSize / 2 - Metrics.height / fSize / 8, y + Metrics.height / fSize / 2 - Metrics.height / fSize / 8);
            canvas.rotate(rSpeed, rect.centerX(), rect.centerY());
            canvas.drawRoundRect(rect, r_, r_, tempPaint);

            canvas.restore();
        }

        canvas.save();

        rect.set(x - Metrics.height / fSize / 2, y - Metrics.height / fSize / 2,
                x + Metrics.height / fSize / 2, y + Metrics.height / fSize / 2);
        canvas.rotate(rSpeed, rect.centerX(), rect.centerY());
        canvas.drawRoundRect(rect, r, r, paint);

        canvas.restore();
        super.draw(canvas);
    }

    @Override
    public void finish() {

    }
}
