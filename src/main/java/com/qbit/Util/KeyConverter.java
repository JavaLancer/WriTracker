package com.qbit.Util;

import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * User: Chuck
 */
public class KeyConverter {

    public static int getKeyInt(String keyText) {
        // Lookup text values.
        switch (keyText) {
            case "1":
                return NativeKeyEvent.VC_1;
            case "2":
                return NativeKeyEvent.VC_2;
            case "3":
                return NativeKeyEvent.VC_3;
            case "4":
                return NativeKeyEvent.VC_4;
            case "5":
                return NativeKeyEvent.VC_5;
            case "6":
                return NativeKeyEvent.VC_6;
            case "7":
                return NativeKeyEvent.VC_7;
            case "8":
                return NativeKeyEvent.VC_8;
            case "9":
                return NativeKeyEvent.VC_9;
            case "0":
                return NativeKeyEvent.VC_0;

            case "_":
                return NativeKeyEvent.VC_UNDERSCORE;
            case "-":
                return NativeKeyEvent.VC_MINUS;
            case "=":
                return NativeKeyEvent.VC_EQUALS;
            case "  ":
                return NativeKeyEvent.VC_TAB;

            case "a":
            case "A":
                return NativeKeyEvent.VC_A;
            case "b":
            case "B":
                return NativeKeyEvent.VC_B;
            case "c":
            case "C":
                return NativeKeyEvent.VC_C;
            case "d":
            case "D":
                return NativeKeyEvent.VC_D;
            case "e":
            case "E":
                return NativeKeyEvent.VC_E;
            case "f":
            case "F":
                return NativeKeyEvent.VC_F;
            case "g":
            case "G":
                return NativeKeyEvent.VC_G;
            case "h":
            case "H":
                return NativeKeyEvent.VC_H;
            case "i":
            case "I":
                return NativeKeyEvent.VC_I;
            case "j":
            case "J":
                return NativeKeyEvent.VC_J;
            case "k":
            case "K":
                return NativeKeyEvent.VC_K;
            case "l":
            case "L":
                return NativeKeyEvent.VC_L;
            case "m":
            case "M":
                return NativeKeyEvent.VC_M;
            case "n":
            case "N":
                return NativeKeyEvent.VC_N;
            case "o":
            case "O":
                return NativeKeyEvent.VC_O;
            case "p":
            case "P":
                return NativeKeyEvent.VC_P;
            case "q":
            case "Q":
                return NativeKeyEvent.VC_Q;
            case "r":
            case "R":
                return NativeKeyEvent.VC_R;
            case "s":
            case "S":
                return NativeKeyEvent.VC_S;
            case "t":
            case "T":
                return NativeKeyEvent.VC_T;
            case "u":
            case "U":
                return NativeKeyEvent.VC_U;
            case "v":
            case "V":
                return NativeKeyEvent.VC_V;
            case "w":
            case "W":
                return NativeKeyEvent.VC_W;
            case "x":
            case "X":
                return NativeKeyEvent.VC_X;
            case "y":
            case "Y":
                return NativeKeyEvent.VC_Y;
            case "z":
            case "Z":
                return NativeKeyEvent.VC_Z;

            case "{":
                return NativeKeyEvent.VC_OPEN_BRACKET;
            case "}":
                return NativeKeyEvent.VC_CLOSE_BRACKET;
            case "\\":
                return NativeKeyEvent.VC_BACK_SLASH;

            case ";":
                return NativeKeyEvent.VC_SEMICOLON;
            case "\"":
                return NativeKeyEvent.VC_QUOTE;

            case ",":
                return NativeKeyEvent.VC_COMMA;
            case ".":
                return NativeKeyEvent.VC_PERIOD;
            case "/":
                return NativeKeyEvent.VC_SLASH;

            case " ":
                return NativeKeyEvent.VC_SPACE;
        }

        return -1;
    }
}
