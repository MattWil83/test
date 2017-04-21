package Project;

public class Code {
	public static final int CODE_MAX=1024;
	private long[] code=new long[1024];


	public void setCode(int index, int op, int indirLvl, int arg) {
		// the opcode will use 29 bits, multiplying by
		// 8 moves 3 bits to the left
		long longOp = op*8;
		// put the indirection level in those last 3 bits
		longOp += indirLvl;
		long longArg = arg;
		// move the opcode and indirLvl to the upper 32 bits
		long OpAndArg = longOp << 32;
		// if arg was negative, longArg will have 32 leading 1s,
		// remove them:
		longArg = longArg & 0x00000000FFFFFFFFL;
		//join the upper 32 bits and the lower 32 bits
		code[index] = OpAndArg | longArg;
	}

	int getOp(int i) {
		// move upper half to the lower half discarding lower half 
		// and the 3 bit of the indirLvl
		return (int)(code[i] >> 35);
	}

	int getIndirLvl(int i) {
		// move upper half to the lower half discarding lower half
		// then get last 3 bits
		return (int)(code[i] >> 32)%8;
	}

	int getArg(int i) {
		// cut out upper half keeping lower half
		return (int)(code[i] & 0x00000000FFFFFFFFL);
	}


}



