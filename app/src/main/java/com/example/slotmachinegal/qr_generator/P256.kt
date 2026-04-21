package com.example.slotmachinegal.qr_generator

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.*
import java.security.spec.ECGenParameterSpec
import java.security.spec.X509EncodedKeySpec

object P256 {

    private const val KEYSTORE = "AndroidKeyStore"

    /**
     * Genera un nuevo par de claves P-256 si el alias no existe aún.
     */
    fun generateP256KeyPairIfAbsent(alias: String): Boolean {
        val keyStore = KeyStore.getInstance(KEYSTORE).apply { load(null) }

        if (keyStore.containsAlias(alias)) {
            return false // Clave ya existe, no se genera
        }

        val keyGen = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, KEYSTORE)

        val parameterSpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
        )
            .setAlgorithmParameterSpec(ECGenParameterSpec("secp256r1")) // P-256
            .setDigests(KeyProperties.DIGEST_SHA256)
            .setUserAuthenticationRequired(false) // Cambiar según tu seguridad deseada
            .build()

        keyGen.initialize(parameterSpec)
        keyGen.generateKeyPair()
        return true
    }

    /**
     * Firma datos usando la clave privada del alias especificado.
     */
    fun signData(alias: String, data: ByteArray): ByteArray {
        val keyStore = KeyStore.getInstance(KEYSTORE).apply { load(null) }
        val privateKey = keyStore.getKey(alias, null) as? PrivateKey
            ?: throw IllegalStateException("Clave privada no encontrada para alias: $alias")

        val signature = Signature.getInstance("SHA256withECDSA")
        signature.initSign(privateKey)
        signature.update(data)
        return signature.sign()
    }

    /**
     * Obtiene la clave pública en formato Base64 asociada al alias.
     */
    fun getPublicKeyBase64(alias: String): String? {
        val keyStore = KeyStore.getInstance(KEYSTORE).apply { load(null) }
        val cert = keyStore.getCertificate(alias) ?: return null
        return Base64.encodeToString(cert.publicKey.encoded, Base64.NO_WRAP)
    }

    /**
     * Verifica una firma P-256 con la clave pública proporcionada.
     */
    fun verifySignature(data: ByteArray, signature: ByteArray, publicKeyBytes: ByteArray): Boolean {
        val keyFactory = KeyFactory.getInstance("EC")
        val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(publicKeyBytes))
        val verifier = Signature.getInstance("SHA256withECDSA")
        verifier.initVerify(publicKey)
        verifier.update(data)
        return verifier.verify(signature)
    }

    /**
     * Elimina una clave del Keystore si existe.
     */
    fun deleteKey(alias: String) {
        val keyStore = KeyStore.getInstance(KEYSTORE).apply { load(null) }
        if (keyStore.containsAlias(alias)) {
            keyStore.deleteEntry(alias)
        }
    }

    /**
     * Verifica si una clave ya existe.
     */
    fun doesKeyExist(alias: String): Boolean {
        val keyStore = KeyStore.getInstance(KEYSTORE).apply { load(null) }
        return keyStore.containsAlias(alias)
    }
}
