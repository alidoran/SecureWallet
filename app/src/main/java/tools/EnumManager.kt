package tools

class EnumManager {
    enum class ErrorType {
        DataError, NotFound, NotConnect
    }

    enum class BiometricError(val value: Int) {
        Disabled(9), Negative(13), NoBiometric(11), NoDeviceCredential(
            14), NoSpace(4), SecurityUpdateRequired(15),
        TimeOut(3), UnableToProcess(2), UserCanceled(10), ErrorVendor(8);
    }
}