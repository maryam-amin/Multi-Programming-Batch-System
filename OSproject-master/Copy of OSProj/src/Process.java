//import java.util.concurrent.Semaphore;


public class Process extends Thread {
	
	public int processID;
    ProcessState status=ProcessState.New;	

	
	public Process(int m) {
		processID = m;
	}
	@Override
	public void run() {
		
		switch(processID)
		{
		case 1:process1();break;
		case 2:process2();break;
		case 3:process3();break;
		case 4:process4();break;
		case 5:process5();break;
		}

	}
	
	private void process1() {
		OperatingSystem.semPrintWait(this);
		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.semPrintPost(this);

		
		
		OperatingSystem.semInputWait(this);
		String fname = OperatingSystem.TakeInput();
		OperatingSystem.semInputPost(this);
	
//		OperatingSystem.semReadWait(this);
		String data = OperatingSystem.readFile(fname+".txt");
//		OperatingSystem.semReadPost(this);
		
		OperatingSystem.semPrintWait(this);
		OperatingSystem.printText(data);
		OperatingSystem.semPrintPost(this);
		
		setProcessState(this,ProcessState.Terminated);
//		System.out.println("p1 over");
		}
	
	private void process2() {
		
		OperatingSystem.semPrintWait(this);
		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.semPrintPost(this);

		OperatingSystem.semInputWait(this);
		String filename= OperatingSystem.TakeInput();
		OperatingSystem.semInputPost(this);

		OperatingSystem.semPrintWait(this);
		OperatingSystem.printText("Enter Data: ");
		OperatingSystem.semPrintPost(this);

		OperatingSystem.semInputWait(this);
		String data= OperatingSystem.TakeInput();
		OperatingSystem.semInputPost(this);

//		OperatingSystem.semWriteWait(this);
		OperatingSystem.writefile(filename,data);
//		OperatingSystem.semWritePost(this);
		
		setProcessState(this,ProcessState.Terminated);
//		System.out.println("p2 over");

		}
	private void process3() {
		int x=0;
		OperatingSystem.semPrintWait(this);

		while (x<301)
		{ 			
			OperatingSystem.printText(x+"\n");
			x++;
		}	
		OperatingSystem.semPrintPost(this);
		setProcessState(this,ProcessState.Terminated);
//		System.out.println("p3 over");
	
	}
	
	private void process4() {
	
		int x=500;
		OperatingSystem.semPrintWait(this);
		while (x<1001)
		{
		
			OperatingSystem.printText(x+"\n");
			x++;
		}	
		OperatingSystem.semPrintPost(this);
		setProcessState(this,ProcessState.Terminated);
//		System.out.println("p4 over");
}
	private void process5() {
		OperatingSystem.semPrintWait(this);
		OperatingSystem.printText("Enter LowerBound: ");
		OperatingSystem.semPrintPost(this);

		OperatingSystem.semInputWait(this);
		String lower= OperatingSystem.TakeInput();
		OperatingSystem.semInputPost(this);

		OperatingSystem.semPrintWait(this);
		OperatingSystem.printText("Enter UpperBound: ");
		OperatingSystem.semPrintPost(this);

		OperatingSystem.semInputWait(this);
		String upper= OperatingSystem.TakeInput();
		OperatingSystem.semInputPost(this);		
		
		int lowernbr=Integer.parseInt(lower);
		int uppernbr=Integer.parseInt(upper);
		String data="";
		
		while (lowernbr<=uppernbr)
		{
			data+=lowernbr++ +"\n";
		}	
		
//		OperatingSystem.semWriteWait(this);
		OperatingSystem.writefile("P5.txt", data);
//		OperatingSystem.semWritePost(this);

		setProcessState(this,ProcessState.Terminated);
//		System.out.println("p5 over");
	}
	

	
	 public static void setProcessState(Process p, ProcessState s) {
		 p.status=s;
		 if (s == ProcessState.Terminated)
		 {
			 OperatingSystem.ProcessTable.remove(OperatingSystem.ProcessTable.indexOf(p));
			 
			 if( OperatingSystem.ProcessTable.size()>0)
			 OperatingSystem.ProcessTable.get(0).start(); 
		 }
	}
	 
	 public static ProcessState getProcessState(Process p) {
		 return p.status;
	}
	 

}
