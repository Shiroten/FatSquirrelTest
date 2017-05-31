package de.hsa.games.fatsquirrel.core.entity.character;

import de.hsa.games.fatsquirrel.XY;
import de.hsa.games.fatsquirrel.XYsupport;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.core.entity.EntityContext;
import de.hsa.games.fatsquirrel.core.entity.EntityType;

public abstract class MasterSquirrel extends PlayerEntity {

    protected BotControllerFactory factory;

    private static final int START_ENERGY = 1000;
    public static final EntityType type = EntityType.MASTERSQUIRREL;

    public EntityType getEntityType() {
        return type;
    }

    public MasterSquirrel(int id, XY coordinate) {
        super(id, coordinate);
        this.energy = START_ENERGY;
    }

    @Override
    public void nextStep(EntityContext context) {}

    public boolean mySquirrel(MiniSquirrel squirrelToCheck) {
        return this == squirrelToCheck.getDaddy();
    }

    public BotControllerFactory getFactory() {
        return factory;
    }
}
