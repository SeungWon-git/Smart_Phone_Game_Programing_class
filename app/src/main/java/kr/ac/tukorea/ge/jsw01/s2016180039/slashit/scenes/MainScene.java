package kr.ac.tukorea.ge.jsw01.s2016180039.slashit.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.jsw01.framework.game.Scene;
import kr.ac.tukorea.ge.jsw01.framework.interfaces.GameObject;
import kr.ac.tukorea.ge.jsw01.framework.objects.Score;
import kr.ac.tukorea.ge.jsw01.framework.res.Metrics;
import kr.ac.tukorea.ge.jsw01.framework.util.Gauge;
import kr.ac.tukorea.ge.jsw01.s2016180039.slashit.R;

public class MainScene extends Scene {
    public static final String PARAM_STAGE_INDEX = "stage_index";
    private static MainScene singleton;
    public static Score score;
    private static ArrayList<GameObject> slimes;
    private static Gauge timer;

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

        timer = new Gauge(Metrics.size(R.dimen.timer_guage_thickness_fg), R.color.yellow,
                Metrics.size(R.dimen.timer_guage_thickness_bg), R.color.red, Metrics.width / 2);
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

    @Override
    public void draw(Canvas canvas) {
        timer.draw(canvas, Metrics.width / 2, Metrics.height / 10);

        draw(canvas, sceneStack.size() - 1);
    }

    @Override
    public void update(int elapsedNanos) {
        frameTime = (float) (elapsedNanos / 1_000_000_000f);
        elapsedTime += frameTime;
        for (ArrayList<GameObject> gameObjects : layers) {
            for (GameObject gobj : gameObjects) {
                gobj.update(frameTime);
            }
        }

        timer.setValue((60.0f - elapsedTime) / 60.0f);
    }
}