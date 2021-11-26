package main.me.oskar.code;

public class OpCode {

    public static final int CONST = 0x0;
    public static final int LOAD_G = 0x1;
    public static final int STORE_G = 0x2;
    public static final int LOAD_L = 0x3;
    public static final int STORE_L = 0x4;
    public static final int LOAD_A = 0x5;
    public static final int STORE_A = 0x6;
    public static final int JMP = 0x7;
    public static final int BRF = 0x8;
    public static final int BRT = 0x9;
    public static final int CALL = 0xA;
    public static final int CALL_B = 0xB;
    public static final int RET = 0xC;
    public static final int LOAD_R = 0xD;
    public static final int STORE_R = 0xE;
    public static final int ASF = 0xF;
    public static final int RSF = 0x10;
    public static final int POP = 0x11;
    public static final int ADD = 0x12;
    public static final int SUB = 0x13;
    public static final int MUL = 0x14;
    public static final int DIV = 0x15;
    public static final int MOD = 0x16;
    public static final int EQ = 0x17;
    public static final int NE = 0x18;
    public static final int LT = 0x19;
    public static final int LE = 0x1A;
    public static final int GT = 0x1B;
    public static final int GE = 0x1C;
    public static final int NOT = 0x1D;
    public static final int NEG = 0x1E;
    public static final int HALT = 0x1F;
    public static final int ARRAY = 0x20;
}
