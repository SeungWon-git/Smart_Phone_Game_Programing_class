package kr.ac.tukorea.ge.jsw01.s2016180039.slashit.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.jsw01.framework.game.Scene;
import kr.ac.tukorea.ge.jsw01.framework.interfaces.GameObject;
import kr.ac.tukorea.ge.jsw01.framework.objects.Score;
import kr.ac.tukorea.ge.jsw01.framework.objects.Sprite;
import kr.ac.tukorea.ge.jsw01.framework.res.Metrics;
import kr.ac.tukorea.ge.jsw01.framework.res.Sound;
import kr.ac.tukorea.ge.jsw01.framework.util.Gauge;
import kr.ac.tukorea.ge.jsw01.s2016180039.slashit.R;

public class MainScene extends Scene {
    public static final String PARAM_STAGE_INDEX = "stage_index";
    private static MainScene singleton;
    public static Score score;
    private static ArrayList<GameObject> slimes;
    private static Gauge timer;
    public static float fTimer;
    private static Sprite bg;
    private static StageDisplay stageDisplay;
    public static int stage = 1;
    private boolean isGameOver = false;

    public static enum Layer {
        bg, slime, blob, score, stage, gameover, controller, COUNT;
    }

    public static int[] BG_BITMAP_IDS = {
        R.mipmap.grass1, R.mipmap.grass2, R.mipmap.grass3, R.mipmap.overgrowth, R.mipmap.dirt
    };

    public static int[] STAGE_BITMAP_IDS = {
            R.mipmap.stage1, R.mipmap.stage2, R.mipmap.stage3,
            R.mipmap.stage4, R.mipmap.stage5
    };

    public static int[] BGM_SOUND_IDS = {
            R.raw.bgm1, R.raw.bgm2, R.raw.bgm3, R.raw.bgm4, R.raw.bgm5
    };

    public static int[] SFX_SPAWN_IDS = {
            R.raw.spawn1, R.raw.spawn2, R.raw.spawn3,
            R.raw.spawn4, R.raw.spawn5, R.raw.spawn6,
            R.raw.spawn7, R.raw.spawn8, R.raw.spawn9,
            R.raw.spawn10, R.raw.spawn11, R.raw.spawn12
    };

    public static int[] SFX_DEATH_IDS = {
            R.raw.death1, R.raw.death2, R.raw.death3,
            R.raw.death4, R.raw.death5, R.raw.death6
    };

    public static int[] SFX_HIT_IDS = {
            R.raw.hit1, R.raw.hit2, R.raw.hit3,
            R.raw.hit4, R.raw.hit5, R.raw.hit6
    };

    public static MainScene get() {
        if (singleton == null) {
            singleton = new MainScene();
        }
        return singleton;
    }


    public void init() {
        super.init();

        initLayers(Layer.COUNT.ordinal());

        bg = new Sprite(Metrics.width / 2, Metrics.height / 2, Metrics.width, Metrics.height,
                BG_BITMAP_IDS[stage - 1]);

        add(Layer.bg.ordinal(), bg);

        Sound.playMusic(BGM_SOUND_IDS[stage - 1]);

        for(int sfx: SFX_SPAWN_IDS) {
            Sound.loadEffect(sfx);
        }

        for(int sfx: SFX_DEATH_IDS) {
            Sound.loadEffect(sfx);
        }

        for(int sfx: SFX_HIT_IDS) {
            Sound.loadEffect(sfx);
        }

        add(Layer.controller.ordinal(), new SlimeGen());

        score = new Score(R.mipmap.numbers, 0f, 0f, 200f);

        score.set(0);

        add(Layer.score.ordinal(), score);

        stageDisplay = new StageDisplay(Metrics.width / 2, Metrics.height / 2 + Metrics.height / 4,
                Metrics.width, Metrics.width / 4, STAGE_BITMAP_IDS[stage - 1]);

        add(Layer.stage.ordinal(), stageDisplay);

        timer = new Gauge(Metrics.size(R.dimen.timer_guage_thickness_fg), R.color.yellow,
                Metrics.size(R.dimen.timer_guage_thickness_bg), R.color.red, Metrics.width / 2);
    }

    private void CheckGame(){
        if(score.get() > 500){
            stage++;
            if(stage <= 5) {
                init();
            }
            else {
                GameOver();
            }
        }
        else {
               GameOver();
        }
    }

    private void GameOver(){
        if(!isGameOver) {
            Sprite gameOver = new Sprite(Metrics.width / 2, Metrics.height / 2, Metrics.width, Metrics.width,
                    R.mipmap.gameover);
            add(Layer.gameover.ordinal(), gameOver);
            isGameOver = true;
        }
    }

    @Override
    public void end() {
        Sound.stopMusic();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isGameOver){
            return false;
        }

        slimes = objectsAt(Layer.slime.ordinal());

        for(GameObject gameObject: slimes){
            Slime slime = (Slime) gameObject;
            slime.onTouchEvent(event);
        }

        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        draw(canvas, sceneStack.size() - 1);

        timer.draw(canvas, Metrics.width / 2, Metrics.height / 10);
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

        fTimer = 60.0f - elapsedTime;
        if(fTimer < 0){
            CheckGame();
        }

        timer.setValue(fTimer / 60.0f);
    }
}