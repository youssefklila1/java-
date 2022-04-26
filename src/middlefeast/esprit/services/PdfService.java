/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.services;




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
import middlefeast.esprit.entities.Commentaire;

/**
 *
 * @author melki
 */
public class PdfService {
         public void liste_CommandePDF() throws FileNotFoundException, DocumentException {

        CommentaireService ec = new CommentaireService();
        String message = "Voici la liste des commentaires \n\n";

        String file_name = "src/PDF/liste_Commentaire.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();
        Paragraph para = new Paragraph(message);
        document.add(para);
        List<Commentaire> Commande = ec.afficherCommentaire();
        PdfPTable table = new PdfPTable(3);

       
       
        PdfPCell cl1 = new PdfPCell(new Phrase("id commentaire"));
        table.addCell(cl1);
        PdfPCell cl2 = new PdfPCell(new Phrase("description"));
        table.addCell(cl2);
        PdfPCell cl3 = new PdfPCell(new Phrase("nombre de likes"));
        table.addCell(cl3);
       
       
       
       
        table.setHeaderRows(1);
        document.add(table);

        int i = 0;
        for (i = 0; i < Commande.size(); i++) {
           
            table.addCell("" + Commande.get(i).getId());
            table.addCell("" + Commande.get(i).getDescription());
            table.addCell("" + Commande.get(i).getNblike());
         
        }
        document.add(table);

        document.close();

    }
}