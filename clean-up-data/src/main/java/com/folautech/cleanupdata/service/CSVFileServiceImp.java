package com.folautech.cleanupdata.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.folautech.cleanupdata.dao.RowDataDAO;
import com.folautech.cleanupdata.model.RowData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CSVFileServiceImp implements CSVFileService {

    @Autowired
    private RowDataDAO rowDataDAO;

    @Override
    public String generateFile(String slug) {

        int pageSize = 20;
        int pageNumber = 0;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));

        Page<RowData> result = rowDataDAO.getBySlug(slug, pageable);

        if (!result.hasContent()) {

        }

        String[] HEADERS = {"id", "column_1", "column_2", "column_3", "column_4", "column_5", "column_6", "column_7"};
        String filename = new Date().getTime() + "cleaned_data.csv";
        FileWriter out = null;
        try {
            out = new FileWriter(filename);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            while (result.hasContent()) {
                List<RowData> rows = result.getContent();

                for (RowData row : rows) {

                    Double column1 = row.getColumn1();

                    printer.printRecord(row.getId(), row.getColumn1());
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return filename;
    }

}
