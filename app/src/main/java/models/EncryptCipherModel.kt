package models

import java.security.PrivateKey
import java.security.PublicKey

class EncryptCipherModel {
    var publicKey: PublicKey? = null
        private set
    var privateKey: PrivateKey? = null
    lateinit var encryptedBytes: ByteArray
    fun setEncryptCipher(
        publicKey: PublicKey?,
        privateKey: PrivateKey?,
        encryptedBytes: ByteArray
    ) {
        this.publicKey = publicKey
        this.privateKey = privateKey
        this.encryptedBytes = encryptedBytes
    }
}