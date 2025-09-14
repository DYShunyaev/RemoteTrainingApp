package d.shunyaev.RemoteTrainingApp.enums;

public enum Role implements EnumInterface {
    USER("Пользователь"),
    TRAINER("Тренер");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
