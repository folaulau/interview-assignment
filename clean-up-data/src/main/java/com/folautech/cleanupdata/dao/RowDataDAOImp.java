package com.folautech.cleanupdata.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.folautech.cleanupdata.model.RowData;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class RowDataDAOImp implements RowDataDAO {

    @Autowired
    private RowDataRepository rowDataRepository;

    @Autowired
    private JdbcTemplate      jdbcTemplate;

    @Override
    public RowData save(RowData row) {
        return rowDataRepository.saveAndFlush(row);
    }

    @Override
    public Page<RowData> getBySlug(String slug, Pageable pageable) {
        return rowDataRepository.findBySlug(slug, pageable);
    }


}
