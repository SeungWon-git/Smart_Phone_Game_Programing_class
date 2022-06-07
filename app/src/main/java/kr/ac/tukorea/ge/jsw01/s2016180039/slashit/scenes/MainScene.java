package kr.ac.tukorea.ge.jsw01.s2016180039.slashit.scenes;

import kr.ac.tukorea.ge.jsw01.framework.game.Scene;

public class MainScene extends Scene {
    public static final String PARAM_STAGE_INDEX = "stage_index";
    private static MainScene singleton;
    public static MainScene get() {
        if (singleton == null) {
            singleton = new MainScene();
        }
        return singleton;
    }

    public void setMapIndex(int stageIndex) {

    }

    public enum Layer {
        some, COUNT;
    }

    public void init() {
        super.init();

        initLayers(Layer.COUNT.ordinal());
    }
}