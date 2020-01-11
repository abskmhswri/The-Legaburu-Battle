package Army.ArmyUnitFactory;

import Army.ArmyUnitTypes.*;

public class ArmyUnitFactory {

    private IllegalArgumentException illegalArgumentException;

    public ArmyUnitType getArmyUnit(int unitId) throws Exception{
        switch (unitId)
        {
            case 0:
                return getHorse();
            case 1:
                return getElephant();
            case 2:
                return getArmouredTank();
            case 3:
                return getSlingGun();
            default:
                throw illegalArgumentException;
        }
    }

    private ArmyUnitType getSlingGun() {
        return new SlingGun();
    }

    private ArmyUnitType getArmouredTank() {
        return new ArmouredTank();
    }

    private ArmyUnitType getElephant() {
        return new Elephant();
    }

    private ArmyUnitType getHorse() {
        return new Horse();
    }
}
