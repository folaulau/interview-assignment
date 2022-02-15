package com.folautech.cleanupdata.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.folautech.cleanupdata.model.RowData;

interface RowDataRepository extends JpaRepository<RowData, String> {

   Page<RowData> findBySlug(String slug, Pageable pageable);
}
