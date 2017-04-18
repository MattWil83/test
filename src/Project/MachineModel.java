package Project;

import java.util.Map;
import java.util.TreeMap;

public class MachineModel {

	public final Map<Integer, Instruction> IMAP=new TreeMap<>();
	private CPU cpu=new CPU();
	private Memory memory=new Memory();
	private HaltCallback callback;


	public MachineModel(HaltCallback callback) {
		this.callback = callback;

		//NOOP
		IMAP.put(0x0, (arg,level) -> {
			if(level != 0) {
				throw new IllegalArgumentException(
						"Illegal indirection level in NOP instruction");}
			if (level == 0) {
				cpu.incrPC();}});

		//LOD
		IMAP.put(0x1, (arg,level) -> {
			if(level < 0 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in LOD instruction");}
			if (level > 0) {
				IMAP.get(0x1).execute(memory.getData(arg), level-1);}
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
				IMAP.get(0x2).execute(memory.getData(arg), level-1);}});

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
						"Illegal indirection level in ADD instruction");}
			if(level > 0) {
				IMAP.get(0x3).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			} else {
				cpu.setAccum(cpu.getAccum() - arg);
				cpu.incrPC();}});

		//MUL
		IMAP.put(0x5, (arg, level) ->{
			if(level < 0 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in ADD instruction");}
			if(level > 0) {
				IMAP.get(0x3).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			} else {
				cpu.setAccum(cpu.getAccum() * arg);
				cpu.incrPC();}});

		//DIV
		IMAP.put(0x6, (arg, level) ->{
			if(level < 0 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in ADD instruction");}
			if(level > 0) {
				IMAP.get(0x3).execute(memory.getData(cpu.getMemBase()+arg), level-1);} 
			if(arg==0)
				throw new DivideByZeroException("Cannot Divide By Zero");
			else {
				cpu.setAccum(cpu.getAccum() / arg);
				cpu.incrPC();}});




	}



	public MachineModel() {
		this(() -> System.exit(0));

	}






}
