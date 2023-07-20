package com.mygdx.starfishcollectortouchscreench13;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LevelScreen2 extends BaseScreen {
    private Turtle turtle;

    private boolean win;

    private Label starfishLabel;

    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1200, 900);

        BaseActor.setWorldBounds(ocean);

        new Starfish(400, 400, mainStage);
        new Starfish(500, 100, mainStage);

        new Rock(200, 150, mainStage);
        new Rock(100, 300, mainStage);

        new Shark(100, 200, mainStage);
        new Shark(200, 300, mainStage);

        turtle = new Turtle(20, 20, mainStage);

        win = false;

        starfishLabel = new Label("Starfish Left: ", BaseGame.labelStyle);
        starfishLabel.setColor(Color.CYAN);
        starfishLabel.setPosition(20, 520);
        uiStage.addActor(starfishLabel);
    }

    public void update(float dt) {

//        if (turtle.isDead())
//            return;

        for (BaseActor rockActor : BaseActor.getList(mainStage, "com.mygdx.starfishcollectortouchscreench13.Rock"))
            turtle.preventOverlap(rockActor);

        for (BaseActor starfishActor : BaseActor.getList(mainStage, "com.mygdx.starfishcollectortouchscreench13.Starfish")) {
            Starfish starfish = (Starfish) starfishActor;
            if (turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.collect();
                Whirlpool whirl = new Whirlpool(0, 0, mainStage);
                whirl.centerAtActor(starfish);
                whirl.setOpacity(0.25f);
            }
        }

        if (BaseActor.getList(mainStage, "com.mygdx.starfishcollectortouchscreench13.Starfish").size() == 0 && !win) {
            win = true;
            turtle.stop();

            BaseActor youWinMessage = new BaseActor(0, 0, uiStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }

        for (BaseActor sharkActor : BaseActor.getList(mainStage, "com.mygdx.starfishcollectortouchscreench13.Shark")) {
            Shark shark = (Shark) sharkActor;
            if (turtle.overlaps(shark)) {
                turtle.die();
                BaseActor gameOverMessage = new BaseActor(0, 0, uiStage);
                gameOverMessage.loadTexture("game-over.png");
                gameOverMessage.centerAtPosition(400, 300);
                gameOverMessage.setOpacity(0);
                gameOverMessage.addAction(Actions.delay(1));
                gameOverMessage.addAction(Actions.after(Actions.fadeIn(1)));
            }
        }

        starfishLabel.setText("Starfish Left: " + BaseActor.getList(mainStage, "com.mygdx.starfishcollectortouchscreench13.Starfish").size());
    }
}
