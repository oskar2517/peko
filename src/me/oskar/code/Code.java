package me.oskar.code;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Code {

    public static byte[] make(final int op, final int immediate) throws IOException {
        ByteArrayOutputStream instruction = new ByteArrayOutputStream();

        final DataOutputStream out = new DataOutputStream(instruction);
        out.writeByte(op);
        out.writeInt(immediate);

        return instruction.toByteArray();
    }

    public static byte[] make(final int op) throws IOException {
        ByteArrayOutputStream instruction = new ByteArrayOutputStream();

        final DataOutputStream out = new DataOutputStream(instruction);
        out.writeByte(op);

        return instruction.toByteArray();
    }
}
