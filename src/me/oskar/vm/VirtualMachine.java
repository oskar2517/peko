package me.oskar.vm;

import me.oskar.code.OpCode;
import me.oskar.compiler.constant.ConstantPool;
import me.oskar.object.LObject;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class VirtualMachine {

    private final byte[] bytecode;
    private ArrayList<LObject> constantPool;
    private ByteBuffer instructions;
    private final Stack<LObject> stack = new Stack<>();
    private final Stack<Integer> callStack = new Stack<>();
    private LObject[] globals;
    private LObject returnRegister;

    public VirtualMachine(final byte[] bytecode) throws IOException {
        this.bytecode = bytecode;

        init();
    }

    private void init() throws IOException {
        final var in = new DataInputStream(new ByteArrayInputStream(bytecode));
        if (in.readInt() != 0xC0DE) {
            throw new IllegalStateException("Not a valid bytecode file!");
        }
        constantPool = ConstantPool.fromDateInputStream(in);

        final var globalsCount = in.readInt();
        globals = new LObject[globalsCount];

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
            case OpCode.CONST -> {
                final var index = instructions.getInt();
                stack.push(constantPool.get(index));
            }
            case OpCode.LOAD_G -> {
                final var index = instructions.getInt();
                stack.push(globals[index]);
            }
            case OpCode.STORE_G -> {
                final var index = instructions.getInt();
                final var value = stack.pop();
                globals[index] = value;
            }
            case OpCode.LOAD_L -> {
                final var index = instructions.getInt();

                final var value = stack.get(stack.getFp() + index);
                stack.push(value);
            }
            case OpCode.STORE_L -> {
                final var index = instructions.getInt();

                final var value = stack.pop();
                stack.set(stack.getFp() + index, value);
            }
            case OpCode.JMP -> {
                final var offset = instructions.getInt();

                instructions.position(instructions.position() + offset);
            }
            case OpCode.BRF -> {
                final var offset = instructions.getInt();
                final var value = stack.pop();

                if (!value.isTruthy()) {
                    instructions.position(instructions.position() + offset);
                }
            }
            case OpCode.BRT -> {
                final var offset = instructions.getInt();
                final var value = stack.pop();

                if (value.isTruthy()) {
                    instructions.position(instructions.position() + offset);
                }
            }
            case OpCode.CALL -> {
                final var position = instructions.getInt();

                callStack.push(instructions.position());
                instructions.position(position);
            }
            case OpCode.RET -> {
                final var position = callStack.pop();

                instructions.position(position);
            }
            case OpCode.LOAD_R -> stack.push(returnRegister);
            case OpCode.STORE_R -> returnRegister = stack.pop();
            case OpCode.ASF -> {
                final var locals = instructions.getInt();

                callStack.push(stack.getFp());
                stack.setFp(stack.getSp());
                stack.setSp(stack.getSp() + locals);
            }
            case OpCode.RSF -> {
                stack.setSp(stack.getFp());
                stack.setFp(callStack.pop());
            }
            case OpCode.POP -> {
                final var amount = instructions.getInt();

                stack.pop(amount);
            }
            case OpCode.ADD -> {
                final var right = stack.pop();
                final var left = stack.pop();

                stack.push(left.add(right));
            }
            case OpCode.SUB -> {
                final var right = stack.pop();
                final var left = stack.pop();

                stack.push(left.sub(right));
            }
            case OpCode.MUL -> {
                final var right = stack.pop();
                final var left = stack.pop();

                stack.push(left.mul(right));
            }
            case OpCode.DIV -> {
                final var right = stack.pop();
                final var left = stack.pop();

                stack.push(left.div(right));
            }
            case OpCode.MOD -> {
                final var right = stack.pop();
                final var left = stack.pop();

                stack.push(left.mod(right));
            }
            case OpCode.EQ -> {
                final var right = stack.pop();
                final var left = stack.pop();

                stack.push(left.eq(right));
            }
            case OpCode.NE -> {
                final var right = stack.pop();
                final var left = stack.pop();

                stack.push(left.ne(right));
            }
            case OpCode.LT -> {
                final var right = stack.pop();
                final var left = stack.pop();

                stack.push(left.lt(right));
            }
            case OpCode.LE -> {
                final var right = stack.pop();
                final var left = stack.pop();

                stack.push(left.le(right));
            }
            case OpCode.GT -> {
                final var right = stack.pop();
                final var left = stack.pop();

                stack.push(left.gt(right));
            }
            case OpCode.GE -> {
                final var right = stack.pop();
                final var left = stack.pop();

                stack.push(left.ge(right));
            }
            case OpCode.NOT -> {
                final var right = stack.pop();

                stack.push(right.not());
            }
            case OpCode.NEG -> {
                final var right = stack.pop();

                stack.push(right.neg());
            }
            case OpCode.HALT -> {
                System.out.println(stack.getFp());
                System.exit(0);
            }
            case OpCode.PUTS -> {
                final var value = stack.pop();
                System.out.println(value);
            }
        }
    }
}
