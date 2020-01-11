package constants;

public enum ArmyUnitTypeEnum {
    HORSE(0),
    ELEPHANTS(1),
    ARMOURED_TANKS(2),
    SLING_GUNS(3);

    private int unitId;

    ArmyUnitTypeEnum(int unitId){ this.unitId = unitId; }

    public int getId(){ return unitId; }
}
