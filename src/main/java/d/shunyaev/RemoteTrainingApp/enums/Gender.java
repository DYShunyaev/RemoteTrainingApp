package d.shunyaev.RemoteTrainingApp.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Gender implements EnumInterface {
    MAN("MAN"),
    WOMAN("WOMAN"),
    NON("NON");
    private final String description;

    @Override
    public String getDescription() {
        return description;
    }
}
