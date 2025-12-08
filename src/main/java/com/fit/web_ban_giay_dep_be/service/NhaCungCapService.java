package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.NhaCungCap;

import java.util.List;
import java.util.Optional;

public interface NhaCungCapService {
    List<NhaCungCap> getAll();

    Optional<NhaCungCap> getNhaCungCapById(String id);

    NhaCungCap addNhaCungCap(NhaCungCap ncc);

    NhaCungCap updateNhaCungCap(String id, NhaCungCap ncc);

    void deleteNhaCungCap(String id);
}
