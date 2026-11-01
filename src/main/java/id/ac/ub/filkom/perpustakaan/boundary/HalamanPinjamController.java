package id.ac.ub.filkom.perpustakaan.boundary;

import id.ac.ub.filkom.perpustakaan.control.Bukti;
import id.ac.ub.filkom.perpustakaan.control.PinjamGagalException;
import id.ac.ub.filkom.perpustakaan.control.PinjamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * «boundary» HalamanPinjam (bagian penerima request) — UC-01 Pinjam Buku.
 * Tugas: baca input, panggil SATU method control, pilih template.
 * Tanpa query dan tanpa aturan bisnis.
 */
@Controller
@RequestMapping("/pinjam")
public class HalamanPinjamController {

    /** Aplikasi latihan belum punya login: anggota disimulasikan lewat session. */
    private static final String ID_ANGGOTA_DEMO = "A001";

    private final PinjamService pinjamService;                // «control»

    public HalamanPinjamController(PinjamService pinjamService) {
        this.pinjamService = pinjamService;
    }

    /** Langkah 1-2 scenario: tampilkan daftar buku beserta stok. */
    @GetMapping
    public String tampilHalaman(HttpSession session, Model model) {
        model.addAttribute("idAnggota", idAnggotaLogin(session));
        model.addAttribute("daftarBuku", pinjamService.daftarBuku());
        return "pinjam";                                      // templates/pinjam.html
    }

    /** [1] pilihBuku(idBuku) dari aktor Anggota. */
    @PostMapping
    public String pilihBuku(@RequestParam String idBuku,
                            HttpSession session, Model model) {
        String idAnggota = idAnggotaLogin(session);
        try {
            // [2] pinjamBuku(idAnggota, idBuku)
            Bukti bukti = pinjamService.pinjamBuku(idAnggota, idBuku);
            return tampilBukti(bukti, model);                 // [12] tampilkan bukti
        } catch (PinjamGagalException e) {                    // [13] pesanHabis
            model.addAttribute("pesan", e.getMessage());
            model.addAttribute("bisaReservasi", e.isBisaReservasi());
            return tampilHalaman(session, model);             // [14] pesan & reservasi
        }
    }

    /** +tampilBukti() pada class diagram 8a. */
    private String tampilBukti(Bukti bukti, Model model) {
        model.addAttribute("bukti", bukti);
        return "bukti";                                       // templates/bukti.html
    }

    /** Identitas anggota dari session (default A001). */
    private String idAnggotaLogin(HttpSession session) {
        Object id = session.getAttribute("idAnggota");
        if (id == null) {
            session.setAttribute("idAnggota", ID_ANGGOTA_DEMO);
            return ID_ANGGOTA_DEMO;
        }
        return (String) id;
    }
}
