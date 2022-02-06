package me.oskar.peko.vm;

import me.oskar.peko.code.OpCode;
import me.oskar.peko.compiler.constant.ConstantPool;
import me.oskar.peko.error.Error;
import me.oskar.peko.object.ArrayObject;
import me.oskar.peko.object.PekoObject;
import me.oskar.peko.std.BuiltInTable;
import me.oskar.peko.vm.stack.Stack;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class VirtualMachine {

    private final byte[] bytecode;
    private PekoObject[] constantPool;
    private ByteBuffer instructions;
    private final Stack stack = new Stack();
    private PekoObject[] globals;
    private PekoObject returnRegister;

    public VirtualMachine(final byte[] bytecode) throws IOException {
        this.bytecode = bytecode;

        init();
    }

    private void init() throws IOException {
        final var in = new DataInputStream(new ByteArrayInputStream(bytecode));
        if (!new String(in.readNBytes(4), StandardCharsets.UTF_8).equals("peko")) {
            Error.error("Unexpected magic number. This file does not seem to contain Peko bytecode.");
        }
        constantPool = ConstantPool.fromDataInputStream(in);

        final var globalsCount = in.readInt();
        globals = new PekoObject[globalsCount];

        final var functionsPosition = in.readInt();
        instructions = ByteBuffer.wrap(in.readAllBytes());
        instructions.position(functionsPosition);
    }

    public void execute() {
        while (true) {
            executeInstruction();
        }
    }

    private void executeInstruction() {
        final var opCode = instructions.get();

        switch (opCode) {
            case OpCode.CONSTANT -> {
                final var index = instructions.getInt();
                stack.pushObject(constantPool[index]);
            }
            case OpCode.LOAD_GLOBAL -> {
                final var index = instructions.getInt();
                stack.pushObject(globals[index]);
            }
            case OpCode.STORE_GLOBAL -> {
                final var index = instructions.getInt();
                final var value = stack.popObject();
                globals[index] = value;
            }
            case OpCode.LOAD_LOCAL -> {
                final var index = instructions.getInt();

                final var value = stack.getObject(stack.getFp() + index);
                stack.pushObject(value);
            }
            case OpCode.STORE_LOCAL -> {
                final var index = instructions.getInt();

                final var value = stack.popObject();
                stack.setObject(stack.getFp() + index, value);
            }
            case OpCode.LOAD_INDEX -> {
                final var index = stack.popObject();
                final var target = stack.popObject();

                stack.pushObject(target.getIndex(index));
            }
            case OpCode.STORE_INDEX -> {
                final var value = stack.popObject();
                final var index = stack.popObject();
                final var target = stack.popObject();

                target.setIndex(index, value);
            }
            case OpCode.JUMP -> {
                final var offset = instructions.getInt();

                instructions.position(instructions.position() + offset);
            }
            case OpCode.JUMP_IF_TRUE -> {
                final var offset = instructions.getInt();
                final var value = stack.popObject();

                if (!value.isTruthy()) {
                    instructions.position(instructions.position() + offset);
                }
            }
            case OpCode.JUMP_IF_FALSE -> {
                final var offset = instructions.getInt();
                final var value = stack.popObject();

                if (value.isTruthy()) {
                    instructions.position(instructions.position() + offset);
                }
            }
            case OpCode.CALL -> {
                final var position = instructions.getInt();

                stack.pushNumber(instructions.position());
                instructions.position(position);
            }
            case OpCode.CALL_BUILTIN -> {
                final var index = instructions.getInt();
                final var function = BuiltInTable.getInstance().getBuiltInFunction(index);

                final var arguments = new ArrayList<PekoObject>();
                for (var i = 0; i < function.getParametersCount(); i++) {
                    arguments.add(stack.popObject());
                }

                stack.pushObject(function.invoke(arguments));
            }
            case OpCode.RETURN -> {
                final var position = stack.popNumber();

                instructions.position(position);
            }
            case OpCode.LOAD_RETURN -> stack.pushObject(returnRegister);
            case OpCode.STORE_RETURN -> returnRegister = stack.popObject();
            case OpCode.ALLOCATE_STACK_FRAME -> {
                final var locals = instructions.getInt();

                stack.pushNumber(stack.getFp());
                stack.setFp(stack.getSp());
                stack.setSp(stack.getSp() + locals);
            }
            case OpCode.RESET_STACK_FRAME -> {
                stack.setSp(stack.getFp());
                stack.setFp(stack.popNumber());
            }
            case OpCode.DROP -> {
                final var amount = instructions.getInt();

                stack.drop(amount);
            }
            case OpCode.ADD -> {
                final var right = stack.popObject();
                final var left = stack.popObject();

                stack.pushObject(left.add(right));
            }
            case OpCode.SUB -> {
                final var right = stack.popObject();
                final var left = stack.popObject();

                stack.pushObject(left.sub(right));
            }
            case OpCode.MUL -> {
                final var right = stack.popObject();
                final var left = stack.popObject();

                stack.pushObject(left.mul(right));
            }
            case OpCode.DIV -> {
                final var right = stack.popObject();
                final var left = stack.popObject();

                stack.pushObject(left.div(right));
            }
            case OpCode.REM -> {
                final var right = stack.popObject();
                final var left = stack.popObject();

                stack.pushObject(left.mod(right));
            }
            case OpCode.EQUALS -> {
                final var right = stack.popObject();
                final var left = stack.popObject();

                stack.pushObject(left.eq(right));
            }
            case OpCode.NOT_EQUALS -> {
                final var right = stack.popObject();
                final var left = stack.popObject();

                stack.pushObject(left.ne(right));
            }
            case OpCode.LESS_THAN -> {
                final var right = stack.popObject();
                final var left = stack.popObject();

                stack.pushObject(left.lt(right));
            }
            case OpCode.LESS_THAN_OR_EQUAL -> {
                final var right = stack.popObject();
                final var left = stack.popObject();

                stack.pushObject(left.le(right));
            }
            case OpCode.GREATER_THAN -> {
                final var right = stack.popObject();
                final var left = stack.popObject();

                stack.pushObject(left.gt(right));
            }
            case OpCode.GREATER_THAN_OR_EQUAL -> {
                final var right = stack.popObject();
                final var left = stack.popObject();

                stack.pushObject(left.ge(right));
            }
            case OpCode.NOT -> {
                final var right = stack.popObject();

                stack.pushObject(right.not());
            }
            case OpCode.NEG -> {
                final var right = stack.popObject();

                stack.pushObject(right.neg());
            }
            case OpCode.HALT -> System.exit(0);
            case OpCode.CONSTRUCT_ARRAY -> {
                final var size = instructions.getInt();

                final var value = new ArrayList<PekoObject>();
                for (int i = 0; i < size; i++) {
                    value.add(0, stack.popObject());
                }

                stack.pushObject(new ArrayObject(value));
            }
        }
    }
}
