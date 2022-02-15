package com.folautech.cleanupdata.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.folautech.cleanupdata.model.RowData;

public interface RowDataDAO {

    RowData save(RowData row);

    Page<RowData> getBySlug(String slug, Pageable pageable);

}
