package models

import java.io.Serializable

class SignUpModel : Serializable {
    var name: String? = null
    private var lName: String? = null
    var email: String? = null
    var userName: String? = null
    var password: String? = null
    var isBiometric = false

}