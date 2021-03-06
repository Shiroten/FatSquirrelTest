package de.hsa.games.fatsquirrel.core.entity;

import de.hsa.games.fatsquirrel.XY;
import de.hsa.games.fatsquirrel.XYsupport;
import de.hsa.games.fatsquirrel.core.entity.squirrels.PlayerEntity;
import javafx.scene.paint.Color;


/**
 * The BadBeast chases squirrels and bites them, reducing their points
 * Extends Character
 */
public class BadBeast extends Character {
    public static final int START_ENERGY = -150;
    private static final EntityType type = EntityType.BADBEAST;
    private static final Color ENTITYCOLOR = Color.color(1, 0.0392, 0);
    private static final Color ENTITYTEXTCOLOR = Color.gray(0);
    private static final String defaultName = "BadBeast";
    public int moveCounter = 0;
    private int lives;

    public BadBeast(int id, XY coordinate) {
        super(START_ENERGY, id, coordinate, ENTITYCOLOR, ENTITYTEXTCOLOR, defaultName);
        this.lives = 7;
    }

    @Override
    public EntityType getEntityType(){
        return type;
    }

    /**
     * reduces the lives of the beast by one
     */
    public void bites() {
        lives--;
    }

    /**
     * Lives left
     * @return current number of lives
     */
    public int getLives() {
        return this.lives;
    }

    @Override
    public void nextStep(EntityContext context ) {

        int waitingTime = context.getBEAST_MOVE_TIME_IN_TICKS();

        if (moveCounter % (waitingTime + 1) == 0) {
            PlayerEntity pe = context.nearestPlayerEntity(this.getCoordinate());
            XY distance = pe.getCoordinate().minus(this.getCoordinate());

            if (distance.length() < context.getBADBEAST_VIEW_DISTANCE()) {
                context.tryMove(this, XYsupport.normalizedVector(distance));
            } else {
                context.tryMove(this, XYsupport.randomDirection());
            }
        }
        moveCounter++;
    }
    @Override
    public void updateEnergy(int energyDifference){
        this.energy += energyDifference;
        if(energy > 0)
            energy = 0;
    }
}
