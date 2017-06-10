package application.print;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;
 
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JOptionPane;
 
public class PrinterService{
	
	public List<String> getPrinters(){
		
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		
		PrintService printServices[] = PrintServiceLookup.lookupPrintServices(
				flavor, pras);
		
		List<String> printerList = new ArrayList<String>();
		for(PrintService printerService: printServices){
			printerList.add( printerService.getName());
		}
		
		return printerList;
	}
 
	public void printBytes(String printerName, String list_content) {
		
		//Especificamos el tipo de dato a imprimir
	    //Tipo: bytes; Subtipo: autodetectado
	    DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
	    
	    PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
	    
	    PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
	    PrintService service = PrintServiceLookup.lookupDefaultPrintService();
	    
	    //PrintService service = findPrintService(printerName, printService);
	    
	    //PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
	    //PrintService service = ServiceUI.printDialog(null, 700, 200, printService, defaultService, flavor, pras);
	      
	    
	    try { 
	    	byte[] bytes = list_content.getBytes();
	    	Doc doc = new SimpleDoc(bytes,flavor,null);
	    	DocPrintJob job = service.createPrintJob(); 
	    	job.print(doc, null);
	    	
	    } catch (Exception er) {
	      JOptionPane.showMessageDialog(null,"Error al imprimir: " + er.getMessage());
	    }
	  }
	private PrintService findPrintService(String printerName,
			PrintService[] services) {
		for (PrintService service : services) {
			if (service.getName().equalsIgnoreCase(printerName)) {
				return service;
			}
		}
 
		return null;
	}
}
