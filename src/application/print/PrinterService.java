package application.print;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import jssc.*;
 
public class PrinterService{
	
	SerialPort serialPort;
	
	String Gs = Character.toString((char)0x1D);
    String V1 = "V1";
    String CutPaper = Gs+V1;
    
    String ESC = Character.toString((char)0x1B);
    String J1 = "J1";
    String FeedPaper = ESC+J1;
    
    String LF = Character.toString((char)0x0A);
	
	public PrinterService(){
		
		String[] portNames = SerialPortList.getPortNames();
		
		if (portNames.length == 0) {
		    System.out.println("There are no serial-ports :( You can use an emulator, such ad VSPE, to create a virtual serial port.");
		    System.out.println("Press Enter to exit...");
		    try {
		        System.in.read();
		    } catch (IOException e) {
		         // TODO Auto-generated catch block
		          e.printStackTrace();
		    }
		    return;
		}
		
		for (int i = 0; i < portNames.length; i++){
		    System.out.println(portNames[i]);
		 
		}
	}
	
	public void initialCom(String portName){
		 serialPort = new SerialPort(portName);
		 try {
			 serialPort.openPort();

		     serialPort.setParams(SerialPort.BAUDRATE_19200,
		                          SerialPort.DATABITS_8,
		                          SerialPort.STOPBITS_1,
		                          SerialPort.PARITY_NONE);
		 }
		 catch (SerialPortException ex) {
		     System.out.println("There are an error on writing string to port т: " + ex);
		 }
	}
	
	public void Print(String listContent) throws UnsupportedEncodingException, SerialPortException{
		
		serialPort.writeBytes(listContent.getBytes("GB18030"));
		
        serialPort.writeBytes(LF.getBytes("GB2312"));
        serialPort.writeBytes(LF.getBytes("GB2312"));
        serialPort.writeBytes(LF.getBytes("GB2312"));
        serialPort.writeBytes(LF.getBytes("GB2312"));
        serialPort.writeBytes(LF.getBytes("GB2312"));
        serialPort.writeBytes(CutPaper.getBytes("GB2312"));
        serialPort.closePort();
	}

}
