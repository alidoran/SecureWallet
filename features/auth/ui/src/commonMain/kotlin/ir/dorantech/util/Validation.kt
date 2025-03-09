package ir.dorantech.util

import ir.dorantech.model.SecurityLevel

object Validation {
    fun validateUserName(username: String, securityLevel: SecurityLevel): String {
        if (username.isEmpty()) return ""

        val conditions = listOf(
            (username.length > 2) to "Username must be at least 3 characters",
            username.matches("^[a-zA-Z0-9_]+$".toRegex()) to "Username can only contain letters, numbers, and underscores",
            username.matches("^[a-zA-Z0-9](?!.*[_.]{2})[a-zA-Z0-9._]*[a-zA-Z0-9]$".toRegex()) to "Username can't have consecutive symbols",
            username.matches("^[a-zA-Z0-9][a-zA-Z0-9_]*[a-zA-Z0-9]$".toRegex()) to "Username can't start or end with a symbol"
        )

        val errors = conditions.filterNot { it.first }.map { it.second }
        val error = errors.firstOrNull().orEmpty()

        return when (securityLevel) {
            SecurityLevel.Weak -> if (username.length > 2 && username.matches("^[a-zA-Z0-9_]+$".toRegex())) "" else error
            SecurityLevel.Medium -> if (username.length > 2 && username.matches("^[a-zA-Z0-9_]+$".toRegex()) &&
                username.matches("^[a-zA-Z0-9](?!.*[_.]{2})[a-zA-Z0-9._]*[a-zA-Z0-9]$".toRegex())
            ) "" else error

            SecurityLevel.Strong -> if (username.length > 2 && username.matches("^[a-zA-Z0-9_]+$".toRegex()) &&
                username.matches("^[a-zA-Z0-9](?!.*[_.]{2})[a-zA-Z0-9._]*[a-zA-Z0-9]$".toRegex()) &&
                username.matches("^[a-zA-Z0-9][a-zA-Z0-9_]*[a-zA-Z0-9]$".toRegex())
            ) "" else error
        }
    }

    fun validatePass(password: String, securityLevel: SecurityLevel): String {
        if (password.isEmpty()) return ""

        val conditions = listOf(
            (password.length > 7) to "Password must be at least 8 characters",
            password.any { it.isLetter() } to "Password must contain at least one letter",
            password.any { it.isDigit() } to "Password must contain at least one digit",
            password.any { it.isUpperCase() } to "Password must contain at least one uppercase letter",
            password.any { it.isLowerCase() } to "Password must contain at least one lowercase letter",
            password.any { it in "!@#$%^&*()_+={}[]|\\:;\"'<>,.?/" } to "Password must contain at least one special character"
        )

        val errors = conditions.filterNot { it.first }.map { it.second }
        val error = errors.firstOrNull().orEmpty()

        return when (securityLevel) {
            SecurityLevel.Weak -> if (password.length > 7) "" else error
            SecurityLevel.Medium -> if (password.length > 7 && password.any { it.isLetter() } && password.any { it.isDigit() }) "" else error
            SecurityLevel.Strong -> if (password.length > 7 && password.any { it.isLetter() } && password.any { it.isDigit() } &&
                password.any { it.isUpperCase() } && password.any { it.isLowerCase() } && password.any { it in "!@#$%^&*()_+={}[]|\\:;\"'<>,.?/" }) "" else error
        }
    }

    fun validateEmail(email: String, securityLevel: SecurityLevel): String {
        if (email.isEmpty()) return ""
        val weakRegex = Regex("^[^@\\s]+@[^@\\s]+$")
        val mediumRegex =
            Regex("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
        val strongRegex =
            Regex("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zAZ]{2,}(?:\\.[a-zA-Z]{2,})?$")
        return when (securityLevel) {
            is SecurityLevel.Weak -> {
                if (!email.matches(weakRegex)) {
                    "Invalid email format"
                } else ""
            }

            is SecurityLevel.Medium -> {
                if (!email.matches(mediumRegex)) {
                    "Invalid email format: local part can contain letters, numbers, and '_', '+', '&', '*', '-'"
                } else ""
            }

            is SecurityLevel.Strong -> {
                if (!email.matches(strongRegex)) {
                    "Invalid email format with longer TLDs"
                } else ""
            }
        }
    }

    fun validatePhoneNumber(phoneNumber: String, securityLevel: SecurityLevel): String {
        if (phoneNumber.isEmpty()) return ""
        val weakRegex = Regex("^\\d{10,15}$")
        val mediumRegex = Regex("^\\+?\\d{1,3}[\\s-]?\\d{10,15}$")
        val strongRegex =
            Regex("^\\+?\\d{1,3}[\\s-]?\\(?\\d{1,4}\\)?[\\s-]?\\d{1,4}[\\s-]?\\d{1,4}[\\s-]?\\d{1,4}$")

        return when (securityLevel) {
            is SecurityLevel.Weak -> {
                if (!phoneNumber.matches(weakRegex)) {
                    "Phone number must contain between 10 to 15 digits"
                } else ""
            }

            is SecurityLevel.Medium -> {
                if (!phoneNumber.matches(mediumRegex)) {
                    "Phone number must contain a country code and be between 10 to 15 digits"
                } else ""
            }

            is SecurityLevel.Strong -> {
                if (!phoneNumber.matches(strongRegex)) {
                    "Phone number must have a valid international format, including optional parentheses and dashes"
                } else ""
            }
        }
    }

    fun validateFirstLastName(firstName: String, securityLevel: SecurityLevel): String {
        if (firstName.isEmpty()) return ""
        return when (securityLevel) {
            is SecurityLevel.Weak -> {
                if (!firstName.matches(Regex("^[a-zA-Z]+$"))) {
                    "First name must contain only letters"
                } else ""
            }

            is SecurityLevel.Medium -> {
                if (!firstName.matches(Regex("^[A-Z][a-zA-Z]*$"))) {
                    "First name must start with an uppercase letter and contain only letters"
                } else ""
            }

            is SecurityLevel.Strong -> {
                if (!firstName.matches(Regex("^[A-Z][a-zA-Z]{2,30}$"))) {
                    "First name must start with an uppercase letter, contain only letters, and be between 3 and 30 characters"
                } else ""
            }
        }
    }
}



