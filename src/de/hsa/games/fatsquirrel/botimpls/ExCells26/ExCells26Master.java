package de.hsa.games.fatsquirrel.botimpls.ExCells26;

import de.hsa.games.fatsquirrel.XY;
import de.hsa.games.fatsquirrel.XYsupport;
import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.botapi.OutOfViewException;
import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.botimpls.ExCells26.Helper.*;
import de.hsa.games.fatsquirrel.botimpls.ExCells26.Mini.MiniType;
import de.hsa.games.fatsquirrel.core.FullFieldException;
import de.hsa.games.fatsquirrel.core.entity.EntityType;

public class ExCells26Master implements BotController {

    private BotCom botCom;
    private Cell currentCell;
    private boolean firstCall = true;
    private ControllerContext view;

    public ExCells26Master(BotCom botCom) {
        this.botCom = botCom;
    }

    @Override
    public void nextStep(ControllerContext view) {
        toDoAtStartOfNextStep(view);
        if (firstCall) {
            initOfMaster(view);
        }

        if (currentCell.getQuadrant().equals(view.locate())) {
            try {
                botCom.expand();
            } catch (NoConnectingNeighbourException e) {
                e.printStackTrace();
            }
            if(view.getEnergy() >= 100){
                spawningReaper();
            } else{
                changeCurrentCell();
            }


            changeCurrentCell();
        }

        moveToCurrentCell();

    }

    private void changeCurrentCell(){
        //Todo: adding Epsilon distance
        currentCell = currentCell.getNextCell();
        //Todo: remove after debugging
        System.out.println("\nGo to nextCell: " + currentCell);
    }

    private void toDoAtStartOfNextStep(ControllerContext view) {
        botCom.positionOfExCellMaster = view.locate();
        botCom.checkAttendance(view.getRemainingSteps());
        this.view = view;
    }

    private void spawningReaper() {
        try {
            if (currentCell.getMiniSquirrel() == null) {
                botCom.setNextMini(MiniType.REAPER);
                botCom.setForNextMini(currentCell);
                XY spawnDirection = currentCell.getNextCell().getQuadrant().minus(view.locate());
                spawnDirection = XYsupport.oppositeVector(XYsupport.normalizedVector(spawnDirection));
                if (view.getEntityAt(view.locate().plus(spawnDirection)) == EntityType.NONE) {
                    view.spawnMiniBot(spawnDirection, 100);
                } else {
                    //Todo: adding can't spawn
                }
            }
        } catch (SpawnException e) {
            //Todo: add to Log
            e.printStackTrace();
        } catch (OutOfViewException e) {
            e.printStackTrace();
        }
    }

    private void moveToCurrentCell() {
        PathFinder pf = new PathFinder();
        XY betterMove = XY.ZERO_ZERO;
        try {
            betterMove = pf.directionTo(view.locate(), currentCell.getQuadrant(), view);
        } catch (FullFieldException e) {
            //Todo: add to Log
            // e.printStackTrace();
        }
        if(betterMove != XY.ZERO_ZERO){
            view.move(betterMove);
        } else{
            changeCurrentCell();
        }

    }

    private void initOfMaster(ControllerContext view) {
        botCom.setStartPositionOfMaster(view.locate());
        //Todo: set right after implementation of reconMini
        //botCom.setFieldLimit(view.locate());
        botCom.setFieldLimit(new XY(80, 60));
        botCom.setMaster(this);
        botCom.init();
        try {
            botCom.expand();
            botCom.expand();
            botCom.expand();
            botCom.expand();
        } catch (NoConnectingNeighbourException e) {
            //Todo: add to Log
            //e.printStackTrace();
        }
        firstCall = false;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }
}
