package models;

import java.security.PrivateKey;
import java.security.PublicKey;

public class EncryptCipherModel {
    private PublicKey publicKey;
    PrivateKey privateKey;
    byte[] encryptedBytes;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public byte[] getEncryptedBytes() {
        return encryptedBytes;
    }

    public void setEncryptCipher(PublicKey publicKey , PrivateKey privateKey , byte[] encryptedBytes){
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.encryptedBytes =encryptedBytes;
    }
}
