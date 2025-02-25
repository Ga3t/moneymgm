package com.managment.moneyManagmentProject.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.managment.moneyManagmentProject.model.Ledger;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfGeneratorService {

    public byte[] generateLedgerReport(List<Ledger> ledgers) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            PdfPTable table = new PdfPTable(6); // 6 колонок
            table.setWidthPercentage(100);


            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            addTableHeader(table, headerFont);
            addTableRows(table, ledgers);

            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        } finally {
            document.close();
        }
        return outputStream.toByteArray();
    }

    private void addTableHeader(PdfPTable table, Font font) {
        table.addCell(new com.itextpdf.text.Phrase("Name", font));
        table.addCell(new com.itextpdf.text.Phrase("Description", font));
        table.addCell(new com.itextpdf.text.Phrase("Date", font));
        table.addCell(new com.itextpdf.text.Phrase("Transaction Type", font));
        table.addCell(new com.itextpdf.text.Phrase("CategoryServices", font));
        table.addCell(new com.itextpdf.text.Phrase("Price", font));
    }

    private void addTableRows(PdfPTable table, List<Ledger> ledgers) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Ledger ledger : ledgers) {
            table.addCell(ledger.getShortName() == null ? "" : ledger.getShortName());
            table.addCell(ledger.getDescription() == null ? "" : ledger.getDescription());
            table.addCell(ledger.getDate() == null ? "" : ledger.getDate().format(dateFormatter));

            String categoryTypeStr = "";
            if (ledger.getCategory() != null && ledger.getCategory().getCategoryType() != null) {
                categoryTypeStr = ledger.getCategory().getCategoryType().toString();
            }
            table.addCell(categoryTypeStr);

            table.addCell(ledger.getCategory() == null || ledger.getCategory().getName() == null ? "" : ledger.getCategory().getName());
            table.addCell(ledger.getPrice() == null ? "" : ledger.getPrice().toString());
        }
    }
}
