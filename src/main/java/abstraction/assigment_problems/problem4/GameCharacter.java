package abstraction.assigment_problems.problem4;

public abstract class GameCharacter {
    private static int characterCounter = 1000;

    private final String characterId;

    protected GameCharacter() {
        characterCounter++;
        this.characterId = "CHR-" + characterCounter;
    }

    public abstract String getSpecialMove();

    String getCharacterId() {
        return characterId;
    }
}
