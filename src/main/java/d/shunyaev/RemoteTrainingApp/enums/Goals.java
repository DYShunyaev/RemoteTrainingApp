package d.shunyaev.RemoteTrainingApp.enums;

public enum Goals implements EnumInterface {

    WEIGHT_LOSS("Похудение"),
    WEIGHT_GAIN("Набор массы"),
    KEEPING_FIT("Поддержание формы");

    private final String description;

    Goals(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}