package Project;

import java.util.Map;
import java.util.TreeMap;

public class MachineModel {

	public final Map<Integer, Instruction> IMAP=new TreeMap<>();
	private CPU cpu=new CPU();
	private Memory memory=new Memory();
	private HaltCallback callback;
	private Code Code = new Code();
	
	
	public MachineModel(HaltCallback callback) {
		this.callback = callback;

		//NOP
		IMAP.put(0x0, (arg,level) -> {
				cpu.incrPC();});

		//LOD
		IMAP.put(0x1, (arg,level) -> {
			if(level < 0 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in LOD instruction");}
			if (level > 0) {
				IMAP.get(0x1).execute(memory.getData(cpu.getMemBase()), level-1);}
			else {
				cpu.setAccum(arg);
				cpu.incrPC();}});
		//STO
		IMAP.put(0x2, (arg,level) -> {
			if(level < 1 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in STO instruction");}
			if (level == 1) {
				memory.setData(arg, cpu.getAccum());
				cpu.incrPC();}
			else {
				IMAP.get(0x2).execute(memory.getData(cpu.getMemBase()), level-1);}});
		//ADD
		IMAP.put(0x3, (arg, level) ->{
			if(level < 0 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in ADD instruction");}
			if(level > 0) {
				IMAP.get(0x3).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			} else {
				cpu.setAccum(cpu.getAccum() + arg);
				cpu.incrPC();}});
		//SUB
		IMAP.put(0x4, (arg, level) ->{
			if(level < 0 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in SUB instruction");}
			if(level > 0) {
				IMAP.get(0x4).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			} else {
				cpu.setAccum(cpu.getAccum() - arg);
				cpu.incrPC();}});
		//MUL
		IMAP.put(0x5, (arg, level) ->{
			if(level < 0 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in MUL instruction");}
			if(level > 0) {
				IMAP.get(0x5).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			} else {
				cpu.setAccum(cpu.getAccum() * arg);
				cpu.incrPC();}});
		//DIV
		IMAP.put(0x6, (arg, level) ->{
			if(level < 0 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in DIV instruction");}
			if(level > 0) {
				IMAP.get(0x6).execute(memory.getData(cpu.getMemBase()+arg), level-1);} 
			if(arg==0)
				throw new DivideByZeroException("Cannot Divide By Zero");
			else {
				cpu.setAccum(cpu.getAccum() / arg);
				cpu.incrPC();}});
		//AND
		IMAP.put(0x7, (arg,level) -> {
			if(level < 0 || level > 1) {
				throw new IllegalArgumentException(
						"Illegal indirection level in AND instruction");}
			if (level > 0) {
				IMAP.get(0x7).execute(memory.getData(cpu.getMemBase()), level-1);
			} else {
				if (arg != 0 && cpu.getAccum() != 0) {
					cpu.setAccum(1);}
				else {
					cpu.setAccum(0);}
				cpu.incrPC();}});
		//NOT
		IMAP.put(0x8, (arg,level) -> {
			if(cpu.getAccum() == 0){
				cpu.setAccum(1);}
			else {
				cpu.setAccum(0);}
			cpu.incrPC();});
		//CMPZ
		IMAP.put(0xA, (arg,level) -> {
			if(level != 1 || level != 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in CMPZ instruction");}
			if(level == 2){
				IMAP.get(0xA).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			}
			if(level == 1){
				if(memory.getData(cpu.getMemBase()+arg) == 0){
					cpu.setAccum(1);}
				else{
					cpu.setAccum(0);}
				cpu.incrPC();}
		});

        //CMPL
		IMAP.put(0x9, (arg,level) -> {
			if(level != 1 || level != 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in CMPL instruction");}
			if(level == 2){
				IMAP.get(0x9).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			}
			if(level == 1){
				if(memory.getData(cpu.getMemBase()+arg) < 0){
					cpu.setAccum(1);}
				else{
					cpu.setAccum(0);}
				cpu.incrPC();}
		});

		//JUMP
		IMAP.put(0xB, (arg,level) -> {
			if(level < 0 || level > 3) {
				throw new IllegalArgumentException(
						"Illegal indirection level in JUMP instruction");}
			if(level > 2){
				//ABSOLUTE MODE (done in later part)
			}
			if(level > 1){
				IMAP.get(0xB).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			}
			else{
				cpu.setpCounter(cpu.getpCounter()+arg);}});

		//JMPZ
		IMAP.put(0xC, (arg,level) -> {
			if(level < 0 || level > 3) {
				throw new IllegalArgumentException(
						"Illegal indirection level in JUMP instruction");}
			if(level > 2){
				//ABSOLUTE MODE
			}
			else if(level > 1){
				IMAP.get(0xC).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			}
			else{
				if(cpu.getAccum()==0){
					cpu.setpCounter(cpu.getpCounter()+arg);}
				else{
					cpu.incrPC();}}});

		//HALT
		IMAP.put(0xF, (arg, level) -> {
			callback.halt();});
	}



	public int getData(int index) {
		return memory.getData(index);
	}



	public Memory getMemory() {
		return memory;
	}



	public int getAccum() {
		return cpu.getAccum();
	}



	public int getpCounter() {
		return cpu.getpCounter();
	}



	public int getMemBase() {
		return cpu.getMemBase();
	}



	public void setAccum(int accum) {
		cpu.setAccum(accum);
	}



	public void setpCounter(int pCounter) {
		cpu.setpCounter(pCounter);
	}



	public void setMemBase(int memBase) {
		cpu.setMemBase(memBase);
	}




	public Instruction get(int instrNum){
		return IMAP.get(instrNum);
	}
	public Map<Integer, Instruction> getIMAP() {
		return IMAP;}

	 public void setCode(int index, int op, int indirLvl, int arg){
		 Code.setCode(index, op, indirLvl, arg);}
	 public Code getCode(){
		 return Code;}



	public void setData(int index, int value) {
		memory.setData(index, value);
	}


	 
	 
	 
	 
}



