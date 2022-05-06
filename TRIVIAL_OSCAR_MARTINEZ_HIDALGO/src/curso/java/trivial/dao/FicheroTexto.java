package curso.java.trivial.dao;

import java.awt.Desktop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import curso.java.trivial.ClaseLog;
import curso.java.trivial.auxiliares.Auxiliar;
import curso.java.trivial.auxiliares.Constantes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class FicheroTexto{
	
	
	/**
	 * Metodo para generar el fichero pdf
	 * @param nick
	 * @param logP
	 */
	public static void generarPdf(String nick, ArrayList<String> logP) {

		// TODO Auto-generated method stub
		PdfWriter writer = null;
		Document documento = new Document(PageSize.A4, 20, 20, 70, 50);

		try {
			// Obtenemos la instancia del archivo a utilizar
			writer = PdfWriter.getInstance(documento, new FileOutputStream(Constantes.FICHEROINFORME));

			// Para insertar cabeceras/pies en todas las paginas
			writer.setPageEvent(new PDFHeaderFooter());

			// Abrimos el documento para edicion
			documento.open();

			// PARRAFO PARA ESCRIBIR EL NICK
			Paragraph paragraph = new Paragraph();
			paragraph.setSpacingBefore(5);
			paragraph.add("\n");
			String font = "Sans";
			float tamanno = 15;
			int style = Font.UNDERLINE;
			BaseColor color = BaseColor.RED;

			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setFont(new Font(FontFactory.getFont(font, tamanno, style, color)));
			paragraph.add(Auxiliar.rb.getString("ficheropdf")+nick);
			documento.add(paragraph);
			
			//PARRAFOS PARA ESCRIBIR EL ARRAY DE STRINGS CON LOS DATOS DE LA PARTIDA---------------------------
			for (int i = 0; i < logP.size(); i++) {

				// PARRAFOS
				paragraph = new Paragraph();
				paragraph.setSpacingBefore(5);
				font = "Sans";
				tamanno = 11;
				style = Font.BOLDITALIC;
				color = BaseColor.GRAY;

				paragraph.setAlignment(Element.ALIGN_CENTER);
				paragraph.setFont(new Font(FontFactory.getFont(font, tamanno, style, color)));
				paragraph.add(logP.get(i));

				documento.add(paragraph);
			}
			documento.close(); // Cerramos el documento
			writer.close(); // Cerramos writer
			try {
				File path = new File(Constantes.FICHEROINFORME);
				Desktop.getDesktop().open(path);
			} catch (IOException ex) {
				ClaseLog.miLog.debug("Excepcion en Generar pdf");
				System.out.println("Excepcion en Generar pdf");
				ex.printStackTrace();
			}
		} catch (Exception ex) {
			ClaseLog.miLog.debug("Excepcion en Generar pdf");
			System.out.println("Excepcion en Generar pdf");
			ex.getMessage();
		}
	}

	
}
