/*
Assumptions : Enemy will send the same unit types as present with Lengaburu (Example - Lengaburu has 4 types of units. So enemy troop
              contains units of same type only.
 */

import Army.ArmyOperations;
import Army.ArmyUnitFactory.ArmyUnitFactory;
import Army.ArmyUnitTypes.ArmyUnitType;
import constants.ArmyInventory;
import constants.ArmyUnitTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class BattleField {

    public static void main(String[] args) {
        System.out.println("This is the battle field");

        try {
            driverFunction();
        }
        catch(Exception e)
        {
            System.out.println("BattleField::main() - exception occurred");
        }
    }

    private static void driverFunction() throws Exception {
        int strength[] = {100, 101, 20, 5};
        int power[] = {1,2,4,8};
        List<ArmyUnitType> enemyTroop = new ArrayList<>();
        ArmyUnitFactory armyUnitFactory = new ArmyUnitFactory();
        for(ArmyUnitTypeEnum e : ArmyUnitTypeEnum.values())
        {
            ArmyUnitType armyUnitType = armyUnitFactory.getArmyUnit(e.getId());
            armyUnitType.setStrength(strength[e.getId()]);
            armyUnitType.setPower(power[e.getId()]);

            enemyTroop.add(armyUnitType);
        }

        ArmyOperations lengaburuArmyOperations = new ArmyOperations();


        if(lengaburuArmyOperations.onAttack(enemyTroop, 2))
        {
            System.out.println("Yay! Lengaburu wins\n Deployed troops : \n H\tE\tAT\tSG\t");

            List<ArmyUnitType> lengaburuLeftTroop = lengaburuArmyOperations.getLengaburuTroops();
            for(int i = 0; i < lengaburuLeftTroop.size(); i++)
                System.out.print((ArmyInventory.strength[i] - (lengaburuLeftTroop.get(i).getStrength())) + "\t");
        }

        else
            System.out.println("Ugh! Hard luck Lengaburu\n");


    }
}
