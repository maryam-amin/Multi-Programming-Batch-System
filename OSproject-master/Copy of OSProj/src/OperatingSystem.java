import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


public class OperatingSystem {
	
	static boolean  readSem  =true;
	static boolean  writeSem =true;
	static boolean  printSem =true;
	static boolean  inputSem =true;
	
	
	public static ArrayList<Thread> ProcessTable;
	public static ArrayList<Thread> printBlockedProcesses = new ArrayList<Thread>();
	public static ArrayList<Thread> inputBlockedProcesses = new ArrayList<Thread>();
	public static ArrayList<Thread> readBlockedProcesses = new ArrayList<Thread>();
	public static ArrayList<Thread> writeBlockedProcesses = new ArrayList<Thread>();

//	public static int activeProcess= 0;
	//system calls:
	// 1- Read from File
	@SuppressWarnings("unused")
	public static String readFile(String name) {
		String Data="";
		File file = new File(name);
	 try {
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine())
		{
			Data+= scan.nextLine()+"\n";
		}
		scan.close();
	} catch (FileNotFoundException e) {
		System.out.println(e.getMessage());
	}
		return Data;
	}
	
	// 2- Write into file
	@SuppressWarnings("unused")
	public static void writefile(String name, String data) {
		try
		{
			BufferedWriter BW = new BufferedWriter(new FileWriter(name));
			BW.write(data);
			BW.close();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}

	}
	//3- print to console
	@SuppressWarnings("unused")
	public static void printText(String text) {

		System.out.println(text);
		
	}
	
	//4- take input
	
	@SuppressWarnings("unused")
	public static String TakeInput() {
		Scanner in= new Scanner(System.in);
		String data = in.nextLine();
		return data;
		
	}
	
	private static void createProcess(int processID) throws InterruptedException{
		Process p = new Process(processID);
		ProcessTable.add(p);
		Process.setProcessState(p,ProcessState.Ready);
		if(ProcessTable.size()>1) {
		try {
			p.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else {
			p.start();
		}
	}
	
	public static void semPrintWait(Process p){
//		System.out.println("waiting "+printSem);
		while(!printSem) {
//			System.out.println("suspension");
			printBlockedProcesses.add(p);
			Process.setProcessState(p, ProcessState.Waiting);
			p.suspend();		
		}
		printSem=false;
//		System.out.println("checkprint "+printSem);

	}
	
	public static void semPrintPost(Process p){
//		System.out.println("posting "+printSem);
		if (!printSem && printBlockedProcesses.size()>0) {
		Process p2 = (Process) printBlockedProcesses.get(0);
		p2.resume();
		Process.setProcessState(p2, ProcessState.Ready);
		printBlockedProcesses.remove(0);
		}
		printSem = true;

	}
	
	public static void semInputWait(Process p){
//		System.out.println("waiting "+printSem);
		while(!inputSem) {
//			System.out.println("suspension");
			inputBlockedProcesses.add(p);
			Process.setProcessState(p, ProcessState.Waiting);
			p.suspend();		
		}
//		else {
			inputSem=false;
//			}
//		System.out.println("checkprint "+printSem);

	}
	
	public static void semInputPost(Process p){
//		System.out.println("posting "+printSem);
		if (!inputSem && inputBlockedProcesses.size()>0) {
		Process p2 = (Process) inputBlockedProcesses.get(0);
		p2.resume();
		Process.setProcessState(p2, ProcessState.Ready);
		inputBlockedProcesses.remove(0);
		}
		inputSem = true;

	}
	
	public static void semReadWait(Process p){
//		System.out.println("waiting "+printSem);
		while(!readSem) {
//			System.out.println("suspension");
			readBlockedProcesses.add(p);
			Process.setProcessState(p, ProcessState.Waiting);
			p.suspend();		
		}
//		else {
			readSem=false;
//			}
		System.out.println("checkread "+readSem);

	}
	
	public static void semReadPost(Process p){
//		System.out.println("posting "+printSem);
		if (!readSem && readBlockedProcesses.size()>0) {
		Process p2 = (Process) readBlockedProcesses.get(0);
		p2.resume();
		Process.setProcessState(p2, ProcessState.Ready);
		readBlockedProcesses.remove(0);
		}
		readSem = true;

	}
	
	public static void semWriteWait(Process p){
//		System.out.println("waiting "+printSem);
		while(!writeSem) {
//			System.out.println("suspension");
			writeBlockedProcesses.add(p);
			Process.setProcessState(p, ProcessState.Waiting);
			p.suspend();		
		}
//		else {
			writeSem=false;
//			}
//		System.out.println("checkprint "+printSem);

	}
	
	public static void semWritePost(Process p){
		System.out.println("write posting "+writeSem);
		if (!writeSem && writeBlockedProcesses.size()>0) {
		Process p2 = (Process) writeBlockedProcesses.get(0);
		p2.resume();
		Process.setProcessState(p2, ProcessState.Ready);
		writeBlockedProcesses.remove(0);
		}
		writeSem = true;

	}
	
//	just trying
//	public static void schedule() throws InterruptedException {
//		int z=0;
//		while(ProcessTable.size()>0) {
//			for (int i=0;i<ProcessTable.size();i++) {
//				if(i!=z%ProcessTable.size())
//				ProcessTable.get(i).sleep(200*(ProcessTable.size()-1));
//			}
//			z++;
//		}
//	}
	public static void main(String[] args) throws InterruptedException {
   		ProcessTable = new ArrayList<Thread>();
//   		schedule();
		createProcess(1);
		createProcess(2);
		createProcess(3);
		createProcess(4);
		createProcess(5);

	}
}



