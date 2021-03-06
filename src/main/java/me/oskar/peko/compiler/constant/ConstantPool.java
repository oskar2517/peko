package me.oskar.peko.compiler.constant;

import me.oskar.peko.compiler.constant.object.*;
import me.oskar.peko.object.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ConstantPool {

    private final ArrayList<Constant> constants = new ArrayList<>();

    private int addConstant(final Constant c) {
        for (int i = 0; i < constants.size(); i++) {
            if (c.equals(constants.get(i))) {
                return i;
            }
        }

        constants.add(c);

        return constants.size() - 1;
    }

    public int addNumberConstant(final double value) {
        return addConstant(new NumberConstant(value));
    }

    public int addBooleanConstant(final boolean value) {
        return addConstant(new BooleanConstant(value));
    }

    public int addStringConstant(final String value) {
        return addConstant(new StringConstant(value));
    }

    public int addNilConstant() {
        return addConstant(new NilConstant());
    }

    public byte[] toByteArray() throws IOException {
        final var buffer = new ByteArrayOutputStream();
        final var out = new DataOutputStream(buffer);

        out.writeInt(constants.size());

        for (Constant c : constants) {
            if (c instanceof NumberConstant cc) {
                out.writeByte(ConstantType.NUMBER);
                out.writeDouble(cc.getValue());
            } else if (c instanceof BooleanConstant cc) {
                out.writeByte(ConstantType.BOOLEAN);
                out.writeByte(cc.getValue() ? 1 : 0);
            } else if (c instanceof StringConstant cc) {
                final var stringBytes = cc.getValue().getBytes(StandardCharsets.UTF_8);

                out.writeByte(ConstantType.STRING);
                out.writeInt(stringBytes.length);
                out.write(stringBytes);
            } else if (c instanceof NilConstant) {
                out.writeByte(ConstantType.NIL);
            }
        }

        return buffer.toByteArray();
    }

    public static PekoObject[] fromDataInputStream(final DataInputStream in) throws IOException {
        final var poolSize = in.readInt();
        final var pool = new PekoObject[poolSize];

        for (int i = 0; i < poolSize; i++) {
            final var constantType = in.readByte();

            switch (constantType) {
                case ConstantType.NUMBER -> {
                    final var value = in.readDouble();
                    pool[i] = new NumberObject(value);
                }
                case ConstantType.BOOLEAN -> {
                    final var value = in.readByte() == 1;
                    pool[i] = new BooleanObject(value);
                }
                case ConstantType.STRING -> {
                    final var length = in.readInt();
                    final var value = new String(in.readNBytes(length), StandardCharsets.UTF_8);
                    pool[i] = new StringObject(value);
                }
                case ConstantType.NIL -> pool[i] = new NilObject();
                default -> throw new IllegalStateException("Unexpected object type: " + constantType);
            }
        }

        return pool;
    }
}
