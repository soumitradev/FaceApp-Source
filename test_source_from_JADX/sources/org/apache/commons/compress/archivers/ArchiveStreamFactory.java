package org.apache.commons.compress.archivers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.ar.ArArchiveInputStream;
import org.apache.commons.compress.archivers.ar.ArArchiveOutputStream;
import org.apache.commons.compress.archivers.arj.ArjArchiveInputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveInputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveOutputStream;
import org.apache.commons.compress.archivers.dump.DumpArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveOutputStream;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class ArchiveStreamFactory {
    public static final String AR = "ar";
    public static final String ARJ = "arj";
    public static final String CPIO = "cpio";
    public static final String DUMP = "dump";
    public static final String JAR = "jar";
    public static final String SEVEN_Z = "7z";
    public static final String TAR = "tar";
    public static final String ZIP = "zip";
    private String entryEncoding = null;

    public String getEntryEncoding() {
        return this.entryEncoding;
    }

    public void setEntryEncoding(String entryEncoding) {
        this.entryEncoding = entryEncoding;
    }

    public ArchiveInputStream createArchiveInputStream(String archiverName, InputStream in) throws ArchiveException {
        if (archiverName == null) {
            throw new IllegalArgumentException("Archivername must not be null.");
        } else if (in == null) {
            throw new IllegalArgumentException("InputStream must not be null.");
        } else if (AR.equalsIgnoreCase(archiverName)) {
            return new ArArchiveInputStream(in);
        } else {
            if (ARJ.equalsIgnoreCase(archiverName)) {
                if (this.entryEncoding != null) {
                    return new ArjArchiveInputStream(in, this.entryEncoding);
                }
                return new ArjArchiveInputStream(in);
            } else if (ZIP.equalsIgnoreCase(archiverName)) {
                if (this.entryEncoding != null) {
                    return new ZipArchiveInputStream(in, this.entryEncoding);
                }
                return new ZipArchiveInputStream(in);
            } else if (TAR.equalsIgnoreCase(archiverName)) {
                if (this.entryEncoding != null) {
                    return new TarArchiveInputStream(in, this.entryEncoding);
                }
                return new TarArchiveInputStream(in);
            } else if (JAR.equalsIgnoreCase(archiverName)) {
                return new JarArchiveInputStream(in);
            } else {
                if (CPIO.equalsIgnoreCase(archiverName)) {
                    if (this.entryEncoding != null) {
                        return new CpioArchiveInputStream(in, this.entryEncoding);
                    }
                    return new CpioArchiveInputStream(in);
                } else if (DUMP.equalsIgnoreCase(archiverName)) {
                    if (this.entryEncoding != null) {
                        return new DumpArchiveInputStream(in, this.entryEncoding);
                    }
                    return new DumpArchiveInputStream(in);
                } else if (SEVEN_Z.equalsIgnoreCase(archiverName)) {
                    throw new StreamingNotSupportedException(SEVEN_Z);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Archiver: ");
                    stringBuilder.append(archiverName);
                    stringBuilder.append(" not found.");
                    throw new ArchiveException(stringBuilder.toString());
                }
            }
        }
    }

    public ArchiveOutputStream createArchiveOutputStream(String archiverName, OutputStream out) throws ArchiveException {
        if (archiverName == null) {
            throw new IllegalArgumentException("Archivername must not be null.");
        } else if (out == null) {
            throw new IllegalArgumentException("OutputStream must not be null.");
        } else if (AR.equalsIgnoreCase(archiverName)) {
            return new ArArchiveOutputStream(out);
        } else {
            if (ZIP.equalsIgnoreCase(archiverName)) {
                ZipArchiveOutputStream zip = new ZipArchiveOutputStream(out);
                if (this.entryEncoding != null) {
                    zip.setEncoding(this.entryEncoding);
                }
                return zip;
            } else if (TAR.equalsIgnoreCase(archiverName)) {
                if (this.entryEncoding != null) {
                    return new TarArchiveOutputStream(out, this.entryEncoding);
                }
                return new TarArchiveOutputStream(out);
            } else if (JAR.equalsIgnoreCase(archiverName)) {
                return new JarArchiveOutputStream(out);
            } else {
                if (CPIO.equalsIgnoreCase(archiverName)) {
                    if (this.entryEncoding != null) {
                        return new CpioArchiveOutputStream(out, this.entryEncoding);
                    }
                    return new CpioArchiveOutputStream(out);
                } else if (SEVEN_Z.equalsIgnoreCase(archiverName)) {
                    throw new StreamingNotSupportedException(SEVEN_Z);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Archiver: ");
                    stringBuilder.append(archiverName);
                    stringBuilder.append(" not found.");
                    throw new ArchiveException(stringBuilder.toString());
                }
            }
        }
    }

    public ArchiveInputStream createArchiveInputStream(InputStream in) throws ArchiveException {
        if (in == null) {
            throw new IllegalArgumentException("Stream must not be null.");
        } else if (in.markSupported()) {
            byte[] signature = new byte[12];
            in.mark(signature.length);
            try {
                int signatureLength = IOUtils.readFully(in, signature);
                in.reset();
                if (ZipArchiveInputStream.matches(signature, signatureLength)) {
                    if (this.entryEncoding != null) {
                        return new ZipArchiveInputStream(in, this.entryEncoding);
                    }
                    return new ZipArchiveInputStream(in);
                } else if (JarArchiveInputStream.matches(signature, signatureLength)) {
                    return new JarArchiveInputStream(in);
                } else {
                    if (ArArchiveInputStream.matches(signature, signatureLength)) {
                        return new ArArchiveInputStream(in);
                    }
                    if (CpioArchiveInputStream.matches(signature, signatureLength)) {
                        return new CpioArchiveInputStream(in);
                    }
                    if (ArjArchiveInputStream.matches(signature, signatureLength)) {
                        return new ArjArchiveInputStream(in);
                    }
                    if (SevenZFile.matches(signature, signatureLength)) {
                        throw new StreamingNotSupportedException(SEVEN_Z);
                    }
                    byte[] dumpsig = new byte[32];
                    in.mark(dumpsig.length);
                    signatureLength = IOUtils.readFully(in, dumpsig);
                    in.reset();
                    if (DumpArchiveInputStream.matches(dumpsig, signatureLength)) {
                        return new DumpArchiveInputStream(in);
                    }
                    byte[] tarheader = new byte[512];
                    in.mark(tarheader.length);
                    signatureLength = IOUtils.readFully(in, tarheader);
                    in.reset();
                    if (!TarArchiveInputStream.matches(tarheader, signatureLength)) {
                        if (signatureLength >= 512) {
                            TarArchiveInputStream tais = new TarArchiveInputStream(new ByteArrayInputStream(tarheader));
                            if (tais.getNextTarEntry().isCheckSumOK()) {
                                ArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(in);
                                IOUtils.closeQuietly(tais);
                                return tarArchiveInputStream;
                            }
                            IOUtils.closeQuietly(tais);
                        }
                        throw new ArchiveException("No Archiver found for the stream signature");
                    } else if (this.entryEncoding != null) {
                        return new TarArchiveInputStream(in, this.entryEncoding);
                    } else {
                        return new TarArchiveInputStream(in);
                    }
                }
            } catch (Exception e) {
                IOUtils.closeQuietly(null);
            } catch (IOException e2) {
                throw new ArchiveException("Could not use reset and mark operations.", e2);
            } catch (Throwable th) {
                IOUtils.closeQuietly(null);
            }
        } else {
            throw new IllegalArgumentException("Mark is not supported.");
        }
    }
}
