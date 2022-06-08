package kr.ac.tukorea.ge.jsw01.s2016180039.slashit.scenes;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.jsw01.framework.interfaces.GameObject;

public class SlimeGen implements GameObject {
    private static float genInterval;
    private static float time;

    public SlimeGen() {
        genInterval = 3.0f;
    }

    @Override
    public void update(float frameTime) {
        time += frameTime;
        switch (MainScene.stage){
            case 1:
                genInterval = 3.0f;
                break;
            case 2:
                genInterval = 2.0f;
                break;
            case 3:
                genInterval = 1.0f;
                break;
        }
        if (time > genInterval) {
            spawn();
            time -= genInterval;
        }
    }

    private void spawn() {
        Slime slime = Slime.get();

        MainScene.get().add(MainScene.Layer.slime.ordinal(), slime);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}