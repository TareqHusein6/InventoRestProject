package InvetoRestProject;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

//a pdf listener to add to each button 
public class SaveAsPdfListener implements ActionListener{

	private JTable jt;
	private String tableName;
	private String pdfName=null;
	
	public SaveAsPdfListener(JTable t,String name) {
		this.jt=t;
		this.tableName=name;
	}
	public SaveAsPdfListener(JTable t,String name,String pdfName) {
		this.jt=t;
		this.tableName=name;
		this.pdfName=pdfName;
	}
	public String getPdfName()
	{
		return this.pdfName;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//converting jtable to pdf file
		if(this.jt.getColumnCount()>0)
		{
			Document document = new Document(PageSize.A4);
			try {
				//gets the current date
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy_HHmmss").format(Calendar.getInstance().getTime());
				this.pdfName=tableName+timeStamp+".pdf";
				PdfWriter.getInstance(document, new FileOutputStream(pdfName));
				
				document.open();
				Paragraph header = new Paragraph(tableName,
			            new Font(Font.getFamilyIndex("HELVETICA"), 20, Font.BOLD));
				header.setAlignment(Element.ALIGN_CENTER);
				
				header.setSpacingAfter(20);
				Paragraph footer = new Paragraph(timeStamp);
				footer.setAlignment(Element.ALIGN_BOTTOM);
	
				document.add(header);
	
				PdfPTable pdfTable = new PdfPTable(jt.getColumnCount());
				pdfTable.setSpacingAfter(20);
				pdfTable.setWidthPercentage(100);
				PdfPCell[] cells = new PdfPCell[jt.getColumnCount()*jt.getRowCount()];
				Phrase ph;
				Font f = new Font(FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD));
				
				//adding table headers
				for(int i=0;i<jt.getColumnCount();i++) {
					cells[i] = new PdfPCell();
					ph=new Phrase(new Chunk(jt.getColumnName(i),f));
					cells[i].setPhrase(ph);
					pdfTable.addCell(cells[i]);
	
				}
				//getting data from jtable and inserting into pdfptable
				for(int rows=0;rows<jt.getRowCount();rows++) {
					for(int cols=0;cols<jt.getColumnCount();cols++) {
						cells[rows+cols] = new PdfPCell();
						cells[rows+cols].setPhrase(new Phrase(jt.getModel().getValueAt(rows, cols).toString()));
						pdfTable.addCell(cells[rows+cols]);	
					}
				}
				
				document.add(pdfTable);
				document.add(footer);
				document.close();
				
				showMessage("Pdf file created successfuly", "Success");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showMessage("File not found", "Error");
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showMessage("Problem with document", "Error");
		}
		}
		else
			showMessage("Load table first", "Error");
	}
	public static void showMessage(String infoMessage,String titleBar)
	{
		JOptionPane.showMessageDialog(null,infoMessage,titleBar,JOptionPane.INFORMATION_MESSAGE);
	}
	
}
