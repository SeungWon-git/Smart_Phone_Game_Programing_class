package kr.ac.tukorea.ge.jsw01.s2016180039.slashit.scenes;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.jsw01.framework.game.Scene;
import kr.ac.tukorea.ge.jsw01.framework.interfaces.GameObject;
import kr.ac.tukorea.ge.jsw01.framework.objects.Score;
import kr.ac.tukorea.ge.jsw01.s2016180039.slashit.R;

public class MainScene extends Scene {
    public static final String PARAM_STAGE_INDEX = "stage_index";
    private static MainScene singleton;
    public Score score;
    private static ArrayList<GameObject> slimes;

    public static MainScene get() {
        if (singleton == null) {
            singleton = new MainScene();
        }
        return singleton;
    }

    public enum Layer {
        bg, slime, score, controller, COUNT;
    }

    public void init() {
        super.init();

        initLayers(Layer.COUNT.ordinal());

        add(Layer.controller.ordinal(), new SlimeGen());

        score = new Score(R.mipmap.numbers, 0f, 0f, 200f);

        score.set(0);

        add(Layer.score.ordinal(), score);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        slimes = objectsAt(Layer.slime.ordinal());

        for(GameObject gameObject: slimes){
            Slime slime = (Slime) gameObject;
            slime.onTouchEvent(event);
        }

        return true;
    }
}