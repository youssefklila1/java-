/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Commande;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 *
 * @author melki
 */
public class PdfCommande {
         public void liste_CommandePDF() throws FileNotFoundException, DocumentException {

        CommandeService ec = new CommandeService();
        String message = "Voici la liste des commande \n\n";

        String file_name = "src/PDF/liste_Commande.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();
        Paragraph para = new Paragraph(message);
        document.add(para);
        List<Commande> Commande = ec.affichageCommandes();
        PdfPTable table = new PdfPTable(5);

        
        
        PdfPCell cl1 = new PdfPCell(new Phrase("id commande"));
        table.addCell(cl1);
        PdfPCell cl = new PdfPCell(new Phrase("etat"));
        table.addCell(cl);
        PdfPCell cl2 = new PdfPCell(new Phrase("user"));
        table.addCell(cl2);
        PdfPCell cl3 = new PdfPCell(new Phrase("total"));
        table.addCell(cl3);
        PdfPCell cl4 = new PdfPCell(new Phrase("date"));
        table.addCell(cl4);
        
        
        
        table.setHeaderRows(1);
        document.add(table);

        int i = 0;
        for (i = 0; i < Commande.size(); i++) {
            
            table.addCell("" + Commande.get(i).getId());
            table.addCell("" + Commande.get(i).getEtat());
            table.addCell("" + Commande.get(i).getUser_id());
            table.addCell("" + Commande.get(i).getTotal());
            table.addCell("" + Commande.get(i).getDate());

        }
        document.add(table);

        document.close();

    }
}
