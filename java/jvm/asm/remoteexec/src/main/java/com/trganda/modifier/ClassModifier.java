package com.trganda.modifier;

/**
 * Modify the constant pool of *.class file
 */
public class ClassModifier {
    // offset of constant poll
    private static final int CONSTANT_POLL_COUNT_INDEX = 8;

    // tag of CONSTANT_Utf8_info
    private static final int CONSTANT_Utf8_info = 1;

    /**
     * CONSTANT_Integer_info - tag 3, length 5
     * CONSTANT_Float_info - tag 4, length 5
     * CONSTANT_Long_info - tag 5, length 9
     * CONSTANT_Double_info - tag 6, length 9
     * CONSTANT_Class_info - tag 7, length 3
     * CONSTANT_String_info - tag 8, length 3
     * CONSTANT_Fieldref_info - tag 9, length 5
     * CONSTANT_Methodref_info - tag 10, length 5
     * CONSTANT_InterfaceMethodref_info - tag 11, length 5
     * CONSTANT_NameAndType_info - tag 12, length 5
     * CONSTANT_MethodHandle_info - tag 15, length 5
     * CONSTANT_MethodType_info - tag 16, length 3
     * CONSTANT_Dynamic_info - tag 17, length 5
     * CONSTANT_InvokeDynamic_info - tag 18, length 5
     * CONSTANT_Module_info - tag 19, length 3
     * CONSTANT_Package_info - tag 20, length 3
     */
    private static final int[] CONSTANT_ITEM_LENGTH = { -1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5 };

    private static final int u1 = 1;
    private static final int u2 = 2;

    private byte[] classByte;

    public ClassModifier(byte[] classByte) {
        this.classByte = classByte;
    }

    /**
     * Modify the CONSTANT_Utf8_info of constant pool
     * @param oldStr string before
     * @param newStr string after
     * @return modified class file as bytes
     */
    public byte[] modifyUTF8Constant(String oldStr, String newStr) {
        int cpc = getConstantPoolCount();
        int offset = CONSTANT_POLL_COUNT_INDEX + u2;
        for (int i = 1; i < cpc; i++) {
            int tag = ByteUtils.bytes2Int(classByte, offset, u1);
            if (tag == CONSTANT_Utf8_info) {
                int len = ByteUtils.bytes2Int(classByte, offset + u1, u2);
                offset += (u1 + u2);
                String str = ByteUtils.bytes2String(classByte, offset, len);
                if (str.equalsIgnoreCase(oldStr)) {
                    byte[] strBytes = ByteUtils.string2Bytes(newStr);
                    byte[] strLen = ByteUtils.int2Bytes(strBytes.length, u2);
                    classByte = ByteUtils.bytesReplace(classByte, offset - u2, u2, strLen);
                    classByte = ByteUtils.bytesReplace(classByte, offset, len, strBytes);
                    return classByte;
                } else {
                    offset += len;
                }
            } else {
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }
        return classByte;
    }

    public int getConstantPoolCount() {
        return ByteUtils.bytes2Int(classByte, CONSTANT_POLL_COUNT_INDEX, u2);
    }
}
