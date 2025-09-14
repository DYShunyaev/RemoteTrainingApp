package d.shunyaev.RemoteTrainingApp.enums;

public enum TrainingLevel implements EnumInterface {

    BEGINNER("Новичок"),
    INTERMEDIATE("Средний"),
    ADVANCED("Продвинутый");

    private final String description;

    TrainingLevel(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
