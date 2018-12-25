package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class AES256SHA256Decoder extends CoderBase {
    AES256SHA256Decoder() {
        super(new Class[0]);
    }

    InputStream decode(final InputStream in, long uncompressedLength, final Coder coder, final byte[] passwordBytes) throws IOException {
        return new InputStream() {
            private CipherInputStream cipherInputStream = null;
            private boolean isInitialized = null;

            private CipherInputStream init() throws IOException {
                if (this.isInitialized) {
                    return r1.cipherInputStream;
                }
                int byte0 = coder.properties[0] & 255;
                int numCyclesPower = byte0 & 63;
                int byte1 = coder.properties[1] & 255;
                int ivSize = ((byte0 >> 6) & 1) + (byte1 & 15);
                int saltSize = ((byte0 >> 7) & 1) + (byte1 >> 4);
                if ((saltSize + 2) + ivSize > coder.properties.length) {
                    throw new IOException("Salt size + IV size too long");
                }
                byte[] salt = new byte[saltSize];
                System.arraycopy(coder.properties, 2, salt, 0, saltSize);
                byte[] iv = new byte[16];
                System.arraycopy(coder.properties, saltSize + 2, iv, 0, ivSize);
                if (passwordBytes == null) {
                    throw new IOException("Cannot read encrypted files without a password");
                }
                byte[] aesKeyBytes;
                if (numCyclesPower == 63) {
                    aesKeyBytes = new byte[32];
                    System.arraycopy(salt, 0, aesKeyBytes, 0, saltSize);
                    System.arraycopy(passwordBytes, 0, aesKeyBytes, saltSize, Math.min(passwordBytes.length, aesKeyBytes.length - saltSize));
                } else {
                    try {
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] extra = new byte[8];
                        for (long j = 0; j < (1 << numCyclesPower); j++) {
                            digest.update(salt);
                            digest.update(passwordBytes);
                            digest.update(extra);
                            for (int k = 0; k < extra.length; k++) {
                                extra[k] = (byte) (extra[k] + 1);
                                if (extra[k] != (byte) 0) {
                                    break;
                                }
                            }
                        }
                        aesKeyBytes = digest.digest();
                    } catch (NoSuchAlgorithmException e) {
                        NoSuchAlgorithmException noSuchAlgorithmException = e;
                        IOException ioe = new IOException("SHA-256 is unsupported by your Java implementation");
                        ioe.initCause(noSuchAlgorithmException);
                        throw ioe;
                    }
                }
                SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");
                try {
                    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
                    cipher.init(2, aesKey, new IvParameterSpec(iv));
                    r1.cipherInputStream = new CipherInputStream(in, cipher);
                    r1.isInitialized = true;
                    return r1.cipherInputStream;
                } catch (GeneralSecurityException e2) {
                    GeneralSecurityException generalSecurityException = e2;
                    IOException ioe2 = new IOException("Decryption error (do you have the JCE Unlimited Strength Jurisdiction Policy Files installed?)");
                    ioe2.initCause(generalSecurityException);
                    throw ioe2;
                }
            }

            public int read() throws IOException {
                return init().read();
            }

            public int read(byte[] b, int off, int len) throws IOException {
                return init().read(b, off, len);
            }

            public void close() {
            }
        };
    }
}
