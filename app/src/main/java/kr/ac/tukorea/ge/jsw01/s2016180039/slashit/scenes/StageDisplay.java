package kr.ac.tukorea.ge.jsw01.s2016180039.slashit.scenes;

import kr.ac.tukorea.ge.jsw01.framework.objects.Sprite;
import kr.ac.tukorea.ge.jsw01.framework.res.Metrics;

public class StageDisplay extends Sprite {

    public StageDisplay(int x, int y, float w, float h, int bitmapResId){
        super(x, y, w, h, bitmapResId);
    }

    @Override
    public void update(float frameTime){
        y -= Metrics.height / 3 * frameTime;

        setDstRect(Metrics.width, Metrics.width / 4);

        if(y < -Metrics.height / 3)
            MainScene.get().remove(this);
    }
}
