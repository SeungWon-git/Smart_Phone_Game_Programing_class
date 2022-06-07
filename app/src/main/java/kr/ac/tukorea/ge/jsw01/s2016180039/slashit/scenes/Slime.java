package kr.ac.tukorea.ge.jsw01.s2016180039.slashit.scenes;

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

    public Slime(int iSize, Type type, boolean dirRight) {
        super(-Metrics.height / iSize / 2, Metrics.height - random.nextInt(Metrics.height / 5) * (iSize / 4),
                Metrics.height / iSize, Metrics.height / iSize, BITMAP_IDS[random.nextInt(BITMAP_IDS.length)]);

        if (dirRight == false) {
            x = Metrics.width + Metrics.height / iSize / 2;
        }

        gravity = Metrics.height / 150;
    }

    private void init(int iSize, Type type, boolean dirRight) {
        if (type == Type.RANDOM) {
            type = Type.values()[random.nextInt(Type.COUNT.ordinal())];
        }
        this.slimeType = type;

        if (dirRight == true) {
            this.xSpeed = Metrics.width / 15 * iSize;
        } else {
            this.xSpeed = -(Metrics.width / 15 * iSize);
        }
        this.ySpeed = -(Metrics.height / 2 + random.nextInt(Metrics.height / 4 / iSize));
    }

    public static Slime get(Size size, Type type) {
        Slime slime = (Slime) RecycleBin.get(Slime.class);
        boolean dirRight = random.nextBoolean();
        int iSize = 0;

        if (size == Size.big) {
            iSize = random.nextInt(3) + 5;
        } else if (size == Size.medium) {
            iSize = random.nextInt(3) + 9;
        } else if (size == Size.small) {
            iSize = random.nextInt(3) + 12;
        }

        if (slime == null) {
            slime = new Slime(iSize, type, dirRight);
        } else {
            if (dirRight == false) {
                slime.x = Metrics.width + Metrics.height / iSize / 2;
            }
            else{
                slime.x = -Metrics.height / iSize / 2;
            }
            slime.y = Metrics.height - random.nextInt(Metrics.height / 5) * (iSize / 4);
            slime.setDstRect(Metrics.height / iSize, Metrics.height / iSize);
            slime.bitmap = BitmapPool.get(BITMAP_IDS[random.nextInt(BITMAP_IDS.length)]);
        }

        slime.init(iSize, type, dirRight);

        return slime;
    }

    @Override
    public void update(float frameTime) {
        x += xSpeed * frameTime;
        y += ySpeed * frameTime;
        ySpeed += gravity;

        if (x > Metrics.width + Metrics.height / 10 || x < -Metrics.height / 10
                || y > Metrics.height + Metrics.height / 10){
           MainScene.get().remove(this);
           return;
        }

        setDstRectWithRadius();
    }

    @Override
    public void finish() {

    }
}
