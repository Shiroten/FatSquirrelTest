package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.XY;
import de.hsa.games.fatsquirrel.core.entity.Entity;
import de.hsa.games.fatsquirrel.core.entity.EntityType;
import de.hsa.games.fatsquirrel.gui.ImplosionContext;

import java.util.ArrayList;

public interface BoardView {

    /**
     *
     * @param xy Position to check the EntityType
     * @return the EntityType at the Position xy
     */
    EntityType getEntityType(XY xy);

    /**
     *
     * @param xy Position of the Entity
     * @return  Entity of the Position xy
     */
    Entity getEntity(XY xy);

    /**
     *
     * @return the size of the board in XY
     */
    XY getSize();

    /**
     *
     * @return list of all the implosions
     */
    ArrayList<ImplosionContext> getImplosions();

    /**
     *  Used to decrease the tick counter of the implosion
     */
    void tickImplosions();
}
