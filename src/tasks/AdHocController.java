/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;

import pursuitDomain.Cell;
import pursuitDomain.Environment;
import pursuitDomain.Predator;

/**
 *
 * @author J
 */
public class AdHocController extends TaskMode {

    @Override
    public int[] run(Predator p) {
        int[] outputDef = new int[2];

        int currentDistance = p.relCor[0] + p.relCor[1];
        int bestDistance = currentDistance;
        int bestRoute = -1;
        /*
        *   0-EAST
        *   1-WEST
        *   2-SOUTH
        *   3-NORTH
         */
        for (int x = 0; x < 4; x++) {
            int[] newCell = getNewCell(x, p.getCell());

            if (Environment.cellHasPredator(newCell)) {
                continue;
            }
            if (Environment.cellHasPrey(newCell)) {
                bestRoute = x;
                break;
            }

            int height = newCell[0] - p.preyCor[0];
            int width = newCell[1] - p.preyCor[1];

            int heightT = 10 - Math.abs(height);
            if (heightT == 10) {
                heightT = 0;
            }
            int widthT = 10 - Math.abs(width);
            if (widthT == 10) {
                widthT = 0;
            }

            width = Math.abs(width);
            height = Math.abs(height);
            widthT = Math.abs(widthT);
            heightT = Math.abs(heightT);

            int newDistance = height < heightT ? height : heightT;
            newDistance += width < widthT ? width : widthT;

            if (newDistance < bestDistance) {
                bestDistance = newDistance;
                bestRoute = x;
            }
        }

        if (currentDistance == bestDistance) {
            outputDef[0] = Environment.random.nextInt(2);
            outputDef[1] = Environment.random.nextInt(2);
        } else {
            switch (bestRoute) {
                case 0://EAST
                    outputDef[0] = 1;
                    outputDef[1] = 1;
                    break;
                case 1://WEST
                    outputDef[0] = 1;
                    outputDef[1] = 0;
                    break;
                case 2://SOUTH
                    outputDef[0] = 0;
                    outputDef[1] = 1;
                    break;
                default://NORTH
                    outputDef[0] = 0;
                    outputDef[1] = 0;
                    break;
            }
        }

        return outputDef;
    }

    private int[] getNewCell(int act, Cell cell) {
        int[] newCell = new int[2];
        newCell[0] = cell.getLine();
        newCell[1] = cell.getColumn();
        switch (act) {
            case 0://EAST
                newCell[1]++;
                break;
            case 1://WEST
                newCell[1]--;
                break;
            case 2://SOUTH
                newCell[0]++;
                break;
            default://NORTH
                newCell[0]--;
                break;
        }
        if (newCell[0] >= 10) {
            newCell[0] = 0;
        } else if (newCell[0] <= 0) {
            newCell[0] = 9;
        }
        if (newCell[1] >= 10) {
            newCell[1] = 0;
        } else if (newCell[1] <= 0) {
            newCell[1] = 9;
        }
        return newCell;
    }

    @Override
    public String toString() {
        return "AdHoc Controller - Tarefa 2";
    }

}
