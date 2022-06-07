package kr.ac.tukorea.ge.jsw01.s2016180039.slashit.scenes;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.jsw01.framework.interfaces.GameObject;

public class SlimeGen implements GameObject {
    private float genInterval;
    private int stage;
    private float time;

    public SlimeGen() {
        genInterval = 3.0f;
        stage = 1;
    }

    @Override
    public void update(float frameTime) {
        time += frameTime;
        switch (stage){
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
        Slime slime = null;

        if(stage == 1) {
            slime = Slime.get(Slime.Size.big, Slime.Type.normal);
        }
        else if(stage == 2) {
            slime = Slime.get(Slime.Size.medium, Slime.Type.normal);
        }
        else if(stage == 3) {
            slime = Slime.get(Slime.Size.small, Slime.Type.normal);
        }
        else if(stage == 4) {
            //slime = Slime.get(Slime.Size.big, Slime.Type.normal);
        }
        else if(stage == 5) {
            //slime = Slime.get(Slime.Size.big, Slime.Type.normal);
        }


        MainScene.get().add(MainScene.Layer.slime.ordinal(), slime);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}