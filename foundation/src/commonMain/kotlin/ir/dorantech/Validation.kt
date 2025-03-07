package ir.dorantech

import ir.dorantech.model.SecurityLevel

object Validation {
    fun nameValidation(
        username: String,
        securityLevel: SecurityLevel,
    ): String {
        if (username.isEmpty()) return ""
        var error = ""
        val isLengthPass = username.length > 2
        if (!isLengthPass) error = "Username must be at least 3 characters"
        val isAllowedCharacter = username.matches("^[a-zA-Z0-9_]+$".toRegex())
        if (!isAllowedCharacter && error.isEmpty())
            error = "Username can only contain letters, numbers, and underscores"
        val consecutiveSymbols =
            username.matches("^[a-zA-Z0-9](?!.*[_.]{2})[a-zA-Z0-9._]*[a-zA-Z0-9]$".toRegex())
        if (!consecutiveSymbols && error.isEmpty())
            error = "Username can't have consecutive symbols"
        val startEndValidation = username.matches("^[a-zA-Z0-9][a-zA-Z0-9_]*[a-zA-Z0-9]$".toRegex())
        if (!startEndValidation && error.isEmpty())
            error = "Username can't start or end with a symbol"
        return when (securityLevel) {
            SecurityLevel.Weak -> {
                if (isLengthPass && isAllowedCharacter) ""
                else error
            }

            SecurityLevel.Medium -> {
                if (isLengthPass && isAllowedCharacter && consecutiveSymbols) ""
                else error
            }

            SecurityLevel.Strong -> {
                if (isLengthPass && isAllowedCharacter && consecutiveSymbols && startEndValidation) ""
                else error
            }
        }
    }

    fun passwordValidation(
        password: String,
        securityLevel: SecurityLevel,
    ): String {
        if (password.isEmpty()) return ""
        var error = ""
        val isLengthPass = password.length > 7
        if (!isLengthPass)
            error = "Password must be at least 8 characters"
        val hasLetter = password.any { it.isLetter() }
        if (!hasLetter && error.isEmpty())
            error = "Password must contain at least one letter"
        val hasDigit = password.any { it.isDigit() }
        if (!hasDigit && error.isEmpty())
            error = "Password must contain at least one digit"
        val hasUpperCase = password.any { it.isUpperCase() }
        if (!hasUpperCase && error.isEmpty())
            error = "Password must contain at least one uppercase letter"
        val hasLowerCase = password.any { it.isLowerCase() }
        if (!hasLowerCase && error.isEmpty())
            error = "Password must contain at least one lowercase letter"
        val hasSpecialChar = password.any { it in "!@#$%^&*()_+={}[]|\\:;\"'<>,.?/" }
        if (!hasSpecialChar && error.isEmpty())
            error = "Password must contain at least one special character"

        return when (securityLevel) {
            SecurityLevel.Weak -> {
                if (isLengthPass) "" else error
            }

            SecurityLevel.Medium -> {
                if (isLengthPass && hasLetter && hasDigit) ""
                else error
            }

            SecurityLevel.Strong -> {
                if (isLengthPass && hasLetter && hasDigit && hasUpperCase && hasLowerCase && hasSpecialChar) ""
                else error
            }
        }
    }
}



