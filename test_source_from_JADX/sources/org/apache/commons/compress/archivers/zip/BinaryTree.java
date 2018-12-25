package org.apache.commons.compress.archivers.zip;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;

class BinaryTree {
    private static final int NODE = -2;
    private static final int UNDEFINED = -1;
    private final int[] tree;

    public BinaryTree(int depth) {
        this.tree = new int[((1 << (depth + 1)) - 1)];
        Arrays.fill(this.tree, -1);
    }

    public void addLeaf(int node, int path, int depth, int value) {
        if (depth != 0) {
            this.tree[node] = -2;
            addLeaf(((node * 2) + 1) + (path & 1), path >>> 1, depth - 1, value);
        } else if (this.tree[node] == -1) {
            this.tree[node] = value;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Tree value at index ");
            stringBuilder.append(node);
            stringBuilder.append(" has already been assigned (");
            stringBuilder.append(this.tree[node]);
            stringBuilder.append(")");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public int read(BitStream stream) throws IOException {
        int currentIndex = 0;
        while (true) {
            int value;
            int bit = stream.nextBit();
            if (bit != -1) {
                int childIndex = ((currentIndex * 2) + 1) + bit;
                value = this.tree[childIndex];
                if (value != -2) {
                    break;
                }
                currentIndex = childIndex;
            } else {
                return -1;
            }
        }
        if (value != -1) {
            return value;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The child ");
        stringBuilder.append(bit);
        stringBuilder.append(" of node at index ");
        stringBuilder.append(currentIndex);
        stringBuilder.append(" is not defined");
        throw new IOException(stringBuilder.toString());
    }

    static BinaryTree decode(InputStream in, int totalNumberOfValues) throws IOException {
        int i = totalNumberOfValues;
        int size = in.read() + 1;
        if (size == 0) {
            throw new IOException("Cannot read the size of the encoded tree, unexpected end of stream");
        }
        int numberOfValues;
        int bitLength;
        int k;
        int lastBitLength;
        byte[] encodedTree;
        byte[] encodedTree2 = new byte[size];
        new DataInputStream(in).readFully(encodedTree2);
        int[] originalBitLengths = new int[i];
        int pos = 0;
        byte[] arr$ = encodedTree2;
        int len$ = arr$.length;
        int maxLength = 0;
        int i$ = 0;
        while (i$ < len$) {
            byte b = arr$[i$];
            numberOfValues = ((b & SysexMessageWriter.COMMAND_START) >> 4) + 1;
            bitLength = (b & 15) + 1;
            int pos2 = pos;
            pos = 0;
            while (pos < numberOfValues) {
                int pos3 = pos2 + 1;
                originalBitLengths[pos2] = bitLength;
                pos++;
                pos2 = pos3;
            }
            maxLength = Math.max(maxLength, bitLength);
            i$++;
            pos = pos2;
        }
        int[] permutation = new int[originalBitLengths.length];
        for (k = 0; k < permutation.length; k++) {
            permutation[k] = k;
        }
        int[] sortedBitLengths = new int[originalBitLengths.length];
        int c = 0;
        k = 0;
        while (k < originalBitLengths.length) {
            numberOfValues = c;
            for (c = 0; c < originalBitLengths.length; c++) {
                if (originalBitLengths[c] == k) {
                    sortedBitLengths[numberOfValues] = k;
                    permutation[numberOfValues] = c;
                    numberOfValues++;
                }
            }
            k++;
            c = numberOfValues;
        }
        k = 0;
        numberOfValues = 0;
        bitLength = 0;
        int[] codes = new int[i];
        for (pos3 = i - 1; pos3 >= 0; pos3--) {
            k += numberOfValues;
            if (sortedBitLengths[pos3] != bitLength) {
                lastBitLength = sortedBitLengths[pos3];
                numberOfValues = 1 << (16 - lastBitLength);
                bitLength = lastBitLength;
            }
            codes[permutation[pos3]] = k;
        }
        BinaryTree tree = new BinaryTree(maxLength);
        lastBitLength = 0;
        while (lastBitLength < codes.length) {
            int size2;
            i = originalBitLengths[lastBitLength];
            if (i > 0) {
                size2 = size;
                encodedTree = encodedTree2;
                tree.addLeaf(null, Integer.reverse(codes[lastBitLength] << 16), i, lastBitLength);
            } else {
                size2 = size;
                encodedTree = encodedTree2;
            }
            lastBitLength++;
            size = size2;
            encodedTree2 = encodedTree;
            i = totalNumberOfValues;
        }
        encodedTree = encodedTree2;
        return tree;
    }
}
