package com.folautech.cleanupdata.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.folautech.cleanupdata.dao.RowDataDAO;
import com.folautech.cleanupdata.model.RowData;
import com.folautech.cleanupdata.utils.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileProcessorServiceImp implements FileProcessorService {

    @Autowired
    private RowDataDAO rowDataDAO;

    DateTimeFormatter  dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String cleanUp(String filename) throws FileNotFoundException {

        if (null == filename || filename.length() == 0) {
            throw new IllegalArgumentException("filename is empty");
        }

        File file = ResourceUtils.getFile("classpath:" + filename);

        if (!file.exists()) {
            throw new FileNotFoundException("file not found");
        }

        String slug = new Date().getTime() + "-" + UUID.randomUUID().toString();

        try (Scanner scanner = new Scanner(file);) {

            RowData row = null;

            int rowCount = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (rowCount == 0) {
                    rowCount++;
                    continue;
                }

                String[] columns = line.split(",");

                Optional<String> optId = Optional.ofNullable(columns[0]);

                if (!optId.isPresent() || optId.get().trim().length() == 0) {
                    log.warn("no id csvRecord={}", Arrays.toString(columns));
                    continue;
                }

                row = new RowData();

                String id = optId.get();
                row.setId(id);

                row.setSlug(slug);

                try {
                    if (columns[1] != null && columns[1].trim().length() > 0) {

                        Double column1 = Double.valueOf(columns[1]);
                        row.setColumn1(column1);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                try {
                    if (columns[2] != null && columns[2].trim().length() > 0) {

                        Long column2 = Long.valueOf(columns[2]);
                        row.setColumn2(column2);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                try {
                    if (columns[3] != null && columns[3].trim().length() > 0) {
                        Double column3 = Double.valueOf(columns[3]);
                        row.setColumn3(column3);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                try {
                    if (columns[4] != null && columns[4].trim().length() > 0) {
                        LocalDate column4 = LocalDate.parse(columns[4], dateFormatter);
                        row.setColumn4(column4);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                try {
                    if (columns[5] != null && columns[5].trim().length() > 0) {

                        Long column5 = Long.valueOf(columns[5]);
                        row.setColumn5(column5);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                try {
                    if (columns[6] != null && columns[6].trim().length() > 0) {

                        Long column6 = Long.valueOf(columns[6]);
                        row.setColumn6(column6);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                try {
                    if (columns[7] != null && columns[7].trim().length() > 0) {

                        String column7 = columns[7];
                        row.setColumn7(column7);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                log.info("row={}", ObjectUtils.toJson(row));

                rowDataDAO.save(row);

                rowCount++;

            }

        }

        return slug;
    }
}
