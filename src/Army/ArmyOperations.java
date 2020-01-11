package Army;

import Army.ArmyUnitFactory.ArmyUnitFactory;
import Army.ArmyUnitTypes.ArmyUnitType;
import constants.ArmyInventory;
import constants.ArmyUnitTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class ArmyOperations {

    private List<ArmyUnitType> lengaburuTroop = new ArrayList<>();
    private ArmyUnitFactory armyUnitFactory = new ArmyUnitFactory();
    private int strengthFactor = 1;
    private List<ArmyUnitType> enemyTroop = new ArrayList<>();


    private void prepareArmy() throws Exception {
        for(ArmyUnitTypeEnum e : ArmyUnitTypeEnum.values())
        {
            ArmyUnitType armyUnitType = armyUnitFactory.getArmyUnit(e.getId());
            armyUnitType.setStrength(ArmyInventory.strength[e.getId()]);
            armyUnitType.setPower(ArmyInventory.power[e.getId()]);

            lengaburuTroop.add(armyUnitType);
        }
    }

    private boolean deployTroops(){

        if(deployCounterParts())
            return true;
        else if(deployLowerUnit())
            return true;
        else if(deployUpperUnit())
            return  true;

        return false;
    }

    private boolean deployLowerUnit() {
        for(int i = lengaburuTroop.size() - 1; i > 0; i--) {
            int enemyCount = enemyTroop.get(i).getStrength();
            if(enemyCount > 0){
                int lengaburuCount = lengaburuTroop.get(i-1).getStrength();
                int count = lengaburuCount < enemyCount ? lengaburuCount : enemyCount;

                enemyTroop.get(i).setStrength(enemyCount - count);
                lengaburuTroop.get(i-1).setStrength(lengaburuCount - count);
            }
        }

        return isEnemyCovered();
    }

    private boolean deployUpperUnit() {
        for(int i = 0; i < lengaburuTroop.size() - 1; i++){
            int enemyCount = enemyTroop.get(i).getStrength();
            if(enemyCount > 0){
                int lengaburuCount = lengaburuTroop.get(i+1).getStrength();
                int count = getNormalisedCount(enemyCount, lengaburuCount, strengthFactor*ArmyInventory.powerFactor);

                lengaburuTroop.get(i+1).setStrength(lengaburuCount - count);
                enemyTroop.get(i).setStrength(enemyCount - (count * strengthFactor * ArmyInventory.powerFactor));
            }
        }

        return isEnemyCovered();
    }

    private boolean deployCounterParts() {
        for(int i = 0; i < lengaburuTroop.size(); i++) {
            int lengaburuCount = lengaburuTroop.get(i).getStrength();
            int enemyCount = enemyTroop.get(i).getStrength();
            int count = getNormalisedCount(enemyCount, lengaburuCount, strengthFactor);
            lengaburuTroop.get(i).setStrength(lengaburuCount - count);
            enemyTroop.get(i).setStrength(enemyCount - (count*strengthFactor));
        }

        return isEnemyCovered();
    }

    private boolean isEnemyCovered() {
        for(int i = 0; i < enemyTroop.size(); i++) {
            if(enemyTroop.get(i).getStrength() > 0)
                return false;
        }
        return true;
    }

    private int getNormalisedCount(int enemyCount, int total, int normalizationFactor){
        if(enemyCount == 0 || total == 0)
            return 0;
        if(enemyCount < normalizationFactor)
            return enemyCount;

        int remainderCount = enemyCount/normalizationFactor;
        if(enemyCount % normalizationFactor != 0)
            remainderCount++;

        if(remainderCount > total)
            return total;
        return remainderCount;
    }


    //lengaburuArmyStrengthFactor : this denotes the factor by which Lengaburu army is stronger than enemy. Example : its value is 2 (2X strong) for
    //               the case of Falicornia
    public boolean onAttack(List<ArmyUnitType> iEnemyTroop, int lengaburuArmyStrengthFactor){
        try {
            prepareArmy();
        }
        catch (Exception e)
        {
            System.out.println("ArmyOperations::onAttack() - Exception occurred while building army");
        }
        enemyTroop = iEnemyTroop;
        strengthFactor = lengaburuArmyStrengthFactor;
        boolean canLengaburuWin = deployTroops();

        return canLengaburuWin;
    }

    public List<ArmyUnitType> getLengaburuTroops(){
        return lengaburuTroop;
    }
}
