package me.oskar.peko.code;

public class OpCode {

    public static final int CONSTANT = 0x0;
    public static final int LOAD_GLOBAL = 0x1;
    public static final int STORE_GLOBAL = 0x2;
    public static final int LOAD_LOCAL = 0x3;
    public static final int STORE_LOCAL = 0x4;
    public static final int LOAD_INDEX = 0x5;
    public static final int STORE_INDEX = 0x6;
    public static final int JUMP = 0x7;
    public static final int JUMP_IF_TRUE = 0x8;
    public static final int JUMP_IF_FALSE = 0x9;
    public static final int CALL = 0xA;
    public static final int CALL_BUILTIN = 0xB;
    public static final int RETURN = 0xC;
    public static final int LOAD_RETURN = 0xD;
    public static final int STORE_RETURN = 0xE;
    public static final int ALLOCATE_STACK_FRAME = 0xF;
    public static final int RESET_STACK_FRAME = 0x10;
    public static final int DROP = 0x11;
    public static final int ADD = 0x12;
    public static final int SUB = 0x13;
    public static final int MUL = 0x14;
    public static final int DIV = 0x15;
    public static final int REM = 0x16;
    public static final int EQUALS = 0x17;
    public static final int NOT_EQUALS = 0x18;
    public static final int LESS_THAN = 0x19;
    public static final int LESS_THAN_OR_EQUAL = 0x1A;
    public static final int GREATER_THAN = 0x1B;
    public static final int GREATER_THAN_OR_EQUAL = 0x1C;
    public static final int NOT = 0x1D;
    public static final int NEG = 0x1E;
    public static final int HALT = 0x1F;
    public static final int CONSTRUCT_ARRAY = 0x20;
}
