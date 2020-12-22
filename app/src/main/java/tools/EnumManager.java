package tools;

public class EnumManager {

    public enum ErrorType {
        FetchData,
        NoItem,
        NotConnect
    }

    public enum AppLanguage {
        English,
        Persian
    }

    public enum BiometricError {
        Disabled (9),
        Negative(13),
        NoBiometric(11),
        NoDeviceCredential(14),
        NoSpace(4),
        SecurityUpdateRequired(15),
        TimeOut(3),
        UnableToProcess(2),
        UserCanceled(10),
        ErrorVendor(8),
        NotFound(-1),
        ;
        public final int value;
        BiometricError(final int newValue) {
            value = newValue;
        }
        public int getValue() {
            return value;
        }
    }

}
