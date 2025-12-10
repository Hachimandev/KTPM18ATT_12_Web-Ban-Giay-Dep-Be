package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.dto.ChangePasswordRequest;
import com.fit.web_ban_giay_dep_be.dto.UpdateAccountRequest;
import com.fit.web_ban_giay_dep_be.entity.TaiKhoan;
import com.fit.web_ban_giay_dep_be.repository.TaiKhoanRepository;
import com.fit.web_ban_giay_dep_be.service.TaiKhoanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaiKhoanServiceImpl implements TaiKhoanService {

    private final TaiKhoanRepository taiKhoanRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TaiKhoan getCurrentAccount(String username) {
        return taiKhoanRepository.findByTenDangNhap(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
    }

    @Override
    @Transactional
    public TaiKhoan updateAccount(String username, UpdateAccountRequest request) {
        TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        if (request.getEmail() != null && !request.getEmail().equals(taiKhoan.getEmail())) {
            if (taiKhoanRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email đã được sử dụng");
            }
            taiKhoan.setEmail(request.getEmail());
        }

        if (taiKhoan.getKhachHang() != null) {
            if (request.getHoTen() != null) {
                taiKhoan.getKhachHang().setHoTen(request.getHoTen());
            }
            if (request.getSdt() != null) {
                taiKhoan.getKhachHang().setSdt(request.getSdt());
            }
            if (request.getDiaChi() != null) {
                taiKhoan.getKhachHang().setDiaChi(request.getDiaChi());
            }
        }

        return taiKhoanRepository.save(taiKhoan);
    }

    @Override
    @Transactional
    public void changePassword(String username, ChangePasswordRequest request) {
        TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        if (!passwordEncoder.matches(request.getOldPassword(), taiKhoan.getMatKhau())) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }

        if (request.getNewPassword().length() < 6) {
            throw new RuntimeException("Mật khẩu mới phải có ít nhất 6 ký tự");
        }

        taiKhoan.setMatKhau(passwordEncoder.encode(request.getNewPassword()));
        taiKhoanRepository.save(taiKhoan);
    }
}