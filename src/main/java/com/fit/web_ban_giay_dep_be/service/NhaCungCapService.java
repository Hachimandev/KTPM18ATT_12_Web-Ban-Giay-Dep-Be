package com.fit.web_ban_giay_dep_be.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fit.web_ban_giay_dep_be.entity.NhaCungCap;

public interface NhaCungCapService {
    List<NhaCungCap> getAll();

    Optional<NhaCungCap> getNhaCungCapById(String id);

    NhaCungCap addNhaCungCap(NhaCungCap ncc);

    NhaCungCap updateNhaCungCap(String id, NhaCungCap ncc);

    void deleteNhaCungCap(String id);

    List<NhaCungCap> searchSuppliers(String keyword);

    byte[] exportToExcel() throws IOException;
}
